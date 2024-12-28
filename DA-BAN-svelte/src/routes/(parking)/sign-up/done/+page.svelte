<script>
	import done from '$assets/done.svg';
	import { goto } from '$app/navigation';
	import { onMount } from 'svelte';

		onMount(() => {
			setTimeout(() => {
				goto('/sign-in', { replaceState: true });
			}, 5000);
		});
</script>

<div class="h-[600px] flex flex-col gap-2  justify-center">

	<!--	icon -->
	<div class="flex flex-col gap-6">
		<img
			class="mx-auto"
			src={done}>
		<div class="text-center text-[#09B1A7] font-semibold">
			가입완료!
		</div>
	</div>

	<div class="text-center font-bold text-2xl">
		다례에 오신것을<br />
		환영합니다.
	</div>

	<div class="font-light text-sm text-center">조금 뒤에 이동합니다.</div>
</div>