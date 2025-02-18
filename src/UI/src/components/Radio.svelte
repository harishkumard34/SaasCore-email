<!-- | Case      | Class Added       |
|-----------|-------------------|
| primary   | radio-primary     |
| accent    | radio-accent      |
| success   | radio-success     |
| error     | radio-error       |
| warning   | radio-warning     |
 -->

<script lang="ts">
	let {
		type,
		lables
	}: { type: 'primary' | 'accent' | 'success' | 'error' | 'warning' |'qntype'; lables: string } = $props()

	let addedClass = $state('')

	switch (type) {
		case 'primary':
			addedClass += ' radio-primary'
			break
		case 'accent':
			addedClass += ' radio-accent'
			break
		case 'success':
			addedClass += ' radio-sucess'
			break
		case 'error':
			addedClass += ' radio-error'
			break
		case 'warning':
			addedClass += ' radio-warning'
			break
	}

</script>

	<!-- {#each lables as lable} -->
		<!-- <div class="form-control">
			<label class="label cursor-pointer"> -->
				<span class="label-text">{lables}</span>
				<input type="radio" name="radio-10" class={"radio" + addedClass} />
			<!-- </label>
		</div> -->
	<!-- {/each} -->

