<script lang="ts">
    import Button from "../../components/Button.svelte";
    import Text from "../../components/Text.svelte";
    import Card from "../../components/Card.svelte";
    import { onMount } from "svelte";
    import { Country, State, City } from "country-state-city";
    import { goto } from '$app/navigation';
    import { error } from '@sveltejs/kit';
    import { makeXhr } from '../../utils/api';
    import { getHeaders } from '../../utils/security';

    // Variables for dropdown data
    let countries: { isoCode: string; name: string }[] = [];
    let states: { isoCode: string; name: string }[] = [];
    let cities: { name: string }[] = [];
    let country = "";
    let state = "";
    let messageColor = 'text-red-500'

    // Variables for form data
    let email = '';
    let password_phrase = '';
    let otp = '';
    let phone = '';
    let auth_name = '';
    let last_name = '';
    let city = '';
    let zip = '';
    let dob = '';
    let street = '';
    let gender = '';
    let message = '';

    // Fetch countries on mount
    onMount(() => {
        countries = Country.getAllCountries();
    });

    // Update states when a country is selected
    const updateStates = () => {
        if (country) {
            states = State.getStatesOfCountry(country);
            state = "";
            cities = [];
        } else {
            states = [];
            cities = [];
        }
    };

    // Update districts when a state is selected
    const updateDistricts = () => {
        if (state) {
            cities = City.getCitiesOfState(country, state);
        } else {
            cities = [];
        }
    };

    async function handleSignup() {
        if (!email || !password_phrase || !phone || !auth_name || !last_name ) {
            message = 'Please fill in all required fields.';
            return;
        }

        try {
            const res = await makeXhr('/v1/auth/signup', 'POST', {
                email, password_phrase, otp, phone, auth_name, last_name, country, state, city, zip, dob, street, gender
            });

            if (res.ok) {
                message = 'Signup successful! Redirecting to login...';
                messageColor='text-green-500'
                setTimeout(() => goto('/login'), 2000); // Redirect to login page
            } else {
                const { message: errorMessage } = await res.json();
                throw error(res.status, errorMessage || 'Signup failed.');
            }
        } catch (err) {
            if (err instanceof Error) {
                message = err.message || 'An unexpected error occurred.';
            } else {
                message = 'An unexpected error occurred.';
            }
        }
    }
</script>

<div class="flex items-center justify-center min-h-screen">
    <div class="flex flex-col items-center space-y-8">
        <Card clazz="p-8 rounded border border-gray-300 shadow-md w-96 mt-20">
            <h1 class="text-2xl font-bold text-center ">Sign Up</h1>
            
            <div class="mt-4 space-y-4">
                <Text type="text" placeholder="Enter your First Name" bind:value={auth_name}>First Name</Text>
                <Text type="text" placeholder="Enter your Last Name" bind:value={last_name}>Last Name</Text>
                <Text type="text" placeholder="Enter your Email" verification="email" bind:value={email}>Email</Text>
                <Text type="text" placeholder="Enter your Phone Number" verification="number" bind:value={phone}>Phone No</Text>
                <Text type="password" placeholder="Enter your Password" bind:value={password_phrase}>New Password</Text>
                <Text type="password" placeholder="Re-enter your Password">Confirm Password</Text>
                
                <!-- Country Dropdown -->
                <label for="country" class="block text-gray-700">Country</label>
                <select
                    id="country"
                    bind:value={country}
                    on:change={updateStates}
                    class="block w-full border rounded p-2"
                >
                    <option value="">Select Country</option>
                    {#each countries as country}
                        <option value={country.isoCode}>{country.name}</option>
                    {/each}
                </select>

                <!-- State Dropdown -->
                <label for="state" class="block text-gray-700">State</label>
                <select
                    id="state"
                    bind:value={state}
                    on:change={updateDistricts}
                    class="block w-full border rounded p-2"
                >
                    <option value="">Select State</option>
                    {#each states as state}
                        <option value={state.isoCode}>{state.name}</option>
                    {/each}
                </select>

                <!-- District Dropdown -->
                <label for="city" class="block text-gray-700">City</label>
                <select
                    id="city"
                    bind:value={city}
                    class="block w-full border rounded p-2"
                >
                    <option value="">Select City</option>
                    {#each cities as city}
                        <option value={city.name}>{city.name}</option>
                    {/each}
                </select>

                <!-- Hidden inputs to store country, state, and city values -->
                <input type="hidden" name="country" bind:value={country} />
                <input type="hidden" name="state" bind:value={state} />
                <input type="hidden" name="city" bind:value={city} />

                <Text type="text" placeholder="Enter your ZIP Code" verification="number" bind:value={zip}>ZIP Code</Text>
                <Text type="date" placeholder="Enter your Date of Birth" bind:value={dob}>Date of Birth</Text>
                <Text type="text" placeholder="Enter your Street" bind:value={street}>Street</Text>
                <Text type="text" placeholder="Enter your Gender" bind:value={gender}>Gender</Text>
            </div>

            <center>
                <Button type="primary" clazz="mt-7 w-1/2" size="md" onClick={handleSignup}>Sign Up</Button>
            </center>
            <p class="mt-4 text-sm text-gray-500 text-center">
                Already have an account? 
                <a href="/login" class="text-blue-500 hover:underline">Login</a>
            </p>
            {#if message}
                <p class="mt-4 text-sm {messageColor} text-center">{message}</p>
            {/if}
        </Card>
    </div>
</div>