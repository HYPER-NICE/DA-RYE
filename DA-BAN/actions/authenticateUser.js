export async function authenticateUser(currentState, formData) {
	const email = formData.get('email');
	const password = formData.get('password');

	try {
		debugger;
		const test = process.env.NEXT_PUBLIC_API_URL;
		const response = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/api/sign-in`, {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			credentials: 'include',
			body: JSON.stringify({ email, password })
		});

		const result = await response.json();

		if (response.ok) {
			return { ...currentState, user: result.user, error: null };
		} else {
			return { ...currentState, error: result.message };
		}
	} catch (error) {
		return { ...currentState, error: '서버와의 연결에 문제가 발생했습니다.' };
	}
}
