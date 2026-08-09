

const BASE_IMAGE_URL = 'http://localhost:8080';

/**
 * Genera el HTML de una tarjeta de proyecto.
 * @param {Object} proyecto - Datos del proyecto devueltos por la API
 * @returns {string} HTML en string de la tarjeta
 */
export function createProjectCard(proyecto) {
    const imagenHTML = proyecto.imageUrl 
        ? `<img src="${BASE_IMAGE_URL}${proyecto.imageUrl}" alt="${proyecto.title}">` 
        : '';

    // Si tu API devuelve un array de tecnologías úsalo; si no, dejamos el fallback por defecto
    const techStackHTML = proyecto.techStack && proyecto.techStack.length > 0
        ? proyecto.techStack.map(tech => `<p class="tech">${tech}</p>`).join('')
        : `
            <p class="tech">Spring boot</p>
            <p class="tech">Html+css+js</p>
            <p class="tech">docker</p>
            <p class="tech">SQL</p>
        `;

    return `
        <div class="project" onclick="window.location.href='${BASE_IMAGE_URL}/api/project/${proyecto.id}'">
            <div class="project_imagen">
                ${imagenHTML}
            </div>
            <div class="project_info">
                <div class="project_title">
                    <h2>${proyecto.title}</h2>
                </div>
                <div class="project_description">
                    <p>${proyecto.description}</p>
                </div>
                <div class="project_author">
                    <p>${proyecto.author || 'Anónimo'}</p>
                </div>
                <div class="project_tech">
                    ${techStackHTML}
                </div>
            </div>
        </div>
    `;
}