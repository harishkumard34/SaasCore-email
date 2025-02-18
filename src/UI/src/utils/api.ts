import { getHeaders } from './security';

export async function makeXhr(url: string, method: string, params: any, extraHeaders: any = {}) {
    const headers = { ...getHeaders(), ...extraHeaders, 'Content-Type': 'application/json' };

    const response = await fetch(url, {
        method,
        headers,
        body: JSON.stringify(params)
    });

    return response;
}
