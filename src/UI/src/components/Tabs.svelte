<script lang="ts">
  import { onMount } from 'svelte';
  import { goto, afterNavigate } from '$app/navigation';

  let { tabs }: { tabs: { labels: string; href: string }[] } = $props();

  let currentPath = $state('');

  onMount(() => {
    currentPath = window.location.pathname;
  });

  afterNavigate(() => {
    currentPath = window.location.pathname;
  });

  function navigate(event: MouseEvent, href: string) {
    event.preventDefault();
    goto(href);
  }
</script>


<nav aria-label="Tabs Navigation">
  <ul class="tabs-list">
    {#each tabs as tab}
      <li class="tab-item">
        <a
          href={tab.href}
          onclick={(event) => navigate(event, tab.href)}
          class="tab-link {currentPath === tab.href ? 'active' : ''}">
          {tab.labels}
        </a>
      </li>
    {/each}
  </ul>
</nav>

<style>
  .tabs-list {
    list-style: none;
    padding: 0;
    margin: 0;
    display: flex;
    border-bottom: 1px solid #e5e7eb; 
  }

  .tab-item {
    margin-right: 1rem;
  }

  .tab-link {
    display: inline-block;
    padding: 0.5rem 1rem;
    text-decoration: none;
    color: #4b5563;
    transition: color 0.2s, border-bottom 0.2s;
  }

  .tab-link:hover {
    color: #3b82f6; 
  }

  /* Active tab styling */
  .tab-link.active {
    font-weight: bold;
    color: #3b82f6; /* Blue 500 */
    border-bottom: 2px solid #3b82f6;
  }
</style>
