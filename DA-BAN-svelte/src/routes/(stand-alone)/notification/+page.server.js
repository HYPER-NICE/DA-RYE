import { SECRET_BACKEND_HOST } from '$env/static/private';

export async function load() {
	const response = await fetch(`${SECRET_BACKEND_HOST}/api/noti`, {
		method: 'POST',
		headers: { 'Content-Type': 'application/json' },
		credentials: 'include',
		body: JSON.stringify({ name, email, contact, password, confirmPassword })
	});

	return {
		title: '공지사항'
	};
}

export const actions = {
	default: async () => {

	}
}