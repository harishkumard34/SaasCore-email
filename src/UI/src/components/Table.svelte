<!--
	@component Table - A simple table component

	- use snippets in table head and table rows if other components are needed
	- !! use proper tag covering or else may break during reload !!

	| Prop        | Type      | Default | Description
	|-------------|-----------|---------|-------------
	| headers     | string[]  | []      | An array of strings representing the table headers.
	| rows        | {columns: string[]; active?: boolean; hover?: boolean}[] | [] | A 2D array of objects representing the table rows. Each object has a required `columns` property and optional `active` and `hover` properties.
	| isZebra     | boolean   | false   | Whether the table should have zebra striping.
	| TableHead   | Snippet   |         | A custom snippet to render the table head.``(<th> should be the covering tag for all the individual headers)``
	| TableRows   | Snippet   |         | A custom snippet to render the table rows.``(<tr> should be the covering tag)``
-->

<script lang="ts">
	import { type Snippet } from 'svelte'

	let {
		headers = [],
		rows = [],
		isZebra,
		TableHead,
		TableRows
	}: {
		headers?: string[]
		rows?: { columns: string[]; active?: boolean; hover?: boolean }[]
		isZebra?: boolean
		TableHead?: Snippet
		TableRows?: Snippet
	} = $props()
</script>

<table class={isZebra ? 'table table-zebra' : 'table'}>
	<!-- head -->
	<thead class="bg-base-300">
		{#if headers.length}
			<tr>
				{#each headers as header}
					<th>{header}</th>
				{/each}
			</tr>
		{:else if TableHead}
			<tr>
				{@render TableHead()}
			</tr>
		{/if}
	</thead>
	<tbody>
		<!-- row  -->
		{#if rows.length}
			{#each rows as row}
				<tr class={row.hover && !isZebra ? 'hover' : ''}>
					{#each row.columns as column}
						<td>{column}</td>
					{/each}
				</tr>
			{/each}
		{:else if TableRows}
			{@render TableRows()}
		{/if}
	</tbody>
</table>
{#snippet name()}{/snippet}
