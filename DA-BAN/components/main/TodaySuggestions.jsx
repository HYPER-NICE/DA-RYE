import {TodaySuggestionItem} from "@/components/main/TodaySuggestionItem";

export function TodaySuggestions() {
	const suggestions = [
		{
			imgSrc: "/main/sample/today-suggestion-01.png",
			name: "상품명 들어가는곳",
			price: "10,000원",
			description: "50자내의 간략한 상품소개",
		},
		{
			imgSrc: "/main/sample/today-suggestion-02.png",
			name: "상품명 들어가는곳",
			price: "10,000원",
			description: "50자내의 간략한 상품소개",
		},
		{
			imgSrc: "/main/sample/today-suggestion-03.png",
			name: "상품명 들어가는곳",
			price: "10,000원",
			description: "50자내의 간략한 상품소개",
		},
		{
			imgSrc: "/main/sample/today-suggestion-04.png",
			name: "상품명 들어가는곳",
			price: "10,000원",
			description: "50자내의 간략한 상품소개",
		},
		{
			imgSrc: "/main/sample/today-suggestion-05.png",
			name: "상품명 들어가는곳",
			price: "10,000원",
			description: "50자내의 간략한 상품소개",
		},
		{
			imgSrc: "/main/sample/today-suggestion-06.png",
			name: "상품명 들어가는곳",
			price: "10,000원",
			description: "50자내의 간략한 상품소개",
		},
		{
			imgSrc: "/main/sample/today-suggestion-07.png",
			name: "상품명 들어가는곳",
			price: "10,000원",
			description: "50자내의 간략한 상품소개",
		},
		{
			imgSrc: "/main/sample/today-suggestion-08.png",
			name: "상품명 들어가는곳",
			price: "10,000원",
			description: "50자내의 간략한 상품소개",
		},
	];

	const initialSuggestions = suggestions.slice(0, 6);
	const hasMoreSuggestions = suggestions.length > 6;

	return (
	<div>
		{/* 타이틀 영역 */}
		<div className="text-[var(--basic-0,#1E1E1E)] font-pretendard text-[1.375rem] font-bold leading-[1.6225rem]">
			오늘의 추천 차
		</div>

		{/* 목록 영역 */}
		<div className="grid grid-cols-1 gap-4 md:grid-cols-3 ">
			{initialSuggestions.map((item, index) => (
					<TodaySuggestionItem
							key={index}
							imgSrc={item.imgSrc}
							name={item.name}
							price={item.price}
							description={item.description}
					/>
			))}
		</div>
		
		{/* 액션 영역 */}
		{hasMoreSuggestions && (
				<button
						className="mt-12 block w-full p-3.5 rounded border border-[var(--line-normal,#E7E7E7)]"
				>
					상품 더보기
				</button>
		)}
	</div>
	)
}