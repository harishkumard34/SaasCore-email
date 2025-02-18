<script lang="ts">
	import Button from '../../../components/Button.svelte';
	import Text from '../../../components/Text.svelte';
	import CheckBox from '../../../components/CheckBox.svelte';
	import Dropdown from '../../../components/Dropdown.svelte';
	import MultiLine from '../../../components/MultiLine.svelte';
	import Modal from '../../../components/Modal.svelte';


	interface Item {
		id: string;
		label: string;
		component?: 'text' | 'number' | 'date' | 'multiline' | 'checkbox' | 'email' | 'phone';
		column_name?: string; // Add column_name to the Item interface
		mandatory?: boolean; // Add mandatory flag
	}

	let items = $state<Item[]>([]); // Declare items with $state for reactivity

	let newType = $state<'text' | 'number' | 'date' | 'multiline' | 'checkbox'>('text');
	let newLabel = $state<string>('');
	let message = $state<string>('');
	let messageColor = $state<string>('text-green-600');

	let fetchedFields = $state<Item[]>([]); // Declare fetchedFields with $state for reactivity

	// Counters for each type
	let stringCounter = 0;
	let intCounter = 0;
	let dateCounter = 0;
	let multilineCounter = 0;
	let checkboxCounter = 0;
	let emailCounter = 0;
	let phoneCounter = 0;

	async function fetchExistingColumnNames() {
		try {
			const res = await fetch('http://localhost:8080/v1/fields', {
				method: 'GET',
				headers: {
					'Content-Type': 'application/json',
					'X-PrivateTenant': 'saasdb_10000',
					'X-Session-ID': '6382e6af-3afc-4256-8ba1-1db8b11ab18b',
				},
			});

			if (res.ok) {
				const data = await res.json();
				const columnNames = data.map((field: any) => field.column_name);
				updateCounters(columnNames);
			} else {
				const errorMessage = await res.text();
				throw new Error(errorMessage || 'Failed to fetch existing column names.');
			}
		} catch (err) {
			if (err instanceof Error) {
				message = err.message || 'An unexpected error occurred.';
			} else {
				message = 'An unexpected error occurred.';
			}
			messageColor = 'text-red-600';
		}
	}

	function updateCounters(columnNames: string[]) {
		columnNames.forEach((columnName) => {
			if (columnName.startsWith('stringColumn')) {
				const count = parseInt(columnName.replace('stringColumn', ''), 10);
				if (count > stringCounter) stringCounter = count;
			} else if (columnName.startsWith('intColumn')) {
				const count = parseInt(columnName.replace('intColumn', ''), 10);
				if (count > intCounter) intCounter = count;
			} else if (columnName.startsWith('dateColumn')) {
				const count = parseInt(columnName.replace('dateColumn', ''), 10);
				if (count > dateCounter) dateCounter = count;
			} else if (columnName.startsWith('multilineColumn')) {
				const count = parseInt(columnName.replace('multilineColumn', ''), 10);
				if (count > multilineCounter) multilineCounter = count;
			} else if (columnName.startsWith('checkboxColumn')) {
				const count = parseInt(columnName.replace('checkboxColumn', ''), 10);
				if (count > checkboxCounter) checkboxCounter = count;
			} else if (columnName.startsWith('emailColumn')) {
				const count = parseInt(columnName.replace('emailColumn', ''), 10);
				if (count > emailCounter) emailCounter = count;
			} else if (columnName.startsWith('phoneColumn')) {
				const count = parseInt(columnName.replace('phoneColumn', ''), 10);
				if (count > phoneCounter) phoneCounter = count;
			}
		});
	}

	function generateColumnName(type: string): string {
		switch (type) {
			case 'text':
				return `stringColumn${++stringCounter}`;
			case 'number':
				return `intColumn${++intCounter}`;
			case 'date':
				return `dateColumn${++dateCounter}`;
			case 'multiline':
				return `multilineColumn${++multilineCounter}`;
			case 'checkbox':
				return `checkboxColumn${++checkboxCounter}`;
			case 'email':
				return `emailColumn${++emailCounter}`;
			case 'phone':
				return `phoneColumn${++phoneCounter}`;
			default:
				return `stringColumn${++stringCounter}`;
		}
	}


	let showModal = $state(false); // Add this state to control the modal visibility

