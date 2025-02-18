<script lang="ts">
	let {
		clazz,
		placeholder,
		children,
		value = $bindable(""),
	}: {
		clazz?: string;
		placeholder?: string;
		children: Function;
		value?: string;
	} = $props();

	let addedClass = $state(clazz);
</script>

<label class="form-control w-full max-w-xs">
	<div class="label">
		<span class="label-text">{@render children()}</span>
	</div>
	<textarea
		bind:value
		class="textarea textarea-bordered ${addedClass}"
		{placeholder}
	></textarea>
</label>
