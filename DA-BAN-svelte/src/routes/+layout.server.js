import { SECRET_BACKEND_HOST } from '$env/static/private';

export async function load({ cookies }) {
	let role;
	try {
		const response = await fetch(`${SECRET_BACKEND_HOST}/api/members/me`, {
			headers: {
				Cookie: `JSESSIONID=${cookies.get('JSESSIONID')}`
			}
		});
		const member = await response.json();
		role = member.role;
	} catch (error) {
		role = 'GUEST';
	}

	return {
		role
	};
}