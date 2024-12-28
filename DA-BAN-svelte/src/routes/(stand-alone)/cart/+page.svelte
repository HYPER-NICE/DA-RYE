<script>
	const carts = [
		{
			productName: 'name'
		}];
</script>

{#snippet cartItem(data)}
	<div>{data.productName}</div>
{/snippet}

{#each carts as cart}
	{@render cartItem(cart)}
{/each}
