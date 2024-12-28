<script>
	import { applyAction, enhance } from '$app/forms';
	import { goto, replaceState } from '$app/navigation';

	// 필드별 유효성 안내 메시지
	let nameTitle = "이름은 2~20자의 한글 또는 영문이어야 합니다.";
	let emailTitle = "유효한 이메일 주소를 입력하세요.";
	let contactTitle = "연락처는 '010-1234-5678' 형식이어야 합니다.";
	let passwordTitle = "패스워드는 8자 이상이어야 합니다.";
	let passwordConfirmTitle = "동일한 패스워드를 한번 더 입력하세요.";

	/** @type {{ form: import('./$types').ActionData }} */
	let { form } = $props();
</script>

<form method="POST"
			use:enhance={() => {
				return async ({result, update}) => {
					if (result.type === 'redirect') {
						await goto(result.location, {replaceState: true});
					}

					await applyAction(result);
				}
			}}
			class="p-4 flex flex-col gap-6">
	<div class="flex flex-col gap-4">
		<!-- 이름 -->
		<label class="flex flex-col gap-1">
			<span class="font-medium after:content-['(필수)'] after:ml-1 after:text-[#09B1A7]">이름</span>
			<input
				type="text"
				name="name"
				id="name"
				placeholder="이름을 입력하세요"
				required
				title={nameTitle}
			/>
			<!-- 에러 메시지 -->
			{#if form?.name}
				<p class="text-red-500 text-sm">{form.name}</p>
			{/if}
		</label>

		<!-- 이메일 -->
		<label class="flex flex-col gap-1">
			<span class="after:content-['(필수)'] after:ml-1 after:text-[#09B1A7]">이메일</span>
			<input
				type="email"
				name="email"
				id="email"
				placeholder="이메일을 입력하세요"
				required
				title={emailTitle}
			/>
			<!-- 에러 메시지 -->
			{#if form?.email}
				<p class="text-red-500 text-sm">{form.email}</p>
			{/if}
		</label>

		<!-- 연락처 -->
		<label class="flex flex-col gap-1">
			<span class="after:content-['(필수)'] after:ml-1 after:text-[#09B1A7]">연락처</span>
			<input
				type="tel"
				name="contact"
				id="contact"
				placeholder="연락처를 입력하세요 (예: 010-1234-5678)"
				required
				title={contactTitle}
			/>
			<!-- 에러 메시지 -->
			{#if form?.contact}
				<p class="text-red-500 text-sm">{form.contact}</p>
			{/if}
		</label>

		<!-- 패스워드 -->
		<label class="flex flex-col gap-1">
			<span class="after:content-['(필수)'] after:ml-1 after:text-[#09B1A7]">패스워드</span>
			<input
				type="password"
				name="password"
				id="password"
				placeholder="패스워드를 입력하세요 (8자 이상)"
				required
				minlength="8"
				title={passwordTitle}
			/>
			<!-- 에러 메시지 -->
			{#if form?.password}
				<p class="text-red-500 text-sm">{form.password}</p>
			{/if}
		</label>

		<!-- 패스워드 확인 -->
		<label class="flex flex-col gap-1">
			<span class="after:content-['(필수)'] after:ml-1 after:text-[#09B1A7]">패스워드 확인</span>
			<input
				type="password"
				name="confirmPassword"
				id="confirmPassword"
				placeholder="패스워드를 다시 입력하세요"
				required
				title={passwordConfirmTitle}
			/>
			<!-- 에러 메시지 -->
			{#if form?.confirmPassword}
				<p class="text-red-500 text-sm">{form.confirmPassword}</p>
			{/if}
		</label>
	</div>

	<!-- 동작 버튼 -->
	<div class="flex flex-col gap-4">
		<button type="submit" class="p-3 font-semibold border rounded bg-[#09B1A7] text-white">
			가입하기
		</button>
	</div>
</form>
