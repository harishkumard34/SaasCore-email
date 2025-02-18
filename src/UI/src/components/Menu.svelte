<script lang="ts">
    import type { MouseEventHandler } from 'svelte/elements';

    let {
        text,
        type = 'vertical',
        size = 'md',
        clazz,
        children,
        onClick
    }: {
        text:string;
        type?: 'vertical' | 'horizontal';
        size?: 'xs' | 'sm' | 'md' | 'lg';
    
        clazz: string;
        children: Function;
        onClick?: MouseEventHandler<HTMLButtonElement>;
    } = $props();

    let addedClass = $state(clazz);

    function setClass() {
        switch (type) {
            case 'vertical':
                addedClass += ' menu-vertical';
                break;
            case 'horizontal':
                addedClass += ' menu-horizontal';
                break;
        }

        switch (size) {
            case 'xs':
                addedClass += ' menu-xs';
                break;
            case 'sm':
                addedClass += ' menu-sm';
                break;
            case 'md':
                addedClass += ' menu-md';
                break;
            case 'lg':
                addedClass += ' menu-lg';
                break;
        }
    }

    setClass();
</script>

<div class={'menu bg-base-100 rounded-box z-[1] shadow ' + addedClass}>
    <ul class="menu-content">
        {@render children()}
    </ul>
</div>

