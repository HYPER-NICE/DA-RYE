<script>
	/**
	 * @property {String} [classes] - 버튼의 클래스 이름.
	 * @property {'toggle' | 'hide' | 'show' | null | undefined} [popovertargetaction] - popovertarget의 동작 방식.
	 */
	const { classes, popovertargetaction } = $props();

	/** @type {HTMLButtonElement} */
	let button;
	/** @type {HTMLDialogElement} */
	let dialog;
</script>

<button bind:this={button} onclick={() => dialog.showModal()} >button</button>
<dialog bind:this={dialog}>asdfasdf</dialog>

<button popovertarget="nav" {popovertargetaction} class={classes}>
	<svg xmlns="http://www.w3.org/2000/svg" width="28" height="29" viewBox="0 0 28 29" fill="none">
		<!-- 첫 번째 줄 -->
		<path fill-rule="evenodd" clip-rule="evenodd"
					d="M3 8.5C3 7.94772 3.44772 7.5 4 7.5H24C24.5523 7.5 25 7.94772 25 8.5C25 9.05228 24.5523 9.5 24 9.5H4C3.44772 9.5 3 9.0523 3 8.5Z"
					fill="#1E1E1E" />
		<!-- 두 번째 줄 -->
		<path fill-rule="evenodd" clip-rule="evenodd"
					d="M3 14.5C3 13.9477 3.44772 13.5 4 13.5H24C24.5523 13.5 25 13.9477 25 14.5C25 15.0523 24.5523 15.5 24 15.5H4C3.44772 15.5 3 15.0523 3 14.5Z"
					fill="#1E1E1E" />
		<!-- 세 번째 줄 -->
		<path fill-rule="evenodd" clip-rule="evenodd"
					d="M3 20.5C3 19.9477 3.44772 19.5 4 19.5H24C24.5523 19.5 25 19.9477 25 20.5C25 21.0523 24.5523 21.5 24 21.5H4C3.44772 21.5 3 21.0523 3 20.5Z"
					fill="#1E1E1E" />
	</svg>
</button>
