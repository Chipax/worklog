import { storage } from '../utils/storage.js';

export function initNavbar() {
    const token = storage.getToken();
    const username = storage.getUser();

    const guestMenu = document.getElementById('guest-menu');
    const userMenu = document.getElementById('user-menu');
    const userNameSpan = document.getElementById('user-name');
    const logoutBtn = document.getElementById('logout-btn');
    const authOnlyElements = document.querySelectorAll('.auth-only');

    if (token) {
        // Estado: Logueado
        if (guestMenu) guestMenu.classList.add('hidden');
        if (userMenu) userMenu.classList.remove('hidden');
        if (userNameSpan) userNameSpan.textContent = `@${username || 'usuario'}`;

        authOnlyElements.forEach(el => el.classList.remove('hidden'));
    } else {
        // Estado: Invitado
        if (guestMenu) guestMenu.classList.remove('hidden');
        if (userMenu) userMenu.classList.add('hidden');

        authOnlyElements.forEach(el => el.classList.add('hidden'));
    }

    // Gestionar Cierre de Sesión
    if (logoutBtn) {
        logoutBtn.addEventListener('click', () => {
            storage.clearSession();
            window.location.href = 'index.html';
        });
    }
}