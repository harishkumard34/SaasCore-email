<script lang="ts">
	import { getAuthToken } from "$lib";
	import { onMount } from "svelte";
	import Card from "../../components/Card.svelte";
	import Text from "../../components/Text.svelte";
	import Button from "../../components/Button.svelte";
	import MultiLine from "../../components/MultiLine.svelte";

	let moduleId = 1000000000004; //should it be passed from the url?
	let moduleData: any = $state([]); //response from the url
	let formData: Record<string, any> = $state({}); // stores values of items in the form

	async function getFieldsData() {
		const url = `http://localhost:8080/v1/modules/${moduleId}`;

		const response = await fetch(url, {
			method: "GET",
			headers: getAuthToken(),
		});

		const data = await response.json();
		moduleData = data;

		// Initialize formData with field names and empty values
		moduleData.layouts.forEach(
			(layout: { layout_details: { sections: any[] } }) => {
				layout.layout_details.sections.forEach(
					(section: { section_details: { fields: any[] } }) => {
						section.section_details.fields.forEach(
							(field: {
								field_details: {
									field_name: string | number;
									value: string;
								};
							}) => {
								formData[field.field_details.field_name] =
									field.field_details.value || "";
							},
						);
					},
				);
			},
		);
	}

	async function postFieldsData() {
		const url = `http://localhost:8080/v1/${moduleData.module_name}/data`;
		delete formData["Country"];
		const payload = JSON.stringify(formData);

		console.log(formData);
		console.log(payload);

		const response = await fetch(url, {
			method: "POST",
			headers: {
				"Content-Type": "application/json",
				...getAuthToken(),
			},
			body: payload,
		});

		if (response.ok) {
			console.log("Data successfully posted");
			formData = {};
		} else {
			console.error("Failed to post data");
		}
	}

	onMount(async () => {
		await getFieldsData(); // Wait until data is fetched
		if (moduleData) {
			// console.log($state.snapshot(moduleData));
		}
	});
</script>

<Card clazz="p-8 rounded border border-gray-300 shadow-md mx-20 mb-20">
	{#each moduleData.layouts as layout}
		<h1 class="text-3xl">
			{layout.layout_details.layout_name + " layout Form"}
		</h1>
		{#each layout.layout_details.sections as section}
			<div>
				<h2 class="text-2xl mb-5 mt-5">
					{section.section_details.section_name}
				</h2>
				{#each section.section_details.fields as field}
					{#if field.field_details.type == "SINGLELINE"}
						<Text
							type="text"
							placeholder={`Enter ${field.field_details.display_name}`}
							bind:value={formData[
								field.field_details.field_name
							]}>{field.field_label}</Text
						>
					{:else if field.field_details.type == "MULTILINE"}
						<MultiLine
							placeholder={`Enter ${field.field_details.display_name}`}
							bind:value={formData[
								field.field_details.field_name
							]}>{field.field_details.display_name}</MultiLine
						>
					{:else if field.field_details.type == "PHONE"}
						<Text
							type="text"
							verification="phone"
							placeholder={`Enter ${field.field_details.display_name}`}
							bind:value={formData[
								field.field_details.field_name
							]}>{field.field_details.display_name}</Text
						>
					{:else if field.field_details.type == "EMAIL"}
						<Text
							type="text"
							verification="email"
							placeholder={`Enter ${field.field_details.display_name}`}
							bind:value={formData[
								field.field_details.field_name
							]}>{field.field_details.display_name}</Text
						>
					{:else if field.field_details.type == "DATE"}
						<Text
							type="date"
							placeholder={`Enter ${field.field_details.display_name}`}
							bind:value={formData[
								field.field_details.field_name
							]}>{field.field_details.display_name}</Text
						>
					{:else if field.field_details.type == "NUMBER"}
						<Text
							type="text"
							verification="number"
							placeholder={`Enter ${field.field_details.display_name}`}
							bind:value={formData[
								field.field_details.field_name
							]}>{field.field_details.display_name}</Text
						>
					{:else if field.field_details.type == "PICKLIST"}
						<Text
							type="text"
							options={["option1", "option2", "option3"]}
							placeholder={`Enter ${field.field_details.display_name}`}
							bind:value={formData[
								field.field_details.field_name
							]}>{field.field_details.display_name}</Text
						>
					{:else}
						<p>{field.field_details.display_name}</p>
					{/if}
				{/each}
			</div>
		{/each}
	{/each}
	<center>
		<Button
			type="primary"
			clazz="mt-7 w-1/2"
			size="md"
			onClick={postFieldsData}>Submit</Button
		>
	</center>
</Card>
