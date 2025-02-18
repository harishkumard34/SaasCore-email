<script>
    import Radio from '../../../components/Radio.svelte'

    let qn = 'Are you Human?'

    let type1 = 'primary'
    let type2 = 'accent'
    let type3 = 'success'
    let type4 = 'error'
    let type5 = 'warning'


</script>


<center><p><strong>Types of Radio Buttons</strong></p>

<Radio type="primary" lables={type1} />
<Radio type="accent" lables={type2} />
<Radio type="success" lables={type3} />
<Radio type="error" lables={type4} />
<Radio type="warning" lables={type5} />


<p><strong>Q & A Type</strong></p>

<Radio type="qntype" lables={qn} />

</center>