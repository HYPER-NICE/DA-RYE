import { SECRET_BACKEND_HOST } from '$env/static/private';

const pageData = {
	title: '메뉴'
}

/** @type {import('./$types').PageServerLoad} */
export async function load({ params, cookies }) {

	const memberInfoUrl = new URL(`${SECRET_BACKEND_HOST}/api/members/me`);

	const JSESSIONID = cookies.get('JSESSIONID');
	if (!JSESSIONID) {
		return { ...pageData }
	};

	try {
		const response = await fetch(memberInfoUrl, {
			headers: {
				Cookie: `JSESSIONID=${JSESSIONID}` // 올바른 쿠키 헤더 형식
			}
		});

		const member = await response.json();
		return {
			...pageData,
			member
		};
	} catch (error) {
		// 실패하면 세션이 만료되거나 로그인이 필요한 상태
		cookies.delete('JSESSIONID', { path: '/' });
		return { ...pageData }
	}
}

/** @satisfies {import('./$types').Actions} */
export const actions = {
	default: async ({event, cookies}) => {
		cookies.delete('JSESSIONID', { path: '/' });
		return {success: true};
	}
}