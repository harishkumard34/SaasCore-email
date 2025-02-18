<script lang="ts">
    let {
        type = "text",
        clazz,
        placeholder,
        verification,
        children,
        options = [],
        value = $bindable(""),
    }: {
        type: "text" | "password" | "date";
        clazz?: string;
        placeholder?: string;
        verification?: "email" | "number" | "phone";
        children: Function;
        options?: string[];
        value?: string;
    } = $props();

    let addedClass = $state(clazz);
    let inputValue = $state(value);
    let errorMessage = $state("");

    // Validation functions
    function validateEmail(value: string): boolean {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return emailRegex.test(value);
    }

    function validateNumber(value: string): boolean {
        // Ensure the value contains only digits
        const numberRegex = /^[0-9]*$/;
        return numberRegex.test(value);
    }

    function validatePhone(value: string): boolean {
        // Validate a 10-digit phone number
        const phoneRegex = /^\d{10}$/;
        return phoneRegex.test(value);
    }

    function handleInput(event: Event) {
        inputValue = (event.target as HTMLInputElement).value;
        if (verification === "email" && !validateEmail(inputValue)) {
            errorMessage = "Invalid email address";
        } else if (verification === "number" && !validateNumber(inputValue)) {
            errorMessage = "Only numbers are allowed";
        } else if (verification === "phone" && !validatePhone(inputValue)) {
            errorMessage = "Invalid phone number (must be 10 digits)";
        } else {
            errorMessage = "";
        }
    }

    // Update the value when inputValue changes
    $effect(() => {
        value = inputValue;
    });
</script>

<label class="form-control">
    <div class="label">
        <span class="label-text">{@render children()}</span>
    </div>

    {#if options.length > 0}
        <select
            class={`input input-bordered ${addedClass}`}
            bind:value={inputValue}
        >
            <option value="">{placeholder || "Select an option"}</option>
            {#each options as option}
                <option value={option}>{option}</option>
            {/each}
        </select>
    {:else}
        <input
            {type}
            {placeholder}
            class={`input input-bordered ${addedClass}`}
            bind:value={inputValue}
            oninput={handleInput}
        />
    {/if}

    {#if errorMessage}
        <span class="text-red-500 text-sm">{errorMessage}</span>
    {/if}
</label>
