<script>
	const menus = [
		{
			title: '상품',
			items: ['녹차/말차', '명차', '말효차', '홍차', '허브차']
		},
		{
			title: '마이페이지',
			items: ['나의 쇼핑', '리뷰작성', '문의내역', '설정']
		},
		{
			title: '고객센터',
			items: ['공지사항', 'FAQ', '1:1 문의']
		}
	];
</script>

{#snippet menu(data)}
	<div class="border-t">
		<div class="p-4 text-lg font-bold ">{data.title}</div>
		<ul>
			{#each data.items as item}
				<li><a href="#" class="p-4  block font-medium hover:bg-gray-200 transition-colors duration-300">{item}</a></li>
			{/each}
		</ul>
	</div>
{/snippet}

<div class="p-4">
	<!--	인증 액션 -->
	<div class="mb-4 flex gap-4">
		<a href="/sign-in" class="flex-1 p-3 text-center rounded text-white bg-[#09B1A7]">로그인</a>
		<a
			class="flex-1 p-3 text-center rounded border border-[#09B1A7] bg-white"
			href="/sign-up"
		>회원가입</a>
	</div>

	{#each menus as data}
		{@render menu(data)}
	{/each}
</div>