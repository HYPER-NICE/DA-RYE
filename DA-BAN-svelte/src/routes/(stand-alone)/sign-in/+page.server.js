import { SECRET_BACKEND_HOST } from '$env/static/private';
import { redirect } from '@sveltejs/kit';
// (선택) import cookie from 'cookie'; // 여러 개 쿠키 파싱 시

const pageData = {
	title: '로그인',
};

export function load() {
	return { ...pageData };
}

/** @satisfies {import('./$types').Actions} */
export const actions = {
	default: async ({ cookies, request }) => {
		// 1. 사용자가 제출한 formData를 받아온다.
		const data = await request.formData();
		const email = data.get('email');
		const password = data.get('password');

		// 2. 백엔드로 fetch 요청을 보낸다.
		const response = await fetch(`${SECRET_BACKEND_HOST}/api/sign-in`, {
			method: 'POST',
			headers: { 'Content-Type': 'application/json' },
			// credentials: 'include'는 서버 측 fetch에서 쿠키 전송을 시도할 수 있게 해준다.
			credentials: 'include',
			// body는 email, password를 JSON 형태로 담아 보낸다.
			body: JSON.stringify({ email, password }),
		});

		// 3. 백엔드에서 Set-Cookie 헤더로 JSESSIONID를 내려준 경우 해당 값을 꺼낸다.
		// 여러 쿠키가 있을 수 있으므로, 한 줄로 내려온 쿠키를 꺼내거나
		const setCookieHeader = response.headers.get('set-cookie');

		// 4. 쿠키 문자열에서 JSESSIONID만 파싱하고, SvelteKit cookies API로 사용자에게 다시 세팅한다.
		if (setCookieHeader) {
			// 간단한 케이스: 'JSESSIONID=abcd1234; Path=...'와 같은 한 줄짜리 쿠키 가정
			const [nameValue, ...attributes] = setCookieHeader.split(';');
			const [name, value] = nameValue.split('=');

			if (name === 'JSESSIONID') {
				// cookies.set(쿠키이름, 쿠키값, 옵션)
				cookies.set(name, value, {
					path: '/',
					// 백엔드에서 HttpOnly 등 옵션을 보내줬다면 따로 설정해줄 수도 있음
					httpOnly: false,
					sameSite: 'strict',
					secure: false,
				});
			}
		}

		// 5. 인증/로그인 성공 여부 확인 후, 에러라면 에러 처리
		if (!response.ok) {
			return { error: '로그인 실패' };
		}

		// 6. 성공 시 메인 페이지로 리다이렉트
		throw redirect(303, '/');
	},
};
