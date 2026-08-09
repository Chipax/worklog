import { API } from '../api/endpoints.js';
import { storage } from '../utils/storage.js';

document.addEventListener('DOMContentLoaded', () => {
    // Redirigir si ya tiene sesión
    if (storage.getToken()) {
        window.location.href = 'index.html';
        return;
    }

    const loginForm = document.getElementById('login-form');
    const errorMessage = document.getElementById('error-message');

    loginForm.addEventListener('submit', async (e) => {
        e.preventDefault();

        const username = document.getElementById('username').value.trim();
        const password = document.getElementById('password').value;

        const result = await API.auth.login({ username, password });

        if (result.success) {
            storage.setToken(result.data.token);
            storage.setUser(result.data.username || username);
            window.location.href = 'index.html';
        } else {
            errorMessage.textContent = result.message;
            errorMessage.classList.remove('hidden');
        }
    });
});