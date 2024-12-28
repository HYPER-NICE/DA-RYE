<script>
	import { enhance } from '$app/forms';

	const { form } = $props();
</script>

<form method="post" use:enhance class="p-4 flex flex-col gap-6">
	<!--	data -->
	<div class="flex flex-col gap-4">
		<label class="flex flex-col">
			<span>이메일</span>
			<input type="email" name="email" id="email" placeholder="Email" required />
		</label>

		<label class="flex flex-col">
			<span>비밀번호</span>
			<input type="password" name="password" id="password" placeholder="Password" required />
		</label>
	</div>

	<!--	action -->
	<div class="flex flex-col gap-4">
		<button type="submit" class="p-3 font-semibold border rounded bg-[#09B1A7] text-white">
			로그인
		</button>

		<a href="/sign-up" class="p-3 text-[#09B1A7] text-center font-semibold border rounded border-[#09B1A7]  bg-white">
			회원가입
		</a>
	</div>
</form>