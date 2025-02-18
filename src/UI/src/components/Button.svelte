<!--
	@component Button - A simple button component

	| Prop           | Type      | Default  | Description
	|----------------|-----------|----------|-------------
	| type           | string    | primary  | The type of button.Should be one of: ``'primary', 'secondary', 'cancel'``
	| clazz          | string    |          | Additional classes to add to the button.
	| children       | Function  |          | The content of the button.
	| isCircle       | boolean   | false    | Whether the button is a circle or not.
	| isDisabled     | boolean   | false    | Whether the button is disabled or not.
	| isOutlined     | boolean   | false    | Whether the button is outlined or not.
	| isAnchor       | boolean   | false    | Whether the button is an anchor or button.
	| href           | string    |          | href of the anchor.
	| size           | string    | md       | The size of the button. Should be one of: ``'sm', 'md', 'lg', 'xs', 'wide'``
	| onClick        | Function  |          | The function that will be called when the button is clicked

-->

<script lang="ts">
	import type { MouseEventHandler } from 'svelte/elements'

	let {
		type = 'primary',
		clazz,
		children,
		isCircle,
		isDisabled,
		isOutlined,
		isAnchor = false,
		href,
		size = 'md',
		onClick
	}: {
		isAnchor?: boolean
		type?: 'primary' | 'secondary' | 'cancel'
		size?: 'xs' | 'sm' | 'md' | 'lg' | 'wide'
		clazz?: string
		isCircle?: boolean
		isDisabled?: boolean
		isOutlined?: boolean
		href?: string
		onClick?: MouseEventHandler<HTMLButtonElement>
		children: Function
	} = $props()

	let addedClass = $state(clazz)
	function setClass() {
		switch (type) {
			case 'primary':
				addedClass += ' btn-primary'
				break
			case 'secondary':
				addedClass += ' btn-secondary '
				break
			case 'cancel':
				addedClass += ' btn-neutral'
				break
		}
		switch (size) {
			case 'xs':
				addedClass += ' btn-xs'
				break
			case 'sm':
				addedClass += ' btn-sm'
				break
			case 'md':
				addedClass += ' btn-md'
				break
			case 'lg':
				addedClass += ' btn-lg'
				break
			case 'wide':
				addedClass += ' btn-wide'
				break
		}
		if (isCircle) {
			addedClass += ' btn-circle'
		}
		if (isDisabled) {
			addedClass += ' btn-disabled'
		}
		if (isOutlined) {
			addedClass += ' btn-outline'
		}
	}

	setClass()
</script>

{#if isAnchor}
	<a class={'btn ' + addedClass} {href}>
		{@render children()}
	</a>
{:else}
	<button class={'btn ' + addedClass} onclick={onClick} > 
		{@render children()}
	</button>
{/if}
