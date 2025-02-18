<script>
	import Message from "../../../components/Message.svelte"

</script>

<p>Alert Message types</p>
<br>

<Message type="success" info="Operation was successful!" />
<Message type="warning" info="This is a warning message." />
<Message type="error" info="An error occurred while processing your request." />
