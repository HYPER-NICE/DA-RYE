<script>
	import {page} from '$app/state';
</script>

<div class="p-4">

	<!--	title -->
	<div class="flex flex-col gap-4 border-b py-6">
		<div class="flex gap-2">
			<div
				class={`px-6 py-1 rounded-full border text-lg font-medium`}>{page.data.board.subCategoryName}</div>
			<div class="content-center text-gray-500">{page.data.board.regDateFormatted}</div>
		</div>
		<div class="text-4xl font-semibold">{page.data.board.title}</div>
	</div>

	<!--	content -->
	<div class="mt-16 text-2xl">
		{page.data.board.content}

		{#if page.data.board.reply}
			<div class="mt-16 text-2xl">
				<div
					class="before:content-['A.'] before:font-bold before:mr-2 before:text-2xl before:block p-4 bg-gray-200"
				>
				{page.data.board.reply.content}
				</div>
			</div>
		{/if}
	</div>

</div>