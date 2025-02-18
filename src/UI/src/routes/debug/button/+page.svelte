<script>
	import Button from '../../../components/Button.svelte'
</script>

<p>button types</p>
<Button type="primary" onClick={() => alert('Primary clicked')}>Primary</Button>
<Button type="secondary" onClick={() => alert('Secondary clicked')}>Secondary</Button>
<Button type="cancel" onClick={() => alert('Cancel clicked')}>Cancel</Button>

<p>different sizes</p>
<Button size="lg" onClick={() => alert('Large clicked')}>Large</Button>
<Button size="md" onClick={() => alert('Default clicked')}>Default</Button>
<Button size="wide" onClick={() => alert('wide clicked')}>Wide</Button>
<Button size="sm" onClick={() => alert('Small clicked')}>Small</Button>
<Button size="xs" onClick={() => alert('Extra Small clicked')}>Extra Small</Button>

<p>different varities</p>
<Button isCircle={true} onClick={() => alert('Circle clicked')}>Circle</Button>
<Button isOutlined={true} onClick={() => alert('Outlined clicked')}>Outlined</Button>
<Button isDisabled={true}>Disabled</Button>

<p>extras if needed in clazz</p>
<Button clazz="underline" onClick={() => alert('Extra class clicked')}>Underline</Button>
<Button clazz="btn rounded-none px-16" onClick={() => alert('Other class clicked')}>others</Button>
