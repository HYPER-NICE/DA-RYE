<script>
	import { page } from '$app/state';

	const menus = [
		{
			title: '홈',
			items: [{ name: '홈', href: '/' }]
		},
		{
			title: '상품',
			items: [{ name: '전체 상품', href: '/products' }, { name: '베스트 상품', href: '/best' }, { name: '신상품', href: '/new' }]
		},
		{
			title: '마이페이지',
			items: [{ name: '주문내역', href: '/order' }, { name: '배송지 관리', href: '/address' }, {
				name: '개인정보 수정',
				href: '/profile'
			}]
		},
		{
			title: '고객센터',
			items: [{ name: '공지사항', href: '/notification' }, { name: '자주 묻는 질문', href: '/faq' }, {
				name: '문의',
				href: '/inquiry'
			}]
		}
	];
</script>

{#snippet menu(data)}
	<div class="border-t">
		<div class="p-4 text-lg font-bold ">{data.title}</div>
		<ul>
			{#each data.items as item}
				<li><a href={item.href}
							 class="p-4  block font-medium hover:bg-gray-200 transition-colors duration-300">{item.name}</a></li>
			{/each}
		</ul>
	</div>
{/snippet}

<div class="p-4">
	{#if page.data.member}
		<!--	포인트	-->
		<div class="mb-4 flex gap-4">
			<div class="flex-1 p-3 text-center rounded text-white bg-[#09B1A7]">
				<span>포인트 </span>
				<span>{page.data.member.point}</span></div>
			<form method="POST"
				class="flex-1 p-3 text-center rounded border border-[#09B1A7] bg-white">
				<button type="submit" class="w-full h-full font-semibold text-center">로그아웃</button>
			</form>
		</div>
	{:else}
		<!--	인증 액션 -->
		<div class="mb-4 flex gap-4">
			<a href="/sign-in" class="flex-1 p-3 text-center rounded text-white bg-[#09B1A7]">로그인</a>
			<a
				class="flex-1 p-3 text-center rounded border border-[#09B1A7] bg-white"
				href="/sign-up"
			>회원가입</a>
		</div>
	{/if}

	{#each menus as data}
		{@render menu(data)}
	{/each}
</div>