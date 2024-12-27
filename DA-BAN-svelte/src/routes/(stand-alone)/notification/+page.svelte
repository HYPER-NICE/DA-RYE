<script>
	const { data } = $props();
	const boards = data.boards;
</script>

{#snippet thread(board)}
	<a href={`/notification/${board.id}`} class="flex flex-col gap-4 border-b py-6">
		<div class="flex gap-2">
			<div
				class={`px-6 py-1 rounded-full border ${board.categoryId === 1 && "border-red-500 text-red-500 text-lg" } font-medium`}>{board.categoryName}</div>
			<div class="content-center text-gray-500">{board.regDateFormatted}</div>
		</div>
		<div class="text-2xl">{board.title}</div>
	</a>
{/snippet}

<div class="p-4">
	<!-- filter -->
	<form>

	</form>

	<!-- content -->

	<div >
	{#each boards as board}
		{@render thread(board)}
	{/each}
	</div>
</div>