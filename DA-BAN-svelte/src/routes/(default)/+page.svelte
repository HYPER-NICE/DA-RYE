<script>
	import banner01 from '$assets/main/banner-01.png';
	import RootCategories from '$lib/main/RootCategories.svelte';
	import Banner from '$lib/main/Banner.svelte';
	import TodaySuggestions from '$lib/main/TodaySuggestions.svelte';
</script>

<!---->
<Banner />

<!-- content -->
<div class="py-12 px-4 flex flex-col gap-12">
<RootCategories />
<TodaySuggestions />
</div>
