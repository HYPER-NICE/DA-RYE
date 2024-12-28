<script>
	import Header from '$lib/Header.svelte';

	let { children } = $props();
</script>

<main>
	<Header />
	{@render children()}
</main>
