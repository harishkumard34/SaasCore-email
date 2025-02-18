<script lang="ts">
    import Tooltip from '../../../components/Tooltip.svelte';
</script>

<style>
    .container {
        margin: 20px;
        padding: 20px;
    }
    .spaced {
        margin-bottom: 20px;
    }
</style>

<div class="container">
    <center>
        <p class="spaced"><strong>Tooltip  Page</strong></p>

        <div class="spaced">
            <Tooltip type="primary" size="md">
                <div class="btn">Primary Tooltip</div>
            </Tooltip>
        </div>

        <div class="spaced">
            <Tooltip type="secondary" size="lg">
                <div class="btn">Secondary Tooltip</div>
            </Tooltip>
        </div>

        <div class="spaced">
            <Tooltip type="success" size="sm">
                <div class="btn">Success Tooltip</div>
            </Tooltip>
        </div>

        <div class="spaced">
            <Tooltip type="error" size="xs">
                <div class="btn">Error Tooltip</div>
            </Tooltip>
        </div>

        <div class="spaced">
            <Tooltip type="warning" size="md">
                <div class="btn">Warning Tooltip</div>
            </Tooltip>
        </div>

        <div class="spaced">
            <Tooltip type="accent" size="md">
                <div class="btn">Accent Tooltip</div>
            </Tooltip>
        </div>

        <div class="spaced">
            <Tooltip type="info" size="md">
                <div class="btn">Info Tooltip</div>
            </Tooltip>
        </div>

        <div class="spaced">
            <Tooltip type="top" size="md">
                <div class="btn">Top Tooltip</div>
            </Tooltip>
        </div>

        <div class="spaced">
            <Tooltip type="bottom" size="md">
                <div class="btn">Bottom Tooltip</div>
            </Tooltip>
        </div>

        <div class="spaced">
            <Tooltip type="left" size="md">
                <div class="btn">Left Tooltip</div>
            </Tooltip>
        </div>

        <div class="spaced">
            <Tooltip type="right" size="md">
                <div class="btn">Right Tooltip</div>
            </Tooltip>
        </div>

        <div class="spaced">
            <Tooltip type="open" size="md">
                <div class="btn">Open Tooltip</div>
            </Tooltip>
        </div>
    </center>
</div>






