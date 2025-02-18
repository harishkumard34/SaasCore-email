<script lang="ts">
  import { onMount } from 'svelte';
  import { writable, get } from 'svelte/store';
  import Button from '../../../components/Button.svelte';
  import Table from '../../../components/Table.svelte';
  import { Country, State, City } from 'country-state-city';
  import {getAuthToken} from '$lib';
  import Tabs from '../../../components/Tabs.svelte';

const myTabs = [
  { labels: 'Users', href: '/settings/users' },
  { labels: 'Roles', href: '/settings/roles' },
  { labels: 'Profiles', href: '/settings/profiles' }
];

  interface RolePayload {
    id: number;
    name: string;
  }

  interface User {
    user_id?: number | null;
    first_name: string;
    last_name: string;
    email_id: string;
    profile?: { profile_id: string; profile_name: string } | null;
    role: string | RolePayload;
    country: string;
    state: string;
    city: string;
    address: string;
    zip: string;
    status: string;
    street: string;
    dob: string;
    created_time?: number;
    modified_time?: number;
    createdBy: number;
    modifiedBy: number;
    gender: string;
    time_zone: string;
    language: string;
    locale: string;
  }

  const users = writable<User[]>([]);


  
  function getHeaders() {
    return {
      ...getAuthToken(),
      'Content-Type': 'application/json'
    };
  }

  let countries: { value: string; label: string }[] = [];
  let states: { value: string; label: string }[] = [];
  let cities: { value: string; label: string }[] = [];

  async function fetchCountries() {
    countries = Country.getAllCountries().map(c => ({ value: c.isoCode, label: c.name }));
  }

  async function fetchStates(countryCode: string) {
    states = State.getStatesOfCountry(countryCode).map(s => ({ value: s.isoCode, label: s.name }));
  }

  async function fetchCities(countryCode: string, stateCode: string) {
    cities = City.getCitiesOfState(countryCode, stateCode).map(c => ({ value: c.name, label: c.name }));
  }

  onMount(async () => {
    await fetchCountries();
    fetchUsers();
  });

  async function fetchUsers() {
    try {
      const response = await fetch('http://localhost:8080/v1/users', { headers: getHeaders() });
      if (!response.ok) throw new Error('Failed to fetch users');
      const data = await response.json();

      console.log('Fetched users:', JSON.stringify(data, null, 2));

      const mappedUsers = Array.isArray(data)
        ? data.map(user => ({
            user_id: user.user_id || null,
            first_name: user.first_name,
            last_name: user.last_name,
            email_id: user.email_id,
            role: user.role?.name || '',
            country: user.country || '',
            state: user.state || '',
            city: user.city || '',
            address: user.address || '',
            zip: user.zip || '',
            status: user.status || 'ACTIVE',
            street: user.street || '',
            dob: user.dob || '',
            created_time: user.created_time ? new Date(user.created_time).getTime() : Date.now(),
            modified_time: user.modified_time ? new Date(user.modified_time).getTime() : Date.now(),
            profile: user.profile ? { profile_id: user.profile.id, profile_name: user.profile.name } : null,
            createdBy: user.createdBy || 0,
            modifiedBy: user.modifiedBy || 0,
            gender: user.gender || '',
            time_zone: user.time_zone ? user.time_zone.toString() : '',
            language: user.language || '',
            locale: user.locale || ''
          }))
        : [];

      users.set(mappedUsers);
    } catch (error) {
      console.error('Fetch Users Error:', error);
    }
  }

  const modalState = writable({
    showModal: false,
    currentUser: null as User | null,
    newUser: {
      user_id: null,
      first_name: '',
      last_name: '',
      email_id: '',
      role: '',
      country: '',
      state: '',
      city: '',
      address: '',
      zip: '',
      status: 'ACTIVE',
      street: '',
      dob: '',
      created_time: Date.now(),
      modified_time: Date.now(),
      profile: { profile_id: '1000000000002', profile_name: 'Administrator' }, // Default profile
      createdBy: 0,
      modifiedBy: 0,
      gender: '',
      time_zone: '',
      language: '',
      locale: ''
    } as User
  });

  function openModal(user: User | null = null) {
    modalState.update(state => ({
      ...state,
      showModal: true,
      currentUser: user ? { ...user } : null,
      newUser: user
        ? { ...user }
        : {
            user_id: null,
            first_name: '',
            last_name: '',
            email_id: '',
            role: '',
            country: '',
            state: '',
            city: '',
            address: '',
            zip: '',
            status: 'ACTIVE',
            street: '',
            dob: '',
            created_time: Date.now(),
            modified_time: Date.now(),
            profile: { profile_id: '', profile_name: 'Administer' },
            createdBy: 0,
            modifiedBy: 0,
            gender: '',
            time_zone: '',
            language: '',
            locale: ''
          }
    }));
    if (user?.country) fetchStates(user.country);
    if (user?.state) fetchCities(user.country, user.state);
  }

  function closeModal() {
    modalState.update(state => ({ ...state, showModal: false }));
  }

  async function removeUser(user_id: number) {
    try {
      const response = await fetch(`http://localhost:8080/v1/users/${user_id}`, {
        method: 'DELETE',
        headers: getHeaders()
      });
      if (!response.ok) throw new Error('Failed to delete user');
      console.log('User deleted successfully');
      fetchUsers();
    } catch (error) {
      console.error('Remove User Error:', error);
    }
  }

  function handleCountryChange(event: Event) {
    const selectedCountry = (event.target as HTMLSelectElement).value;
    modalState.update(state => ({
      ...state,
      newUser: {
        ...state.newUser,
        country: selectedCountry,
        state: '',
        city: ''
      }
    }));
    fetchStates(selectedCountry);
  }

  function handleStateChange(event: Event) {
    const selectedState = (event.target as HTMLSelectElement).value;
    modalState.update(state => ({
      ...state,
      newUser: {
        ...state.newUser,
        state: selectedState
      }
    }));
    fetchCities(get(modalState).newUser.country, selectedState);
  }

  function handleCityChange(event: Event) {
    const selectedCity = (event.target as HTMLSelectElement).value;
    modalState.update(state => ({
      ...state,
      newUser: {
        ...state.newUser,
        city: selectedCity
      }
    }));
  }

  async function saveUser() {
    const { newUser } = get(modalState);
    const method = newUser.user_id ? 'PUT' : 'POST';

    // Format dob to include a time component (e.g., "2025-02-13T00:00:00")
    const formattedDob = newUser.dob ? `${newUser.dob}T00:00:00` : '';

    // Build the complete user payload with the formatted dob
    const payload = {
      user_id: newUser.user_id,
      first_name: newUser.first_name,
      last_name: newUser.last_name,
      email_id: newUser.email_id,
      country: newUser.country,
      state: newUser.state,
      city: newUser.city,
      address: newUser.address,
      zip: newUser.zip,
      status: 'ACTIVE',
      street: newUser.street || '',
      dob: formattedDob,
      created_time: Date.now(),
      modified_time: Date.now(),
      createdBy: newUser.createdBy || 0,
      modifiedBy: newUser.modifiedBy || 0,
      gender: newUser.gender || '',
      time_zone: newUser.time_zone || '',
      language: newUser.language || '',
      locale: newUser.locale || ''
    };

    const url = newUser.user_id
      ? `http://localhost:8080/v1/users/${newUser.user_id}`
      : 'http://localhost:8080/v1/users/';

    try {
      const response = await fetch(url, {
        method,
        headers: getHeaders(),
        body: JSON.stringify(payload)
      });

      if (!response.ok) {
        const errorData = await response.text();
        throw new Error(`Failed to save user: ${errorData}`);
      }

      console.log('User saved successfully');
      closeModal();
      fetchUsers();
    } catch (error) {
      console.error('Save User Error:', error);
    }
  }