// Define the closeModal function
function closeModal() {
	showModal = false; // This will close the modal
}

async function addCustomField() {
	if (!newLabel.trim()) return;

	await fetchExistingColumnNames();

	const newItem: Item = {
		id: `${Date.now()}`,
		label: newLabel,
		component: newType,
		column_name: generateColumnName(newType), // Generate column name dynamically
	};

	items = [...items, newItem];
	newLabel = '';
	newType = 'text';
	closeModal(); // Close the modal after saving
}

	function handleDelete(id: string) {
		items = items.filter((item) => item.id !== id); // This will now trigger reactive updates
	}

	async function handleFieldCreation() {
		try {
			const fieldsData = items.map((item) => ({
				field_name: item.label,
				display_name: item.label,
				type: mapComponentToFieldsType(item.component ?? 'text'),
				column_name: item.column_name, // Include the dynamically generated column name
			}));

			const res = await fetch('http://localhost:8080/v1/fields', {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json',
					'X-PrivateTenant': 'saasdb_10000',
					'X-Session-ID': '6382e6af-3afc-4256-8ba1-1db8b11ab18b',
				},
				body: JSON.stringify(fieldsData),
			});

			if (res.ok) {
				message = 'Fields Creation successful!';
				messageColor = 'text-green-600';

				// Clear the fields after successful submission
				items = []; // This will now trigger reactive updates
				newLabel = '';
				newType = 'text';
			} else {
				const errorMessage = await res.text();
				throw new Error(errorMessage || 'Fields creation failed.');
			}
		} catch (err) {
			if (err instanceof Error) {
				message = err.message || 'An unexpected error occurred.';
			} else {
				message = 'An unexpected error occurred.';
			}
			messageColor = 'text-red-600';
		}
	}

	async function fetchFields() {
		try {
			const res = await fetch('http://localhost:8080/v1/fields', {
				method: 'GET',
				headers: {
					'Content-Type': 'application/json',
					'X-PrivateTenant': 'saasdb_10000',
					'X-Session-ID': '6382e6af-3afc-4256-8ba1-1db8b11ab18b',
				},
			});

			if (res.ok) {
				const data = await res.json();
				console.log('Fetched Data:', data);
				fetchedFields = data.map((field: any) => ({
					id: field.field_id,
					label: field.display_name,
					component: mapFieldsTypeToComponent(field.type),
					column_name: field.column_name, // Include column_name in fetched fields
				}));
				console.log('Mapped Fields:', fetchedFields);
				message = 'Fields fetched successfully!';
				messageColor = 'text-green-600';
			} else {
				const errorMessage = await res.text();
				throw new Error(errorMessage || 'Failed to fetch fields.');
			}
		} catch (err) {
			if (err instanceof Error) {
				message = err.message || 'An unexpected error occurred.';
			} else {
				message = 'An unexpected error occurred.';
			}
			messageColor = 'text-red-600';
		}
	}

	async function handleDeleteField(fieldId: string) {
		try {
			const res = await fetch(`http://localhost:8080/v1/fields/${fieldId}`, {
				method: 'DELETE',
				headers: {
					'Content-Type': 'application/json',
					'X-PrivateTenant': 'saasdb_10000',
					'X-Session-ID': '6382e6af-3afc-4256-8ba1-1db8b11ab18b',
				},
			});

			if (res.ok) {
				const deletedField = fetchedFields.find((field) => field.id === fieldId);
				if (deletedField) {
					const columnName = deletedField.column_name;
					if (columnName) {
						const type = columnName.replace(/\d+$/, '');
						const count = parseInt(columnName.replace(type, ''), 10);

						switch (type) {
							case 'stringcolumn':
								if (count === stringCounter) stringCounter--;
								break;
							case 'intcolumn':
								if (count === intCounter) intCounter--;
								break;
							case 'datecolumn':
								if (count === dateCounter) dateCounter--;
								break;
							case 'multilinecolumn':
								if (count === multilineCounter) multilineCounter--;
								break;
							case 'checkboxcolumn':
								if (count === checkboxCounter) checkboxCounter--;
								break;
							case 'emailcolumn':
								if (count === emailCounter) emailCounter--;
								break;
							case 'phonecolumn':
								if (count === phoneCounter) phoneCounter--;
								break;
						}
					}
				}

				fetchedFields = fetchedFields.filter((field) => field.id !== fieldId); // This will now trigger reactive updates
				message = 'Field deleted successfully!';
				messageColor = 'text-green-600';
			} else {
				const errorMessage = await res.text();
				throw new Error(errorMessage || 'Failed to delete field.');
			}
		} catch (err) {
			if (err instanceof Error) {
				message = err.message || 'An unexpected error occurred.';
			} else {
				message = 'An unexpected error occurred.';
			}
			messageColor = 'text-red-600';
		}
	}

	function mapComponentToFieldsType(component: string): number {
		switch (component) {
			case 'text':
				return 0;
			case 'number':
				return 10;
			case 'date':
				return 7;
			case 'multiline':
				return 1;
			case 'checkbox':
				return 6;
			case 'phone':
				return 2;
			case 'email':
				return 3;
			default:
				return 0;
		}
	}

	function mapFieldsTypeToComponent(type: string): Item['component'] {
		const lowerCaseType = type.toLowerCase();
		switch (lowerCaseType) {
			case 'text':
				return 'text';
			case 'number':
				return 'number';
			case 'date':
				return 'date';
			case 'multiline':
				return 'multiline';
			case 'checkbox':
				return 'checkbox';
			case 'phone':
				return 'phone';
			case 'email':
				return 'email';
			default:
				return 'text';
		}
	}

	let editingFieldId = $state<string | null>(null); // Track which field is being edited
    let editedLabel = $state<string>(''); // Store the temporary label during editing
    let showEditModal = $state(false); // Control the visibility of the edit modal

    // Open the edit modal and populate it with the current field label
    function openEditModal(fieldId: string, currentLabel: string) {
        editingFieldId = fieldId;
        editedLabel = currentLabel;
        showEditModal = true;
    }

    // Close the edit modal and reset states
    function closeEditModal() {
        editingFieldId = null;
        editedLabel = '';
        showEditModal = false;
    }

    // Save the edited label
    async function saveEditedLabel() {
        if (editingFieldId && editedLabel.trim()) {
            await handleEditField(editingFieldId, editedLabel);
            closeEditModal();
        }
    }

    // Handle editing a field label
    async function handleEditField(fieldId: string, newLabel: string) {
        try {
            const res = await fetch(`http://localhost:8080/v1/fields/${fieldId}/label`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'text/plain', // Send as plain text
                    'X-PrivateTenant': 'saasdb_10000',
                    'X-Session-ID': '6382e6af-3afc-4256-8ba1-1db8b11ab18b',
                },
                body: newLabel, // Send the label as a plain string
            });

            if (res.ok) {
                const updatedField = await res.json();
                fetchedFields = fetchedFields.map(field =>
                    field.id === fieldId ? { ...field, label: updatedField.display_name } : field
                );
                message = 'Field label updated successfully!';
                messageColor = 'text-green-600';
            } else {
                const errorMessage = await res.text();
                throw new Error(errorMessage || 'Failed to update field label.');
            }
        } catch (err) {
            if (err instanceof Error) {
                message = err.message || 'An unexpected error occurred.';
            } else {
                message = 'An unexpected error occurred.';
            }
            messageColor = 'text-red-600';
        }
    }
