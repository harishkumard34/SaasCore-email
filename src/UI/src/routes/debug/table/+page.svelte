<script>
	import Button from '../../../components/Button.svelte'
	import CheckBox from '../../../components/CheckBox.svelte'
	import Table from '../../../components/Table.svelte'
	let table = {
		headers: ['Header 1', 'Header 2', 'Header 3'],
		rows: [
			{ columns: ['1 Row 1', '1 Row 2', '1 Row 3'], hover: true },
			{ columns: ['2 Row 1', '2 Row 2', '2 Row 3'], hover: true },
			{ columns: ['3 Row 1', '3 Row 2', '3 Row 3'], hover: true },
			{ columns: ['4 Row 1', '4 Row 2', '4 Row 3'], hover: true }
		]
	}
	let needData = $state(false)
</script>

<button onclick={() => (needData = !needData)}> data 👁️</button>
{#if needData}
	<p>table data format</p>
	<pre>table: {JSON.stringify(table, null, 4)}</pre>
	<br />
{/if}

<h3 class="text-center">table default</h3>
<Table {...table} /> <br />
<h3 class="text-center">zebra table</h3>
<Table {...table} isZebra={true} /> <br />
<h3>table with checkbox and other custom components</h3>
<Table>
	<!-- <th> should be the covering tag for all the individual headers-->
	{#snippet TableHead()}
		<th>
			<CheckBox type="primary">''</CheckBox>
		</th>
		{#each table.headers as header}
			<th>{header}</th>
		{/each}
		<th>actions</th>
	{/snippet}
	<!-- <tr> should be the covering tag -->
	{#snippet TableRows()}
		{#each table.rows as row}
			<tr>
				<th>
					<CheckBox type="primary">''</CheckBox>
				</th>
				{#each row.columns as column}
					<td>{column}</td>
				{/each}
				<th>
					<Button>Delete</Button>
				</th>
			</tr>
		{/each}
	{/snippet}
</Table>
