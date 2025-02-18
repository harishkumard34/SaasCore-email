<script lang="ts">
    import { Store } from 'lucide-svelte';
    import type { Snippet } from "svelte";
    
    let { items,icons, children }: { items: Snippet; icons:Snippet; children?: Function } = $props();
</script>

<div class="drawer drawer-end  z-20">
    <input id="my-drawer" type="checkbox" class="drawer-toggle" />
    <div class="drawer-content ">
      <!-- Page content here -->
       {#if children}
       {@render children()}
         {/if}
      <label for="my-drawer" class="btn btn-ghost btn-circle drawer-button"> {@render icons()}</label>
    </div>
    <div class="drawer-side mt-16 ">
      <label for="my-drawer" aria-label="close sidebar" class="drawer-overlay"></label>
      <ul class="menu  text-base-content min-h-full w-80 p-4">
        <!-- Sidebar content here -->
      {@render items()}
      </ul>
    </div>
  </div>