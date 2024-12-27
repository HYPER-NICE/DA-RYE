<script>
	import logo from '$assets/logo.png';
	import NavButton from '$lib/components/nav/NavButton.svelte';
	import { page } from '$app/state';

	const back = () => {
		history.back();
	};
</script>

<header class="flex justify-between">
	<div class="flex gap-4">
	{#if page.url.pathname !== '/'}
		<a href="#" onclick={back} class="p-6">
			<svg xmlns="http://www.w3.org/2000/svg" width="28" height="28" viewBox="0 0 28 28" fill="none">
				<path
					d="M25.6021 15.3515C26.282 15.3515 26.8333 14.8016 26.8333 14.1234C26.8333 13.4451 26.282 12.8953 25.6021 12.8953L6.41312 12.8953L13.7335 5.59645C14.2143 5.11686 14.2143 4.33929 13.7335 3.85969C13.2527 3.3801 12.4731 3.3801 11.9923 3.85969L2.69386 13.1316C2.21305 13.6112 2.21305 14.3888 2.69386 14.8684L11.9923 24.1403C12.4731 24.6199 13.2527 24.6199 13.7335 24.1403C14.2143 23.6607 14.2143 22.8831 13.7335 22.4035L6.6605 15.3515L25.6021 15.3515Z"
					fill="#1E1E1E" />
			</svg>
		</a>
	{:else}
		<!-- logo	-->
		<a href="/" class="p-6">
			<img src={logo} alt="logo" class="w-[3.9375rem] h-[1.9375rem]" />
		</a>
	{/if}
	<div class="content-center font-bold text-2xl" >{page?.data?.title}</div>
	</div>

	<!-- content -->
	<NavButton classes="p-6" popovertargetaction="toggle" />
</header>