export function Footer() {
	return (
			<div>
				<div
						className="my-8 mx-4 flex flex-col gap-6 text-[#737B8C]"
				>
					{/* 타이틀 영역 */}
					<div className="flex flex-col gap-2">
						<a target="_blank" href="https://github.com/HYPER-NICE/DA-RYE" className="text-[1.125rem] font-bold">Project - 다례</a>
						<a target="_blank"  href="https://github.com/HYPER-NICE" className="text-[0.875rem]">Team - HYPER NICE</a>
					</div>

					<hr className="border-[#737B8C]"/>

					{/* 서브 영역 */}
					<div className="flex">

						<div className="w-1/2 flex flex-col gap-1">
							<div className="text-[0.875rem]">고객센터</div>
							<div className="text-[0.875rem] font-bold">help@darye.com</div>
							<div>080-8888-8888</div>
							<div>
								<div>평일 09:00 ~ 18:00</div>
								<div>토요일 09:00 ~ 13:00</div>
								<div>주말 / 공휴일 휴무</div>
							</div>
						</div>

						<div
								className="border-l border-[#737B8C] mx-4"
						></div>

						<div className="w-1/2 flex flex-col gap-1">
							<div>총괄 : 김태희</div>
							<div>개발 : 김동민</div>
							<div>개발 : 김가영</div>
							<div>개발 : 김우연</div>
							<div>개발 : 강민우</div>
							<div>개발 : 정영규</div>
						</div>

					</div>
				</div>
			</div>
	)
}