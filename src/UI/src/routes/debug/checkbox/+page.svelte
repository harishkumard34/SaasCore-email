<script>
    import CheckBox from '../../../components/CheckBox.svelte';
</script>


<center><p class="spaced"><strong>Types of CheckBox </strong></p></center>

<CheckBox type="error">Verify you are Human</CheckBox>
<CheckBox type="primary" >Verify you are Human</CheckBox>
<CheckBox type="success">Verify you are Human?</CheckBox>
<CheckBox type="warning">Verify you are Human?</CheckBox>
<CheckBox type="secondary">Verify you are Human?</CheckBox>

<center><p class="spaced"><strong>Size of CheckBox</strong></p></center>

<CheckBox type="success" size="xs" >Verify you are Human?</CheckBox>
<CheckBox type="primary" size="sm" >Verify you are Human</CheckBox>
<CheckBox type="secondary" size="md" clazz="underline">Verify you are Human</CheckBox>
<CheckBox type="error"  >Verify you are Human?</CheckBox>


<CheckBox type="checkbox" size="lg"  >Verify you are Human?</CheckBox>