<script lang="ts">
	import type { Snippet } from "svelte";
	import Button from "./Button.svelte";

	let {
		children,
		buttonname,
		icon, 
		clazzcolour,
		clazzbuttonin,
		clazzbuttonout,
		onClose,
		showModal = $bindable(), // Mark showModal as bindable
		hideButton = false // New prop to control button visibility
	}: { 
		children: Function; 
		buttonname?: string; 
		icon?: Snippet; 
		clazzcolour?: string; 
		clazzbuttonin?: string; 
		clazzbuttonout?: string;
		onClose?: () => void;
		showModal?: boolean;
		hideButton?: boolean; // New prop
	} = $props();
</script>

{#if !hideButton}
	<Button clazz={`bg-base-100 ${clazzbuttonout}`} onClick={() => (showModal = true)}>
		{#if buttonname}{buttonname}{/if}
		{#if icon}{@render icon()}{/if}
	</Button>
{/if}

<center>
	<dialog class="modal" open={showModal}>
		<div class={`modal-box  ${clazzcolour}`}>
			<form method="dialog">
				<button
					onclick={() => {
						showModal = false;
						onClose?.(); // Call the onClose function if it exists
					}}
					class={`btn btn-circle btn-ghost btn-sm absolute right-2 top-2 ${clazzbuttonin}`}
					>✕</button
				>
			</form>
			{@render children()}
		</div>
	</dialog>
</center>