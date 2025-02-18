<script lang="ts">
	import type { Snippet } from "svelte";

	let { items, children }: { items: Snippet; children: Function } = $props();
</script>

<div class="drawer lg:drawer-open">
	<input id="my-drawer-2" type="checkbox" class="drawer-toggle" />
	<div class="drawer-content">
		<!-- Page content here -->
		{@render children()}
	</div>
	<div class="drawer-side">
		<label
			for="my-drawer-2"
			aria-label="close sidebar"
			class="drawer-overlay"
		></label>
		<ul class="menu min-h-full w-20 bg-base-200 p-4 text-base-content">
			<!-- Sidebar content here -->
			{@render items()}
		</ul>
	</div>
</div>
