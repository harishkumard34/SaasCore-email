<script lang="ts">
    let { type, size, children }: { type: 'primary' | 'secondary' | 'success' | 'warning' | 'error' | 'accent' | 'info' | 'top' | 'bottom' | 'left' | 'right' | 'open';
    size?: 'sm' | 'md' | 'lg' | 'xs';
    children: Function } = $props();

    let addedClass1 = $state('');
    switch (type) {
        case 'primary':
            addedClass1 += ' tooltip-primary';
            break;
        case 'secondary':
            addedClass1 += ' tooltip-secondary';
            break;
        case 'success':
            addedClass1 += ' tooltip-success';
            break;
        case 'error':
            addedClass1 += ' tooltip-error';
            break;
        case 'warning':
            addedClass1 += ' tooltip-warning';
            break;
        case 'accent':
            addedClass1 += ' tooltip-accent';
            break;
        case 'info':
            addedClass1 += ' tooltip-info';
            break;
        case 'top':
            addedClass1 += ' tooltip-top';
            break;
        case 'bottom':
            addedClass1 += ' tooltip-bottom';
            break;
        case 'left':
            addedClass1 += ' tooltip-left';
            break;
        case 'right':
            addedClass1 += ' tooltip-right';
            break;
        case 'open':
            addedClass1 += ' tooltip-open';
            break;
    }

    let addedClass2 = $state('');
    switch (size) {
        case 'sm':
            addedClass2 += ' tooltip-sm';
            break;
        case 'md':
            addedClass2 += ' tooltip-md';
            break;
        case 'lg':
            addedClass2 += ' tooltip-lg';
            break;
        case 'xs':
            addedClass2 += ' tooltip-xs';
            break;
    }
</script>

<div class={'tooltip' + addedClass1 + addedClass2} data-tip="This is a tooltip">
    <div class="btn">{@render children()}</div>
</div>


