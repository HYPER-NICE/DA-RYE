import { SECRET_BACKEND_HOST } from '$env/static/private';

const pageData = {
	title: '공지사항',
}

/** @type {import('./$types').LayoutServerLoad} */
export async function load({cookies}) {

	const jsessionId = cookies.get('JSESSIONID') ?? ''; // 없는 경우 대비
	const cookieHeader = `JSESSIONID=${jsessionId}`;

	const response = await fetch(`${SECRET_BACKEND_HOST}/api/notification-board`, {
		method: 'POST',
		headers: { 'Content-Type': 'application/json',
			'Cookie': {cookieHeader}
		},
		credentials: 'include',
	});

	console.log(response);
	if (!response.ok) {
		return {
			...pageData,
			status: response.status,
			error: await response.text()
		};
	}

	return {
		...pageData,
	};
}

export const actions = {
	default: async () => {

	}
}