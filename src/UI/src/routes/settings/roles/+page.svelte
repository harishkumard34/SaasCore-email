<script lang="ts">
  import { onMount } from 'svelte';
  import { writable, get } from 'svelte/store';
  import Button from '../../../components/Button.svelte';
  import Card from '../../../components/Card.svelte';
  import {getAuthToken} from '$lib';


  interface Role {
    role_id: number | null;
    role_name: string;
    reporting_to_id: number | null;
    share_with_peers: boolean;
    description: string;
    created_time: number;
    modified_time: number;
    level: number;
  }

  const roles = writable<Role[]>([]);

  function getHeaders() {
    return {
      ...getAuthToken(),
      'Content-Type': 'application/json'
    };
  }

  async function fetchRoles() {
    try {
      console.log('Fetching roles...');
      const response = await fetch('http://localhost:8080/v1/roles', { headers: getHeaders() });
      if (!response.ok) throw new Error('Failed to fetch roles');
      const data = await response.json();
      roles.set(data);
    } catch (error) {
      console.error('Fetch Roles Error:', error);
    }
  }

  onMount(fetchRoles);

  // modalState holds the modal’s visibility, the currently edited role, and the form data.
  const modalState = writable({
    showModal: false,
    currentRole: null as Role | null,
    newRole: {
      role_id: null,
      role_name: '',
      reporting_to_id: null,
      share_with_peers: false,
      description: '',
      created_time: Date.now(),
      modified_time: Date.now(),
      level: 0
    } as Role
  });

  function openModal(role: Role | null = null) {
    modalState.update((state) => ({
      ...state,
      showModal: true,
      currentRole: role ? { ...role } : null,
      newRole: role
        ? { ...role }
        : {
            role_id: null,
            role_name: '',
            reporting_to_id: null,
            share_with_peers: false,
            description: '',
            created_time: Date.now(),
            modified_time: Date.now(),
            level: 0
          }
    }));
  }

  function closeModal() {
    modalState.update((state) => ({ ...state, showModal: false }));
  }

  async function saveRole() {
    const currentModal = get(modalState);

    // Validate role_name.
    if (!currentModal.newRole.role_name.trim()) {
      alert('Role name cannot be empty!');
      return;
    }

    let created_time = currentModal.newRole.created_time;
    if (typeof created_time === 'string') {
      created_time = Date.parse(created_time);
    }

    // Note: Ensure the property names here match your backend entity
    const roleData = {
      role_id: currentModal.newRole.role_id,
      role_name: currentModal.newRole.role_name,
      reporting_to_id: currentModal.newRole.reporting_to_id,
      sharewithpeers: currentModal.newRole.share_with_peers,
      description: currentModal.newRole.description,
      created_time: created_time,
      modified_time: Date.now(),
      level: currentModal.newRole.level
    };

    console.log("Saving role:", JSON.stringify(roleData, null, 2));

    try {
      const method = currentModal.currentRole ? 'PUT' : 'POST';
      const url = currentModal.currentRole
        ? `http://localhost:8080/v1/roles/${currentModal.currentRole.role_id}`
        : 'http://localhost:8080/v1/roles';
      const response = await fetch(url, {
        method,
        headers: getHeaders(),
        body: JSON.stringify(roleData)
      });
      if (!response.ok) {
        const errorDetails = await response.text();
        console.error('Error saving role:', errorDetails);
        throw new Error('Failed to save role');
      }
      await fetchRoles();
    } catch (error) {
      console.error('Error:', error);
    } finally {
      closeModal();
    }
  }

  async function removeRole(roleId: number) {
    try {
      const response = await fetch(`http://localhost:8080/v1/roles/${roleId}`, {
        method: 'DELETE',
        headers: getHeaders()
      });
      if (!response.ok) {
        const errorDetails = await response.text();
        console.error('Error deleting role:', errorDetails);
        throw new Error('Failed to delete role');
      }
      await fetchRoles();
    } catch (error) {
      console.error('Error:', error);
    }
  }
</script>

<style>
  .container {
    padding: 2rem;
  }
</style>

<div class="container">
  <h2 class="text-2xl font-bold">Roles</h2>
  <br />
  <Button type="primary" clazz="btn btn-primary" onClick={() => openModal()}>
    Create Role
  </Button>
  <br /><br />

  <!-- Display the list of roles -->
  <div class="grid grid-cols-1 gap-4 sm:grid-cols-2 lg:grid-cols-3">
    {#each $roles as role, index (role.role_id ?? index)}
      <Card clazz="w-full bg-base-100 shadow-md">
        <div class="card-body">
          <p>Role: {role.role_name}</p>
          <p>Description: {role.description}</p>
          <p>Share With Peers: {role.share_with_peers ? 'Yes' : 'No'}</p>
          <p>Level: {role.level}</p>
          <p>Created: {new Date(role.created_time).toLocaleString()}</p>
          <p>Modified: {new Date(role.modified_time).toLocaleString()}</p>
          <div class="card-actions">
            <Button type="primary" size="sm" onClick={() => openModal(role)}>
              Edit
            </Button>
            <Button
              type="cancel"
              size="sm"
              onClick={() => role.role_id !== null && removeRole(role.role_id)}
            >
              Delete
            </Button>
          </div>
        </div>
      </Card>
    {/each}
  </div>

  <!-- Modal for creating/editing a role -->
  {#if $modalState.showModal}
    <div class="modal modal-open">
      <div class="modal-box">
        <!-- Role Name -->
        <div class="form-control mb-4">
          <label class="label" for="role-name-input">Role Name</label>
          <input
            id="role-name-input"
            type="text"
            placeholder="Enter Role Name"
            class="input input-bordered"
            value={$modalState.newRole.role_name}
            on:input={(e) => {
              modalState.update(state => ({
                ...state,
                newRole: { ...state.newRole, role_name: (e.target as HTMLInputElement).value }
              }));
            }}
          />
        </div>
        <!-- Share With Peers Checkbox -->
        <div class="form-control mb-4">
          <label class="cursor-pointer label">
            <span class="label-text">Share With Peers</span>
            <input
              type="checkbox"
              class="checkbox"
              checked={$modalState.newRole.share_with_peers}
              on:change={(e) => {
                const target = e.target as HTMLInputElement;
                modalState.update(state => ({
                  ...state,
                  newRole: {
                    ...state.newRole,
                    share_with_peers: target.checked
                  }
                }));
              }}
            />
          </label>
        </div>
        <!-- Description -->
        <div class="form-control mb-4">
          <label class="label" for="description-input">Description</label>
          <input
            id="description-input"
            type="text"
            placeholder="Enter Description"
            class="input input-bordered"
            value={$modalState.newRole.description}
            on:input={(e) => {
              modalState.update(state => ({
                ...state,
                newRole: { ...state.newRole, description: (e.target as HTMLInputElement).value }
              }));
            }}
          />
        </div>
        <!-- Level -->
        <div class="form-control mb-4">
          <label class="label" for="level-input">Level</label>
          <input
            id="level-input"
            type="number"
            class="input input-bordered"
            value={$modalState.newRole.level}
            on:input={(e) => {
              const target = e.target as HTMLInputElement;
              modalState.update(state => ({
                ...state,
                newRole: { ...state.newRole, level: target.value ? +target.value : 0 }
              }));
            }}
          />
        </div>
        <div class="modal-action">
          <Button type="primary" onClick={saveRole}>Save</Button>
          <Button type="cancel" onClick={closeModal}>Cancel</Button>
        </div>
      </div>
    </div>
  {/if}
</div>
