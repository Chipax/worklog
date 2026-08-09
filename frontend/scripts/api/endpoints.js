import { fetchAPI } from './client.js';

export const API = {
    auth: {
        login: (credentials) => fetchAPI('/auth/login', { method: 'POST', body: credentials }),
        register: (userData) => fetchAPI('/auth/register', { method: 'POST', body: userData }),
    },
    projects: {
        getAll: () => fetchAPI('/project'),
        getById: (id) => fetchAPI(`/project/${id}`),
        create: (projectData) => fetchAPI('/project', { method: 'POST', body: projectData }),
        // 💡 Endpoint para subir la portada
        uploadImage: (id, formData) => fetchAPI(`/project/${id}/image`, { method: 'POST', body: formData }),
        delete: (id) => fetchAPI(`/project/${id}`, { method: 'DELETE' })
    }
};