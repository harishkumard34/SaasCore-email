
    <script>
        import Modal from '../../../components/Modal.svelte'
        import Radio from '../../../components/Radio.svelte'
    
    
        let radio = 'Are you Human?'
    </script>
    
    
    <div class="container mx-5 my-5 p-5">
        <center>
            <p class="spaced mb-5"><strong>Verify You Are Human</strong></p>
    
            <div class="spaced mb-5">
                <Radio type="qntype" lables={radio} />
            </div>
    
            <Modal buttonname="Submit">
                <h3 class="text-lg font-bold">Thank You!</h3>
                <p class="py-4">&#x2705; Verification Submitted Successfully</p>
            </Modal>
        </center>
    </div>