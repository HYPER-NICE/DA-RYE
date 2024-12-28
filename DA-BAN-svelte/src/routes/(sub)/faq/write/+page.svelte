<script>
	import { page } from '$app/state';
</script>

{#snippet subCategory(data)}
	<label class={`flex-1 p-4  cursor-pointer ${data.id == page.data.paramSubCategory && "border-b-2 border-black"}`}>
		<input
			type="radio"
			name="sub-category"
			value={data.id}
			required
		/>
		<span class="font-semibold">{data.name}</span>
	</label>
{/snippet}

<form method="POST" class="p-4">
	<div class="mb-4">
		<div>카테고리 </div>
		{#each page.data.subCategories as item}
			{@render subCategory(item)}
		{/each}
	</div>

	<div class="flex flex-col gap-4">
		<input
			type="text"
			name="title"
			placeholder="제목"
			class="p-4 border border-gray-300 rounded-lg"
			required
		/>
		<textarea
			name="content"
			placeholder="내용"
			class="p-4 border border-gray-300 rounded-lg"
			required
		></textarea>
		<div class="flex justify-end gap-2">
			<button
				type="submit"
				class="p-4 bg-blue-500 text-white rounded hover:bg-blue-600">
				제출하기
			</button>
		</div>
	</div>
</form>
