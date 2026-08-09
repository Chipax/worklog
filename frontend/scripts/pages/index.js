import { API } from '../api/endpoints.js';
import { initNavbar } from '../components/navbar.js';
import { createProjectCard } from '../components/projectCard.js';

document.addEventListener('DOMContentLoaded', async () => {
    // 1. Iniciar la barra de navegación (Muestra u oculta Login / Perfil)
    initNavbar();

    // 2. Cargar la lista de proyectos
    await renderProjects();
});

async function renderProjects() {
    const projectList = document.querySelector("#projectList");
    if (!projectList) return;

    // Llamada centralizada a la API (gestiona fetch, errores e inyección de token)
    const result = await API.projects.getAll();

    if (!result.success) {
        projectList.innerHTML = '<p class="error-msg">No se pudieron cargar los proyectos.</p>';
        return;
    }

    const proyectos = result.data;

    if (!proyectos || proyectos.length === 0) {
        projectList.innerHTML = '<p class="empty-msg">No hay proyectos creados todavía.</p>';
        return;
    }

    // Renderizado eficiente: crea todo el HTML en memoria y lo inyecta de una sola vez
    projectList.innerHTML = proyectos.map(createProjectCard).join('');
}