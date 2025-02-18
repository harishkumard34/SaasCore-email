<script lang="ts">
    import { onMount } from 'svelte';
    import Message from '../../components/Message.svelte';

    let showAlert = false;

    onMount(() => {
        const urlParams = new URLSearchParams(window.location.search);
        if (urlParams.get('login') === 'success') {
            showAlert = true;
            setTimeout(() => {
                showAlert = false;
            }, 5000); 
        }
    });
</script>

<!-- Add this inside the existing indicator div -->
{#if showAlert}

<Message type="success" info="Login successful!" clazz="absolute right-0 mt-2 w-1/4 p-2 bg-green-100 text-green-700 border border-green-400 rounded shadow-lg"/>

{/if}