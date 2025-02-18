<script lang="ts">
	import { onMount } from "svelte";
	import Table from "../../components/Table.svelte";
	import { getAuthToken } from "$lib";

	let fieldsData: any = $state([]);
	let rows: any = $state([]);
	let headers: any = $state([]);

	async function getFieldsData() {
		const url = "http://localhost:8080/v1/Contact/data";

		const response = await fetch(url, {
			method: "GET",
			headers: getAuthToken(),
		});

		const data = await response.json();
		fieldsData = data;
	}

	onMount(async () => {
		await getFieldsData(); // Wait until data is fetched
		if (fieldsData.length > 0) {
			headers = Object.keys(fieldsData[0]);

			for (let i = 0; i < fieldsData.length; i++) {
				let row = Object.values(fieldsData[i]);
				rows.push(row);
			}

			// console.log(headers, rows);
		}
	});
</script>

<Table>
	{#snippet TableHead()}
		{#each headers as header}
			<th>{header}</th>
		{/each}
	{/snippet}

	{#snippet TableRows()}
		{#each rows as row}
			<tr>
				{#each row as column}
					<td>{column}</td>
				{/each}
			</tr>
		{/each}
	{/snippet}
</Table>
