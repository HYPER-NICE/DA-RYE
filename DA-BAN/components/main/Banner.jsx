// 배너를 동적으로 가져오는 기능이 필요한가?

export const Banner = () => {
	return (
		<div
				className="
				pt-16 ps-8 h-60
				select-none
			bg-[#EDEDED]
				bg-[url('/main/banner-01.png')] bg-center bg-cover bg-no-repeat
				"
		>
			<div className="font-bold text-[1.375rem] leading-[138%]">
				홀리데이<br/>
				A Winter Tea Market
			</div>
			<div className="mt-2 text-[0.875rem] leading-[157%]">
				그린티 스카우드 대원들의 <br/>
				크리스마스 티 마켓에 초대합니다.
			</div>
		</div>
	)
}