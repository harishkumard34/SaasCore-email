<script lang="ts">
    import Button from '../../components/Button.svelte';
    import Text from '../../components/Text.svelte';
  
    let email = "";
    let firstName = "";
    let lastName = "";
    let contactOwner = "Arunkumar G";
    let jobTitle = "";
  
    // Handler functions for the buttons
    function handleCreate(event: MouseEvent) {
      event.preventDefault();
      console.log("Create clicked");
      // Add your create logic here.
    }
  
    function handleCreateAndAddAnother(event: MouseEvent) {
      event.preventDefault();
      console.log("Create and add another clicked");
      // Add your create and add another logic here.
    }
  
    function handleCancel(event: MouseEvent) {
      event.preventDefault();
      console.log("Cancel clicked");
      // Add your cancel logic here.
    }
  </script>
  
  <div class="p-6 max-w-lg mx-auto bg-white rounded-lg shadow-md">
    <h2 class="text-lg font-semibold">Create contact</h2>
  

    <form class="mt-4 space-y-3">
      <!-- Email input using the custom Text component -->
      <Text
        type="text"
        placeholder="Enter the Email"
        verification="email"
        bind:value={email}>
        Email
      </Text>
  
      <!-- First name input -->
      <Text
        type="text"
        placeholder="Enter the First Name"
        bind:value={firstName}>
        First name
      </Text>
  
      <!-- Last name input -->
      <Text
        type="text"
        placeholder="Enter the Last Name"
        bind:value={lastName}>
        Last name
      </Text>
  
      <!-- Contact owner using a standard select element -->
      <label class="block">
        <span class="text-gray-700">Contact owner</span>
        <select bind:value={contactOwner} class="select select-bordered w-full">
          <option>Arunkumar G</option>
          <option>John Doe</option>
          <option>Jane Smith</option>
        </select>
      </label>
  
      <!-- Job title input -->
      <Text
        type="text"
        placeholder="Enter the Job Title"
        bind:value={jobTitle}>
        Job title
      </Text>
  
      <!-- Action buttons using the custom Button component -->
      <div class="flex space-x-2 mt-4">
        <Button type="primary" onClick={handleCreate}>
          {"Create"}
        </Button>
        <Button type="cancel" onClick={handleCancel}>
          {"Cancel"}
        </Button>
      </div>
    </form>
  </div>
  