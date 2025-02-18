<script lang="ts">
	import type { Snippet } from "svelte";
	import { Search } from "lucide-svelte";

	let {
		menuItems,
		searchQuery,
	}: { menuItems: Snippet; searchQuery: String } = $props();
</script>

<div class="navbar bg-base-100">
	<div class="navbar-start">
		<a href="/" class="btn btn-ghost text-xl">SaasCore</a>
		<div class="navbar px-4 text-white ml-4">
			<div class="flex-1 flex items-center gap-4">
				<div class="relative flex-1 max-w-sm">
					<Search
						class="absolute left-3 top-2.5 h-5 w-5 text-gray-400"
					/>
					<input
						type="search"
						bind:value={searchQuery}
						placeholder="Search SaasCore"
						class="w-full pl-10 pr-4 py-2 bg-base-100 rounded text-white placeholder:text-gray-400 focus:outline-none"
					/>
				</div>
			</div>
		</div>
	</div>

	<div class="navbar-end ml-auto">
		<ul class="menu menu-horizontal px-5">
			{@render menuItems()}
		</ul>
	</div>
	<!-- <div class="navbar-end"> -->
	<!-- <a class="btn">Button</a> -->
</div>

