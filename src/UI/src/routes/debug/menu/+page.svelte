<script lang="ts">
    import Menu from '../../../components/Menu.svelte';

    type MenuItemType = 'vertical' | 'horizontal';
    type MenuItemSize = 'md' | 'lg' | 'sm' | 'xs';
    
    const menuItems: { text: string; type: MenuItemType; size: MenuItemSize }[] = [
        { text: 'Home', type: 'vertical', size: 'md' },
        { text: 'About', type: 'horizontal', size: 'lg' },
        { text: 'Services', type: 'vertical', size: 'sm' },
        { text: 'Contact', type: 'horizontal', size: 'xs' },
        { text: 'Blog', type: 'vertical', size: 'md' },
        { text: 'Logout', type: 'horizontal', size: 'lg' }
    ];

    let needData = $state(false);

    const handleClick = (text: string) => {
        console.log(`${text} item clicked!`);
    };
</script>

<style>
    .container {
        margin: 20px;
        padding: 20px;
    }
    .spaced {
        margin-bottom: 20px;
    }
</style>

<div class="container">
    <center>
        <p class="spaced"><strong>Menu Page</strong></p>

        <button onclick={() => (needData = !needData)}> data 👁️</button>
        {#if needData}
            <p>menu data format</p>
            <pre>{JSON.stringify(menuItems, null, 4)}</pre>
            <br />
        {/if}
        <!-- svelte-ignore a11y_missing_attribute -->
        {#each menuItems as item}
            <div class="spaced">
                <Menu type={item.type} size={item.size} text={item.text} clazz="additional-class" onClick={() => handleClick(item.text)}>
                    <ul class="menu-content">
                        <li><button type="button" class="btn m-1">{item.text}</button></li>
                        <li><a >Item 1</a></li>
                        <li><a>Item 2</a></li>
                    </ul>
                </Menu>
            </div>
        {/each}
    </center>
</div>




















