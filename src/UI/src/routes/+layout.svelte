<script lang="ts">
  import "../app.css";
  import Modal from "../components/Modal.svelte";
  import Navbar from "../components/Navbar.svelte";
  import Sidebar from "../components/Sidebar.svelte";
  import { ShoppingBag, Database, Folder, ChartNoAxesCombined, Cog, TableOfContents, Plus, Bell, Settings, HelpCircle, User, Bookmark, Phone, Store, LayoutDashboard, Contact, Warehouse, Mail, MessageSquare, FileText } from "lucide-svelte";
  import Text from "../components/Text.svelte";
    import Drawer from "../components/Drawer.svelte";

  let { children } = $props();
  let items: string[] = ['listview', 'createform', 'usersettings', 'profiles', 'roles'];
  let showSettingsSidebar:Boolean = false; // Add this state variable


</script>

<Navbar searchQuery="">
  {#snippet menuItems()}
  <div class="flex items-center gap-4">
      <button class="flex items-center gap-2 bg-base-100 px-4 py-2 rounded hover:bg-base-200">
          <Plus class="h-4 w-4" />Create
      </button>
      <button class="btn btn-ghost btn-circle">
          <Phone class="h-4 w-4" />
      </button>
      <button class="btn btn-ghost btn-circle">
          <Store class="h-5 w-5" />
      </button>
      <button class="btn btn-ghost btn-circle">
          <HelpCircle class="h-5 w-5" />
      </button>
      <button class="btn btn-ghost btn-circle">
          <Bell class="h-5 w-5" />
      </button>
    
      <button class="btn btn-ghost btn-circle">
          <User class="h-5 w-5" />
      </button>
      <Drawer>
        {#snippet icons()}
            <Settings class="h-5 w-5" />
            {/snippet}
        {#snippet items()}
        
<div class="flex justify-center items-center ">
        <ul class="menu min-h-full w-45 p-4 text-base-content text-lg">
         
        <li><a href="/settings/users">Users</a></li>
        <li><a href="/settings/roles">Roles</a></li>
        <li><a href="/settings/profiles">Profiles</a></li>
        <li><a href="/settings/permissions">Permissions</a></li>
        <li><a href="/settings/fields">fields</a></li>
    </ul>
    </div>

        {/snippet}
      </Drawer>
    </div>
  

  {/snippet}
</Navbar>

<Sidebar>
  {#snippet items()}
  <li><button class=""><Bookmark></Bookmark></button></li>
  <li><button class=""><LayoutDashboard></LayoutDashboard></button></li>
  <li><button class=""><Contact></Contact></button></li>
  <li><button class=""><Warehouse></Warehouse></button></li>
  <li><button class=""><ShoppingBag></ShoppingBag></button></li>
  <li><button class=""><TableOfContents> </TableOfContents></button></li>
  <li><button class=""><Cog></Cog></button></li>
  <li><button class=""><ChartNoAxesCombined></ChartNoAxesCombined></button></li>
  <li><button class=""><Database></Database></button></li>
  <li><button class=""><Folder></Folder></button></li>
  {/snippet}
  {@render children()}
</Sidebar>



<style>
  li {
      margin-bottom: 25px;
      z-index: 100;
  }
</style>

