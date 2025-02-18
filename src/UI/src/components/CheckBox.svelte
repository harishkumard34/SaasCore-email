<!-- 
| Prop  | Value      | Class            |
|-------|------------|------------------|
| type  | primary    | checkbox-primary |
| type  | secondary  | checkbox-secondary|
| type  | success    | checkbox-success |
| type  | error      | checkbox-error   |
| type  | warning    | checkbox-warning |
| size  | sm         | checkbox-sm      |
| size  | md         | checkbox-md      |
| size  | lg         | checkbox-lg      |
| size  | xs         | checkbox-xs      | -->


<script lang="ts">
	let { type = 'primary',size, children,clazz }: { type: 'primary' | 'secondary' | 'success' | 'warning' | 'error' | 'checkbox'; clazz?: string;
	size?: 'sm' | 'md' | 'lg' | 'xs';
	 children?: Function } = $props()

	let addedClass1 = $state('')
  
	switch (type) {
		case 'primary':
			addedClass1 += ' checkbox-primary'
			break
		case 'secondary':
			addedClass1 += ' checkbox-secondary '
			break
		case 'success':
			addedClass1 += ' checkbox-success'
			break
		case 'error':
			addedClass1 += ' checkbox-error'	
			break
		case 'warning':
			addedClass1 += ' checkbox-warning'
			break
		case 'checkbox':
			addedClass1 += ' '
			break
	}


	let addedClass2 = $state('')
	switch (size) {
		case 'sm':
			addedClass2 += ' checkbox-sm'
			break
		case 'md':
			addedClass2 += ' checkbox-md'
			break
		case 'lg':
			addedClass2 += ' checkbox-lg'
			break
		case 'xs':
			addedClass2 += ' checkbox-xs'
			break
	}

	let addedClass = $state(clazz)


</script>


<span class={`label-text ${addedClass}`}>{#if children}{@render children()}{/if}</span>
<input type="checkbox" checked={true} class={'checkbox' + addedClass1 + addedClass2 } />