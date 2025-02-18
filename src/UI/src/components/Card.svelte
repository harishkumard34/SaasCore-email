<!--
	@component Card - A card component

	| Prop      | Type     | Default | Description
	|-----------|----------|---------|-------------
	| clazz     | string   | ''      | Additional classes to add to the card.
	| children  | Function |         | The content to be rendered inside the card.
-->

<script lang="ts">
	let {
		clazz = '',
		children
	}: {
		clazz?: string
		children: Function
	} = $props()

	let addedClass = $state(clazz)
</script>

<div class={'card shadow-xl ' + addedClass}>
	{@render children()}
</div>
