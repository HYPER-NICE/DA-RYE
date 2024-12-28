<script>
	import TodaySuggestionItem from '$lib/main/TodaySuggestionItem.svelte';

	// 이미지를 동적으로 가져오기
	const images = import.meta.glob('$assets/main/sample/today-suggestion-*.png', { eager: true });

	// 더미 데이터와 이미지 매핑
	const suggestions = [];
	const dummySuggestions = [
		{
			name: '상품명 들어가는곳',
			price: '10,000원',
			description: '50자내의 간략한 상품소개'
		},
		{
			name: '상품명 들어가는곳',
			price: '10,000원',
			description: '50자내의 간략한 상품소개'
		},
		{
			name: '상품명 들어가는곳',
			price: '10,000원',
			description: '50자내의 간략한 상품소개'
		},
		{
			name: '상품명 들어가는곳',
			price: '10,000원',
			description: '50자내의 간략한 상품소개'
		},
		{
			name: '상품명 들어가는곳',
			price: '10,000원',
			description: '50자내의 간략한 상품소개'
		},
		{
			name: '상품명 들어가는곳',
			price: '10,000원',
			description: '50자내의 간략한 상품소개'
		},
		{
			name: '상품명 들어가는곳',
			price: '10,000원',
			description: '50자내의 간략한 상품소개'
		},
		{
			name: '상품명 들어가는곳',
			price: '10,000원',
			description: '50자내의 간략한 상품소개'
		}
	];

	// 이미지를 더미 데이터와 매핑
	let i = 0;
	Object.entries(images).forEach(([key, value]) => {
		suggestions.push({
			imgSrc: key, // 동적으로 불러온 이미지 경로
			...dummySuggestions[i++] // 더미 데이터 매핑
		});
	});

	// 초기 표시할 항목과 더보기 버튼 상태
	const initialSuggestions = suggestions.slice(0, 6);
	const hasMoreSuggestions = suggestions.length > 6;
</script>

<div>
	<!-- 타이틀 영역 -->
	<h2 class="text-[1.375rem] font-bold mb-4">
		오늘의 추천 차
	</h2>

	<!-- 목록 영역 -->
	<div class="grid grid-cols-1 gap-x-4 gap-y-8 md:grid-cols-3">
		{#each initialSuggestions as item (item.imgSrc)}
			<TodaySuggestionItem
				imgSrc={item.imgSrc}
				name={item.name}
				price={item.price}
				description={item.description}
			/>
		{/each}
	</div>

	<!-- 액션 영역 -->
	<button class="mt-12 w-full p-3.5 rounded border">
		상품 더보기
	</button>
</div>
