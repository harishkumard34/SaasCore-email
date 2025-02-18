<script lang="ts">
  import { onMount } from 'svelte';
  import { writable, get } from 'svelte/store';
  import Button from '../../../components/Button.svelte';
  import Card from '../../../components/Card.svelte';
  import Text from '../../../components/Text.svelte';
  import { getAuthToken } from '$lib';

  interface Profile {
    profile_id: number | null;
    profile_name: string;
    description: string;
    status: string | undefined;
    created_time: number;
    modified_time: number;
  }

  const profiles = writable<Profile[]>([]);

  async function fetchProfiles() {
    try {
      console.log('Fetching profiles...');
      const response = await fetch('http://localhost:8080/v1/profiles', {
        headers: {
          ...getAuthToken(),
          'Content-Type': 'application/json',
        },
      });

      if (!response.ok) throw new Error('Failed to fetch profiles');
      const data = await response.json();

      if (!Array.isArray(data)) throw new Error('Invalid response format');

      profiles.set(
        data.map((profile: any) => ({
          profile_id: profile.profile_id ?? null,
          profile_name: profile.profile_name,
          description: profile.description,
          status: profile.status,
          created_time: new Date(profile.created_time).getTime(),
          modified_time: new Date(profile.modified_time).getTime(),
        }))
      );
    } catch (error) {
      console.error('Fetch Profiles Error:', error);
    }
  }

  onMount(fetchProfiles);

  const modalState = writable({
    showModal: false,
    currentProfile: null as Profile | null,
    newProfile: {
      profile_id: null,
      profile_name: '',
      description: '',
      status: '',
      created_time: Date.now(),
      modified_time: Date.now(),
    } as Profile,
  });

  function openModal(profile: Profile | null = null) {
    modalState.update((state) => ({
      ...state,
      showModal: true,
      currentProfile: profile ? { ...profile } : null,
      newProfile: profile ? { ...profile } : {
        profile_id: null,
        profile_name: '',
        description: '',
        status: '',
        created_time: Date.now(),
        modified_time: Date.now(),
      },
    }));
  }

  function closeModal() {
    modalState.update((state) => ({ ...state, showModal: false }));
  }

  async function saveProfile() {
    const profileData = {
      profile_id: get(modalState).newProfile.profile_id ?? null,
      profile_name: get(modalState).newProfile.profile_name,
      description: get(modalState).newProfile.description,
      status: get(modalState).newProfile.status,
      created_time: get(modalState).newProfile.created_time,
      modified_time: Date.now(),
    };

    try {
      const method = get(modalState).currentProfile ? 'PUT' : 'POST';
      const url = get(modalState).currentProfile
        ? `http://localhost:8080/v1/profiles/${get(modalState).currentProfile?.profile_id}`
        : 'http://localhost:8080/v1/profiles/';

      const response = await fetch(url, {
        method,
        headers: {
          ...getAuthToken(),
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(profileData),
      });

      if (!response.ok) {
        const errorDetails = await response.text();
        console.error('Error saving profile:', errorDetails);
        throw new Error('Failed to save profile');
      }

      console.log('Profile saved successfully');
      await fetchProfiles();
    } catch (error) {
      console.error('Error:', error);
    } finally {
      modalState.update((state) => ({ ...state, showModal: false }));
    }
  }

  async function removeProfile(profile_id: number) {
    try {
      const response = await fetch(`http://localhost:8080/v1/profiles/${profile_id}`, {
        method: 'DELETE',
        headers: {
          ...getAuthToken(),
          'Content-Type': 'application/json',
        },
      });

      if (!response.ok) {
        const errorDetails = await response.text();
        console.error('Error deleting profile:', errorDetails);
        throw new Error('Failed to delete profile');
      }

      console.log('Profile deleted successfully');
      await fetchProfiles();
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
<h2 class="text-2xl font-bold">Profiles</h2>
<br />
<Button type="primary" clazz="btn btn-primary" onClick={() => openModal()}>Create Profile</Button>
<br /><br />
<div class="grid grid-cols-1 gap-4 sm:grid-cols-2 lg:grid-cols-3">
  {#each $profiles as profile, index (profile.profile_id ?? index)}
    <Card clazz="w-full bg-base-100 shadow-md">
      <div class="card-body">
        <h3 class="card-title">{profile.profile_name}</h3>
        <p>{profile.description}</p>
        <p>Status: {profile.status}</p>
        <div class="card-actions">
          <Button type="primary" size="sm" onClick={() => openModal(profile)}>Edit</Button>
          <Button type="cancel" size="sm" onClick={() => profile.profile_id !== null && removeProfile(profile.profile_id)}>Delete</Button>
        </div>
      </div>
    </Card>
  {/each}
</div>

{#if $modalState.showModal}
<div class="modal modal-open">
  <div class="modal-box">
    <div class="form-control mb-4">
      <Text type="text" placeholder="Enter the Name" bind:value={$modalState.newProfile.profile_name}>Name</Text>
    </div>
    <div class="form-control mb-4">
      <Text type="text" placeholder="Enter the Description" bind:value={$modalState.newProfile.description}>Description</Text>
    </div>
    <div class="form-control mb-4">
      <Text type="text" placeholder="Enter the Status" bind:value={$modalState.newProfile.status}>Status</Text>
    </div>
    <div class="modal-action">
      <Button type="primary" onClick={saveProfile}>Save</Button>
      <Button type="cancel" onClick={closeModal}>Cancel</Button>
    </div>
  </div>
</div>
{/if}
</div>
