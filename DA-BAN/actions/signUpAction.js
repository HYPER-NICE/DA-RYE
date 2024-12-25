'use server';

import {redirect} from 'next/navigation';

export async function signUpAction(prevState, queryData) {
	const name = queryData.get('name');
	const email = queryData.get('email');
	const contact = queryData.get('contact');
	const password = queryData.get('password');
	const confirmPassword = queryData.get('confirmPassword');

	prevState.name = name;
	prevState.email = email;
	prevState.contact = contact;
	prevState.password = password;
	prevState.confirmPassword = confirmPassword;

	// 비밀번호 확인 유효성 검사
	if (password !== confirmPassword) {
		return {...prevState, valid : {confirmPassword : '비밀번호가 일치하지 않습니다.'}};
	}

	try {
		const response = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/api/sign-up`, {
			method: 'POST',
			headers: {'Content-Type': 'application/json'},
			credentials: 'include',
			body: JSON.stringify({name, email, contact, password, confirmPassword}),
		});

		if (!response.ok) {
			return { ...prevState, valid: await response.json() };
		}

	} catch (error) {
		console.error('Network error:', error);
		throw new Error(error);
	}

	redirect('/sign-in');
}
