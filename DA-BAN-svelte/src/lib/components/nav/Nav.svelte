<script>
	/** @type {HTMLDialogElement} */
	let dialog;

	export function showModal() {
		dialog?.showModal();
	}
</script>

<dialog bind:this={dialog}
				id="nav" class="
				top-0
				bottom-0
				blockff
				my-0
				bg-amber-200
				w-[200px] h-full
				backdrop:bg-red-500" popover="manual">
		<div class="flex flex-col gap-4">
			<a href="/#">홈</a>
			<a href="/#">카테고리</a>
			<a href="/#">검색</a>
			<a href="/#">장바구니</a>
			<a href="/#">마이페이지</a>
	</div>
</dialog>
