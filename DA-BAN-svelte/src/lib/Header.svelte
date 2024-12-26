<script>
    import logo from '$assets/logo.png';
    import NavButton from '$lib/components/nav/NavButton.svelte';

</script>

<header class="flex justify-between border-b">

    <!-- logo	-->
    <a href="/DA-BAN-svelte/static" class="p-6">
        <img src={logo} alt="logo" class="w-[3.9375rem] h-[1.9375rem]" />
    </a>

    <!-- content -->
    <NavButton classes="p-6" popovertargetaction="toggle" />
</header>