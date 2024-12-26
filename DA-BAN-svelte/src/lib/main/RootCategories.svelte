<script>
	import RootCategoryItem from '$lib/main/RootCategoriItem.svelte';

	const images = import.meta.glob('$assets/main/sample/root-categories/*.png', { eager: true });

	const categories = [];
	const dummyCategories = [
		"녹차/말차", "명차", "발효차", "홍차", "허브티"
	]

	let i = 0;
	Object.entries(images).forEach(([key, value]) => {
		categories.push({
			src: key,
			alt: key,
			name: dummyCategories[i++]
		});
	});
</script>

<div class="flex justify-between items-center self-stretch">
	{#each categories as category (category.src)}
		<RootCategoryItem
			src={category.src}
			alt={category.alt}
			name={category.name}
		/>
	{/each}
</div>
