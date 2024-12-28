import { fail, redirect } from '@sveltejs/kit';
import { SECRET_BACKEND_HOST } from '$env/static/private';
import { mockFaqSubCategories as mockSubCategories } from '$lib/dummy/mockBoard.js';

/** @type {import('./$types').PageServerLoad} */
export async function load({ cookies }) {
	const JSESSIONID = cookies.get('JSESSIONID');
	if (!JSESSIONID) {
		fail(304, { message: '사인인이 필요합니다.' });
	};

	const subCategories = mockSubCategories
		.filter((subCategory) => subCategory.id != 0)
		.map((subCategory) => ({
			...subCategory
		}));

	return {
		subCategories
	}
}

export const actions = {
	default: async ({ cookies, request }) => {
    // 인증 검사
		const JSESSIONID = cookies.get('JSESSIONID');
		if (!JSESSIONID) {
			return fail(304, { message: '사인인이 필요합니다.' });
		};

		const formData = await request.formData();
		const title = formData.get('title');
		const content = formData.get('content');
		const subCategory = formData.get('sub-category');

		const postFaqUrl = new URL(`${SECRET_BACKEND_HOST}/api/faq-board`);

		try {
			const response = await fetch(postFaqUrl, {
				method: 'POST',
				headers: { 'Content-Type': 'application/json',
					Cookie: `JSESSIONID=${JSESSIONID}` // 올바른 쿠키 헤더 형식
					},
				body: JSON.stringify({ title, content, subCategory })
			});

			if (!response.ok) {
				const json = await response.json();
				return { ...json };
			}

		} catch (error) {
			console.error('Network error:', error);
			throw new Error('서버와의 연결에 문제가 발생했습니다.');
		}

		redirect(303, '/faq');
	}
};