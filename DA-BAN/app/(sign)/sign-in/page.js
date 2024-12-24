// components/SignIn.js
'use client'; // Ensure this is a client component

import Link from 'next/link';
import { useState } from 'react';
import { useActionState } from 'react';
import {authenticateUser} from "@/actions/authenticateUser"; // Adjust the import based on your setup

export default function SignIn() {
	const [email, setEmail] = useState('');
	const [password, setPassword] = useState('');

	const [state, formAction, isPending] = useActionState(authenticateUser, {
		user: null,
		error: null
	});

	const handleSubmit = (e) => {
		e.preventDefault();
		formAction(new FormData(e.target));
	};

	return (
			<>
				<img
						src="/logo.png"
						alt="logo"
						className="w-[3.9375rem] h-[1.9375rem] mt-20 mb-12 mx-auto"
				/>
				<form onSubmit={handleSubmit}>
					{/* 입력 영역 */}
					<div className="flex flex-col gap-4">
						<label className="font-medium">
							이메일
							<input
									name="email"
									type="email"
									placeholder="이메일을 입력하세요"
									required
									value={email}
									onChange={(e) => setEmail(e.target.value)}
									className="py-2 px-4 mt-2 border rounded w-full text-sm outline-none focus:border-semantic-primary-normal"
							/>
						</label>
						<label className="font-medium block">
							비밀번호
							<input
									name="password"
									type="password"
									placeholder="비밀번호를 입력하세요"
									required
									minLength={4}
									value={password}
									onChange={(e) => setPassword(e.target.value)}
									className="py-2 px-4 mt-2 border rounded w-full text-sm outline-none focus:border-semantic-primary-normal"
							/>
						</label>
					</div>

					{/* 액션 영역 */}
					<div className="mt-8 flex flex-col gap-4">
						<button
								type="submit"
								disabled={!email || !password || isPending}
								className={`px-8 py-3 rounded-md font-semibold 
              ${
										!email || !password || isPending
												? 'bg-semantic-component-fill-disabled text-semantic-labelText-disabled'
												: 'bg-semantic-button-primary text-semantic-labelText-inverse'
								}`}
						>
							로그인
						</button>

						<Link
								href="/sign-up"
								className="py-3 text-center font-semibold text-semantic-primary-normal rounded border border-semantic-primary-normal"
						>
							회원가입
						</Link>

						<ul className="flex gap-4 justify-end">
							<li><Link href={"/progress"}>계정 찾기</Link></li>
							<li><Link href={"/progress"}>비밀번호 찾기</Link></li>
						</ul>

					</div>

					{/* 에러 메시지 */}
					{state.error && (
							<div className="mt-4 text-red-500 text-center">
								{state.error}
							</div>
					)}
				</form>
			</>
	);
}
