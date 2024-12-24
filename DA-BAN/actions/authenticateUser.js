'use server'

import { redirect } from 'next/navigation';
import {cookies} from "next/headers";

export async function authenticateUser(prevState, queryData) {
	const email = queryData.get('email');
	const password = queryData.get('password');

	try {
		const response = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/api/sign-in`, {
			method: 'POST',
			headers: { 'Content-Type': 'application/json' },
			credentials: 'include',
			body: JSON.stringify({ email, password })
		});

		if (!response.ok) {
			return { ...prevState, error: '이메일 또는 비밀번호가 일치하지 않습니다.' };
		}

		// 응답 쿠키에서 JSESSIONID만 읽음
		const responseCookies = response.headers.get('set-cookie');
		if (responseCookies) {
			const jsessionCookie = responseCookies
					.split(',')
					.find((cookie) => cookie.trim().startsWith('JSESSIONID='));

			if (jsessionCookie) {
				const [keyValue, ...options] = jsessionCookie.split(';');
				const [key, value] = keyValue.trim().split('=');

				// JSESSIONID만 클라이언트에 전달
				const cookieStore = await cookies();
				cookieStore.set(key, value, {
					path: '/', // 기본 경로 설정
					httpOnly: options.some((opt) => opt.trim() === 'HttpOnly'),
					secure: options.some((opt) => opt.trim() === 'Secure'),
				});
			}
		}
	} catch (error) {
		console.error('Network error:', error);
		return { ...prevState, error: '서버와의 연결에 문제가 발생했습니다.' };
	}

	// cache

	redirect('/');
}
