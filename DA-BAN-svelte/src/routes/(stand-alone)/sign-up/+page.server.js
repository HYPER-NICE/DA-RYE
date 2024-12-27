import { fail, redirect } from '@sveltejs/kit';
import { SECRET_BACKEND_HOST } from '$env/static/private';

export function load({ params }) {
	return {
		title: '회원가입'
	};
}

export const actions = {
	default: async ({ cookies, request }) => {

		const data = await request.formData();
		const name = data.get('name');
		const email = data.get('email');
		const contact = data.get('contact');
		const password = data.get('password');
		const confirmPassword = data.get('confirmPassword');

		try {
			const response = await fetch(`${SECRET_BACKEND_HOST}/api/sign-up`, {
				method: 'POST',
				headers: { 'Content-Type': 'application/json' },
				credentials: 'include',
				body: JSON.stringify({ name, email, contact, password, confirmPassword })
			});

			if (!response.ok) {
				const json = await response.json();
				return { ...json };
			}

		} catch (error) {
			console.error('Network error:', error);
			throw new Error('서버와의 연결에 문제가 발생했습니다.');
		}

		// 성공적으로 처리되면 리다이렉트
		redirect(303, '/sign-up/done');
	}
};