</script>

<div class="container mx-auto p-8 bg-white">
	<div class="mb-8 flex flex-col gap-4">
		<h1 class="text-3xl font-bold text-green-500">Fields Creation</h1>
		<p class="text-black">Create fields and manage input components.</p>
	</div>

	<div class="mb-6">
		<Modal 
			buttonname="+ Custom Fields" 
			clazzcolour="bg-white border-2 border-green-500 text-green-500 shadow-lg rounded-lg" 
			clazzbuttonin="bg-white border-2 border-green-500 text-green-500 shadow-lg rounded-lg" 
			clazzbuttonout="bg-white border-2 border-green-500 text-green-500 shadow-lg rounded-lg hover:bg-white hover:text-green hover:border-green-500"
			onClose={closeModal}
			bind:showModal={showModal} 
		>
			<div class="flex flex-col gap-4">
				<div>
					<label for="label" class="block text-sm font-medium bg-white  text-green-500">Field Label</label>
					<input
						id="label"
						type="text"
						bind:value={newLabel}
						placeholder="Enter a label for the field"
						class="mt-1 block w-full rounded bg-white text-black"
					/>
				</div>
				<div>
					<label for="type" class="block text-sm font-medium bg-white  text-green-500 ">Field Type</label>
					<select
						id="type"
						bind:value={newType}
						class="mt-1 block w-full rounded bg-gray-200 text-gray-800 border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
					>
						<option value="text">Text</option>
						<option value="number">Numerical</option>
						<option value="date">Date</option>
						<option value="email">Email</option>
						<option value="phone">Phone</option>
						<option value="multiline">MultiLine</option>
						<option value="checkbox">CheckBox</option>
					</select>
				</div>

				<Button
					type="primary"
					onClick={addCustomField}
					clazz="self-start rounded bg-white border-2 border-green-500 text-green-500 shadow-lg rounded-lg hover:bg-white hover:text-green hover:border-green-500"
				>
					Save
				</Button>
			</div>
		</Modal>
	</div>

	<!-- ... (rest of the code) -->


	<div class="space-y-2">
		{#each items as item (item.id)}
			<div class="flex flex-col items-start rounded-lg bg-white p-4 shadow transition-all hover:shadow-xl">
				{#if item.component === 'text'}
					<Text type="text" placeholder={`Enter ${item.label}`} clazz="bg-violet-100 text-blue-900 border-violet-500 placeholder-black">
						{item.label}
					</Text>
				{:else if item.component === 'number'}
					<Text type="text" verification="number" placeholder={`Enter ${item.label}`} clazz="bg-violet-100 text-blue-900 border-violet-500 placeholder-black">
						{item.label}
					</Text>
				{:else if item.component === 'date'}
					<Text type="date" placeholder={`Enter ${item.label}`} clazz="bg-violet-100 text-blue-900 border-violet-500 placeholder-black">
						{item.label}
					</Text>
				{:else if item.component === 'multiline'}
					<MultiLine placeholder={`Enter ${item.label}`} clazz=" bg-violet-100 text-blue-900 border-violet-500 placeholder-black ">
						{item.label}
					</MultiLine>
				{:else if item.component === 'checkbox'}
					<CheckBox type="primary" clazz="text-black text-base mb-4">
						{item.label}
					</CheckBox>
				{:else if item.component === 'email'}
					<Text type="text" verification="email" placeholder={`Enter ${item.label}`} clazz="bg-violet-100 text-blue-900 border-violet-500 placeholder-black">
						{item.label}
					</Text>
				{:else if item.component === 'phone'}
					<Text type="text" verification="phone" placeholder={`Enter ${item.label}`} clazz="bg-violet-100 text-blue-900 border-violet-500 placeholder-black">
						{item.label}
					</Text>
				{/if}

				<Button
					clazz="mt-2 self-end bg-cyan-500 text-white hover:bg-cyan-600"
					type="primary"
					onClick={() => handleDelete(item.id)}
				>
					Delete
				</Button>
			</div>
		{/each}
	</div>

	<div class="mt-8 flex justify-center gap-4">
		<Button
			type="primary"
			onClick={handleFieldCreation}
			clazz="rounded bg-purple-500 px-4 py-2 text-white hover:bg-purple-600"
		>
			Submit Fields
		</Button>
		<Button
			type="primary"
			onClick={fetchFields}
			clazz="rounded bg-green-500 px-4 py-2 text-white hover:bg-green-600"
		>
			Display Fields
		</Button>
	</div>

	{#if message}
		<div class={`mt-4 text-center ${messageColor}`}>
			{message}
		</div>
	{/if}

	<Modal
	hideButton={true}
    buttonname="Edit Field"
    clazzcolour="bg-white border-2 border-green-500 text-green-500 shadow-lg rounded-lg"
    clazzbuttonin="bg-white border-2 border-green-500 text-green-500 shadow-lg rounded-lg"
    clazzbuttonout="bg-white border-2 border-green-500 text-green-500 shadow-lg rounded-lg hover:bg-white hover:text-green hover:border-green-500"
    onClose={closeEditModal}
    bind:showModal={showEditModal}
>
    <div class="flex flex-col gap-4">
        <div>
            <label for="edit-label" class="block text-sm font-medium bg-white text-green-500">Edit Field Label</label>
            <input
                id="edit-label"
                type="text"
                bind:value={editedLabel}
                placeholder="Enter a new label for the field"
                class="mt-1 block w-full rounded bg-white text-black"
            />
        </div>
        <div class="flex gap-2">
            <Button
                type="primary"
                onClick={saveEditedLabel}
                clazz="rounded bg-green-500 px-4 py-2 text-white hover:bg-green-600"
            >
                Save
            </Button>
            <Button
                type="primary"
                onClick={closeEditModal}
                clazz="rounded bg-gray-500 px-4 py-2 text-white hover:bg-gray-600"
            >
                Cancel
            </Button>
        </div>
    </div>
</Modal>

<!-- Display Fetched Fields -->
<div class="space-y-2 mt-8">
    <h2 class="text-2xl font-bold text-green-500">Fetched Fields</h2>
    {#each fetchedFields as field (field.id)}
        <div class="flex flex-col items-start rounded-lg bg-white p-4 shadow transition-all hover:shadow-xl">
            {#if field.component === 'text'}
                <Text type="text" placeholder={`Enter ${field.label}`} clazz="bg-violet-100 text-blue-900 border-violet-500 placeholder-black">
                    {field.label}
                </Text>
            {:else if field.component === 'number'}
                <Text type="text" verification="number" placeholder={`Enter ${field.label}`} clazz="bg-violet-100 text-blue-900 border-violet-500 placeholder-black">
                    {field.label}
                </Text>
            {:else if field.component === 'date'}
                <Text type="date" placeholder={`Enter ${field.label}`} clazz="bg-violet-100 text-blue-900 border-violet-500 placeholder-black">
                    {field.label}
                </Text>
            {:else if field.component === 'multiline'}
                <MultiLine placeholder={`Enter ${field.label}`} clazz="bg-violet-100 text-blue-900 border-violet-500 placeholder-black">
                    {field.label}
                </MultiLine>
            {:else if field.component === 'checkbox'}
                <CheckBox type="primary" clazz="text-black text-base mb-4">
                    {field.label}
                </CheckBox>
            {:else if field.component === 'email'}
                <Text type="text" verification="email" placeholder={`Enter ${field.label}`} clazz="bg-violet-100 text-blue-900 border-violet-500 placeholder-black">
                    {field.label}
                </Text>
            {:else if field.component === 'phone'}
                <Text type="text" verification="phone" placeholder={`Enter ${field.label}`} clazz="bg-violet-100 text-blue-900 border-violet-500 placeholder-black">
                    {field.label}
                </Text>
            {/if}

            <div class="mt-2 self-end flex gap-2">
                <Button
                    type="primary"
                    onClick={() => openEditModal(field.id, field.label)}
                    clazz="rounded bg-blue-500 px-4 py-2 text-white hover:bg-blue-600"
                >
                    Edit
                </Button>
                <Button
                    type="primary"
                    onClick={() => handleDeleteField(field.id)}
                    clazz="rounded bg-red-500 px-4 py-2 text-white hover:bg-red-600"
                >
                    Delete
                </Button>
            </div>
        </div>
    {/each}
</div>
</div>