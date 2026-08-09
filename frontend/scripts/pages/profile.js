import { API } from '../api/endpoints.js';
import { initNavbar } from '../components/navbar.js';
import { createProjectCard } from '../components/projectCard.js';

//TODO 
//No esta fet encara es una situacio inicial per poder visualitzar rapidament com es la pagina i si funciona.
//
//
document.addEventListener('DOMContentLoaded', async () => {
    // 1. Inicializar el navbar (muestra u oculta según sesión)
    initNavbar();

    // 2. Cargar los proyectos
    await renderProfileProjects();
});

async function renderProfileProjects() {
    const projectList = document.querySelector("#projectList");
    if (!projectList) return;

    // Petición a la API para obtener proyectos
    const result = await API.projects.getAll();

    if (!result.success) {
        projectList.innerHTML = '<p class="error-msg">No se pudieron cargar los proyectos.</p>';
        return;
    }

    const proyectos = result.data;

    if (!proyectos || proyectos.length === 0) {
        projectList.innerHTML = '<p class="empty-msg">Este usuario no tiene proyectos todavía.</p>';
        return;
    }

    // Inyectamos las tarjetas usando la plantilla de projectCard.js
    projectList.innerHTML = proyectos.map(createProjectCard).join('');
}