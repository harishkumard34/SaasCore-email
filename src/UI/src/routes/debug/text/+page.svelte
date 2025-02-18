<script>
	import Text from '../../../components/Text.svelte'


</script>

<p>Text types</p>
<br />
<Text type="text" placeholder="Enter the Name">Name</Text>

<Text type="text" placeholder="Enter the Email" verification="email">Email</Text>

<Text type="text" placeholder="Enter the Number" verification="number">Phone No</Text>

<Text type="password" placeholder="Enter the Password">Password</Text>

<Text type="date" placeholder="Enter the Date">Date</Text>