</script>

<style>
  .container {
    padding: 2rem;
  }
  .form-control {
    margin-bottom: 1rem;
  }
  .form-control label {
    display: block;
    margin-bottom: 0.5rem;
  }
  .input, .select {
    width: 100%;
    padding: 0.5rem;
  }
</style>

<Tabs tabs={myTabs}/>


<div class="container">
  <h2 class="text-2xl font-bold">Users</h2>
  <br />
  <Button type="primary" onClick={() => openModal()}>Create User</Button>
  <br /><br />

  <Table>
    {#snippet TableHead()}
      <th>First Name</th>
      <th>Last Name</th>
      <th>Email</th>
      <th>Country</th>
      <th>State</th>
      <th>City</th>
      <th>Address</th>
      <th>Zip</th>
      <th>Actions</th>
    {/snippet}
    {#snippet TableRows()}
      {#each $users as user (user.user_id ?? user.email_id)}
        <tr>
          <td>{user.first_name}</td>
          <td>{user.last_name}</td>
          <td>{user.email_id}</td>
          <td>{user.country}</td>
          <td>{user.state}</td>
          <td>{user.city}</td>
          <td>{user.address}</td>
          <td>{user.zip}</td>
          <td>
            <Button type="primary" size="sm" onClick={() => openModal(user)}>Edit</Button>
            {#if user.user_id != null}
              <Button type="cancel" size="sm" onClick={() => removeUser(user.user_id!)}>Delete</Button>
            {/if}
          </td>
        </tr>
      {/each}
    {/snippet}
  </Table>

  {#if $modalState.showModal}
    <div class="modal modal-open">
      <div class="modal-box">
        <div class="form-control">
          <label for="firstName">First Name</label>
          <input id="firstName" type="text" class="input" bind:value={$modalState.newUser.first_name} />
        </div>
        <div class="form-control">
          <label for="lastName">Last Name</label>
          <input id="lastName" type="text" class="input" bind:value={$modalState.newUser.last_name} />
        </div>
        <div class="form-control">
          <label for="email">Email</label>
          <input id="email" type="email" class="input" bind:value={$modalState.newUser.email_id} />
        </div>
        <div class="form-control">
          <label for="country">Country</label>
          <select id="country" class="select" bind:value={$modalState.newUser.country} on:change={handleCountryChange}>
            <option value="">Select Country</option>
            {#each countries as country}
              <option value={country.value}>{country.label}</option>
            {/each}
          </select>
        </div>
        <div class="form-control">
          <label for="state">State</label>
          <select id="state" class="select" bind:value={$modalState.newUser.state} on:change={handleStateChange}>
            <option value="">Select State</option>
            {#each states as state}
              <option value={state.value}>{state.label}</option>
            {/each}
          </select>
        </div>
        <div class="form-control">
          <label for="city">City</label>
          <select id="city" class="select" bind:value={$modalState.newUser.city} on:change={handleCityChange}>
            <option value="">Select City</option>
            {#each cities as city}
              <option value={city.value}>{city.label}</option>
            {/each}
          </select>
        </div>
        <div class="form-control">
          <label for="address">Address</label>
          <input id="address" type="text" class="input" bind:value={$modalState.newUser.address} />
        </div>
        <div class="form-control">
          <label for="zip">Zip</label>
          <input id="zip" type="text" class="input" bind:value={$modalState.newUser.zip} />
        </div>
        <!-- New fields added below -->
        <div class="form-control">
          <label for="dob">Date of Birth</label>
          <input id="dob" type="date" class="input" bind:value={$modalState.newUser.dob} />
        </div>
        <div class="form-control">
          <label for="street">Street</label>
          <input id="street" type="text" class="input" bind:value={$modalState.newUser.street} />
        </div>
        <div class="form-control">
          <label for="gender">Gender</label>
          <select id="gender" class="select" bind:value={$modalState.newUser.gender}>
            <option value="">Select Gender</option>
            <option value="Male">Male</option>
            <option value="Female">Female</option>
            <option value="Other">Other</option>
          </select>
        </div>
        <div class="form-control">
          <label for="language">Language</label>
          <input id="language" type="text" class="input" bind:value={$modalState.newUser.language} />
        </div>
        <div class="modal-action">
          <Button type="cancel" onClick={closeModal}>Cancel</Button>
          <Button type="primary" onClick={saveUser}>Save</Button>
        </div>
      </div>
    </div>
  {/if}
</div>
