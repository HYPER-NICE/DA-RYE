<script>
	import { i18n } from '$lib/i18n';
	import { ParaglideJS } from '@inlang/paraglide-sveltekit';
	import '../app.css';
	import 'pretendard/dist/web/variable/pretendardvariable-dynamic-subset.css';

	let { children } = $props();
</script>

<ParaglideJS {i18n}>
	<div class="w-[600px] min-h-[600px] mx-auto border">
		{@render children()}
	</div>
</ParaglideJS>
