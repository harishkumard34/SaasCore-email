<!--
    @component Dropdown - A simple dropdown component

    | Prop           | Type      | Default  | Description
    |----------------|-----------|----------|-------------
    | type           | string    | ''       | The type of dropdown (end, top, bottom, left, right, hover, open).
    | class          | string    | ''       | Additional classes for the dropdown container.
    | contentClass   | string    | ''       | Additional classes for the dropdown content.
    | children       | Function  |          | The function to render the dropdown items.
    | onClick        | Function  |          | The function that will be called when the dropdown is clicked.
-->

<script lang="ts">
    import type { MouseEventHandler } from 'svelte/elements';

    let {
        type = 'top',
        contentClass = '',
        clazz,
        children,
        onClick
    }: {
        type?: 'end' | 'top' | 'bottom' | 'left' | 'right' | 'hover' | 'open';
    
        contentClass: string;
        clazz: string;
        children: Function;
        onClick?: MouseEventHandler<HTMLButtonElement>;
    } = $props();

    let addedClass = $state(clazz);
    let addedContentClass = $state(contentClass);

    function setClass() {
        switch (type) {
            case 'end':
                addedClass += ' dropdown-end';
                break;
            case 'top':
                addedClass += ' dropdown-top';
                break;
            case 'bottom':
                addedClass += ' dropdown-bottom';
                break;
            case 'left':
                addedClass += ' dropdown-left';
                break;
            case 'right':
                addedClass += ' dropdown-right';
                break;
            case 'hover':
                addedClass += ' dropdown-hover';
                break;
            case 'open':
                addedClass += ' dropdown-open';
                break;
        }

        switch (contentClass) {
            case 'menu':
                addedContentClass += ' menu bg-base-100 rounded-box z-[1] w-52 p-2 shadow';
                break;
        }
    }

    setClass();
</script>

<div class={'dropdown bg-base-100 rounded-box z-[1] text-white shadow ' + addedClass}>
    <button type="button" class="btn m-1" onclick={onClick}>Dropdown</button>
    <ul class={'dropdown-content ' + addedContentClass}>
        {@render children()}
    </ul>
</div>
