<script>
	import { i18n } from '$lib/i18n';
	import { ParaglideJS } from '@inlang/paraglide-sveltekit';
	import '../app.css';
	import Header from '$lib/Header.svelte';
	import 'pretendard/dist/web/variable/pretendardvariable-dynamic-subset.css';
	import Footer from '$lib/common/Footer.svelte';
	import Nav from '$lib/components/nav/Nav.svelte';

	let { children } = $props();
</script>

<ParaglideJS {i18n}>
	<div class="w-[600px] min-h-[600px] mx-auto border">
		<main>
			<Header />
			{@render children()}
		</main>
		<Footer />
	</div>
	<Nav />
</ParaglideJS>
