<script>
	export let src;
	export let alt;
	export let name;
	export let href = '#'; // 기본값을 설정하여 href가 없을 경우도 처리
</script>

<a
	href={href}
	class="group flex flex-col items-center gap-3"
>
	<img
		src={src}
		alt={alt}
		class="w-[5.75rem] h-[5.75rem] rounded-full bg-lightgray bg-center bg-cover bg-no-repeat transition-transform duration-300 transform group-hover:scale-110"
	/>
	<div class="select-none">{name}</div>
</a>
