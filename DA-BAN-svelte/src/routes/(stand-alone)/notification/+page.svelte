<script>
	import { page } from '$app/state';
	import { enhance } from '$app/forms';

	/** @type HTMLFormElement */
	let form;

	const handleSubCategory = () => {
		if (form) form.requestSubmit();
	};
</script>

{#snippet subCategory(data)}
	<label class={`flex-1 p-4  cursor-pointer ${data.id == page.data.paramSubCategory && "border-b-2 border-black"}`}>
		<input
			class="hidden"
			onchange={handleSubCategory} type="radio" name="sub-category" value={data.id}>
		<span class="font-semibold">{data.name}</span>
	</label>
{/snippet}

{#snippet thread(board)}
	<a href={`/notification/${board.id}`} class="flex flex-col gap-4 border-b py-6">
		<div class="flex gap-2">
			<div
				class={`px-6 py-1 rounded-full border ${board.subCategoryId === 1 && "border-red-500 text-red-500" } text-lg font-medium`}>{board.subCategoryName}</div>
			<div class="content-center text-gray-500">{board.regDateFormatted}</div>
		</div>
		<div class="text-2xl">{board.title}</div>
	</a>
{/snippet}


<div class="p-4">
	<!-- filter -->
	<form bind:this={form}>
		<div class="px-16 text-center flex justify-around">
			{#each page.data.subCategories as item}
				{@render subCategory(item)}
			{/each}
		</div>

		<div class="flex mt-4 gap-2">
		<input type="search" name="keyword" placeholder="검색어를 입력하세요"
					 class="flex-1 p-4 border border-gray-300 rounded-lg"
		>
		<button type="submit" class="p-4 content-center hover:bg-gray-200 rounded"
		>검색</button>
		</div>
	</form>

	<!-- content -->
	<div>
		{#each page.data.boards as board}
			{@render thread(board)}
		{/each}
	</div>
</div>