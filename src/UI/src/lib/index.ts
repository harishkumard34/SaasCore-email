// place files you want to import through the `$lib` alias in this folder.
export function getAuthToken() {
    const token = localStorage.getItem('authToken')

    return {

        'X-Session-ID': `${token}`
    }
}
