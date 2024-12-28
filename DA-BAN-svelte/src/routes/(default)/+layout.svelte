<script>
	import Header from '$lib/Header.svelte';
	import Footer from '$lib/common/Footer.svelte';

	let { children } = $props();
</script>

<main>
	<Header />
	{@render children()}
</main>
<Footer />
