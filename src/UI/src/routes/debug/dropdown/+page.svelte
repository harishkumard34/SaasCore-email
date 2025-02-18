<script lang="ts">
    import Dropdown from '../../../components/Dropdown.svelte';

    const dropdownData: { type: 'end' | 'top' | 'bottom' | 'left' | 'right' | 'hover' | 'open', text: string }[] = [
        { type: 'end', text: 'Dropdown End' },
        { type: 'top', text: 'Dropdown Top' },
        { type: 'bottom', text: 'Dropdown Bottom' },
        { type: 'left', text: 'Dropdown Left' },
        { type: 'right', text: 'Dropdown Right' },
        { type: 'hover', text: 'Dropdown Hover' },
        { type: 'open', text: 'Dropdown Open' }
    ];

    let needData = $state(false);
</script>

<style>
    .container {
        margin: 20px;
        padding: 20px;
    }
    .spaced {
        margin-bottom: 4em;
    }
</style>

<button onclick={() => (needData = !needData)}> data 👁️</button>
{#if needData}
    <p>dropdown data format</p>
    <pre>dropdownData: {JSON.stringify(dropdownData, null, 4)}</pre>
    <br />
{/if}

<div class="container">
    <center>
        <p class="spaced"><strong>Dropdown Page</strong></p>

        {#each dropdownData as dropdown}
            <div class="spaced">
                <Dropdown type={dropdown.type} contentClass="menu" clazz="">
                    <button type="button" class="btn m-1">{dropdown.text}</button>
                    <!-- svelte-ignore a11y_missing_attribute -->
                    <ul>
                        <li><a >Item 1</a></li>
                        <li><a>Item 2</a></li>
                    </ul>
                </Dropdown>
            </div>
        {/each}
    </center>
</div>
