export function Menu() {
	return (
			<div className="absolute left-0 top-0 bg-basic h-full w-9/12 py-6 px-5">
				{/* 타이틀 영역 */}
				<div>
					<div className="flex justify-between">
						{/* 로고 */}
						<a href="/">
							<img src="/logo.png" alt="logo" className="w-[3.9375rem] h-[1.9375rem]"/>
						</a>
						<svg xmlns="http://www.w3.org/2000/svg" width="28" height="29" viewBox="0 0 28 29" fill="none">
							<path
									d="M12.3501 14.5L2.67505 24.175L4.32496 25.8249L14 16.1499L23.675 25.825L25.3249 24.175L15.6499 14.5L25.3249 4.82496L23.675 3.17505L14 12.8501L4.32497 3.17507L2.67505 4.82498L12.3501 14.5Z"
									fill="#1E1E1E"/>
						</svg>
					</div>
				</div>

				{/* 사인인 사인업 */}
				<div className="flex w-full mt-8 gap-2">
					<a className="py-3 rounded-md bg-primary w-1/2 text-basic text-center">로그인</a>
					<a className="py-3 rounded-md border border-primary bg-basic text-primary w-1/2 text-center">회원가입</a>
				</div>

				<hr className="border-[#737B8C] mt-6"/>

				{/* 서브 영역 */}
				<div></div>
			</div>
	)
}