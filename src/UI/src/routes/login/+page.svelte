<script lang="ts">
    import Button from "../../components/Button.svelte";
    import Text from "../../components/Text.svelte";
    import Card from "../../components/Card.svelte";
    import { goto } from '$app/navigation';
    import { makeXhr } from '../../utils/api';
    import { error } from '@sveltejs/kit';

    let loginId = '';
    let password = '';
    let message = '';
    let messageColor = 'text-red-500';

    async function handleLogin() {
        if (!loginId || !password) {
            message = 'Please fill in all required fields.';
            return;
        }

        try {
            const res = await makeXhr('/v1/auth/login', 'POST', { loginId, password });

            const data = await res.json();

            if (res.ok) { 
                localStorage.setItem('authToken', data.token); // Save session ID
                message = data.message;
                messageColor = 'text-green-500';
                setTimeout(() => goto('debug/home/?login=success'), 1000); // Redirect to home page with query parameter
            } else {
                message = data.message;
            }
        } catch (err) {
            console.error("Login error:", err);
            message = 'An unexpected error occurred. Please try again.';
        }
    }
</script>

<div class="flex items-center justify-center min-h-screen">
    <div class="flex flex-col items-center space-y-8">
        <Card clazz="p-8 rounded border border-gray-300 shadow-md w-96 mt-18">
            <h1 class="text-2xl font-bold text-center">Login</h1>
            
            <div class="mt-4 space-y-4">
                <Text type="text" placeholder="Enter your Email" bind:value={loginId}>Email</Text>
                <Text type="password" placeholder="Enter your Password" bind:value={password}>Password</Text>
            </div>

            <center>
                <Button type="primary" clazz="mt-7 w-1/2" size="md" onClick={handleLogin}>Login</Button>
            </center>
            <p class="mt-4 text-sm text-gray-500 text-center">
                Don't have an account? 
                <a href="/signup" class="text-blue-500 hover:underline">Sign Up</a>
            </p>
            {#if message}
                <p class="mt-4 text-sm {messageColor} text-center">{message}</p>
            {/if}
        </Card>
    </div>
</div>