'use client';

import Link from 'next/link';
import { useActionState } from 'react';
import { authenticateUser } from '@/actions/authenticateUser'; // 위에서 만든 서버 액션

export default function SignIn() {
	const [state, formAction, isPending] = useActionState(authenticateUser, {});

	return (
			<>
				<img
						src="/logo.png"
						alt="logo"
						className="w-[3.9375rem] h-[1.9375rem] mt-20 mb-12 mx-auto"
				/>

				{/* 입력 영역 */}
				<form action={formAction} className="flex flex-col gap-4">
					<label className="font-medium">
						이메일
						<input
								name="email"
								type="email"
								required
								className="py-2 px-4 mt-2 border rounded w-full text-sm outline-none focus:border-semantic-primary-normal"
						/>
					</label>

					<label className="font-medium">
						비밀번호
						<input
								name="password"
								type="password"
								required
								minLength={4}
								className="py-2 px-4 mt-2 border rounded w-full text-sm outline-none focus:border-semantic-primary-normal"
						/>
					</label>

					<button
							type="submit"
							disabled={isPending}
							className={`px-8 py-3 mt-4 rounded-md font-semibold ${
									isPending
											? 'bg-semantic-component-fill-disabled text-semantic-labelText-disabled'
											: 'bg-semantic-button-primary text-semantic-labelText-inverse'
							}`}
					>
						{isPending ? '로그인 중...' : '로그인'}
					</button>
				</form>

				{/* 3) 에러 메시지 출력 */}
				{state?.error && (
						<div className="mt-4 text-red-500 text-center">
							{state.error}
						</div>
				)}

				{/* 회원가입 링크, 기타 링크 */}
				<div className="mt-8 flex flex-col gap-4">
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
			</>
	);
}
