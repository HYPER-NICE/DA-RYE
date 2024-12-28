<script>
	import {page} from '$app/state';
	import done from '$assets/done.svg';
	import { goto } from '$app/navigation';
	import { onMount } from 'svelte';

	onMount(() => {
		setTimeout(() => {
			goto('/', { replaceState: true });
		}, 5000);
	});
</script>

<div class="h-[600px] flex flex-col gap-2  justify-center">

	<!--	icon -->
	<div class="flex flex-col gap-6">
		<img
			class="mx-auto"
			src={done}>
		<div class="text-center text-red-500 font-semibold">
			{page.status}
		</div>
	</div>

	<div class="text-center font-bold text-2xl">
		{page.error.message}
	</div>

	<div>
		{page.error.code}
	</div>

	<div class="font-light text-sm text-center">조금 뒤에 이동합니다.</div>
</div>