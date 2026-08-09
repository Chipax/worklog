import { storage } from '../utils/storage.js';

const API_BASE_URL = 'http://localhost:8080/api';

export async function fetchAPI(endpoint, options = {}) {
    const token = storage.getToken();

    const headers = {
        'Content-Type': 'application/json',
        ...options.headers
    };

    if (token) {
        headers['Authorization'] = `Bearer ${token}`;
    }

    // 💡 SI ES FORMDATA (IMAGEN), EL NAVEGADOR DEBE ELEGIR EL CONTENT-TYPE
    if (options.body instanceof FormData) {
        delete headers['Content-Type'];
    }

    const config = {
        method: options.method || 'GET',
        headers,
        ...options
    };

    // Formatear JSON solo si NO es FormData
    if (options.body && typeof options.body === 'object' && !(options.body instanceof FormData)) {
        config.body = JSON.stringify(options.body);
    }

    try {
        const response = await fetch(`${API_BASE_URL}${endpoint}`, config);

        if (response.status === 401) {
            storage.clearSession();
        }

        const data = await response.json().catch(() => null);

        if (!response.ok) {
            return {
                success: false,
                status: response.status,
                message: data?.message || 'Error en la solicitud.'
            };
        }

        return { success: true, status: response.status, data };
    } catch (error) {
        console.error('Error de red:', error);
        return { success: false, message: 'Error de conexión con el servidor.' };
    }
}