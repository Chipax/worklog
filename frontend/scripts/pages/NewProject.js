import { API } from '../api/endpoints.js';
import { storage } from '../utils/storage.js';
import { initNavbar } from '../components/navbar.js';

let editor = null;

document.addEventListener('DOMContentLoaded', () => {
    // 1. Cargar barra de navegación dinámica
    initNavbar();

    // 2. Inicializar EditorJS
    editor = new EditorJS({
        holder: 'editorjs',
        placeholder: 'Haz clic aquí para empezar a escribir tu proyecto...',
        tools: {
            header: Header
        }
    });

    // 3. Activar previsualización de imagen
    inicializarPrevisualizacion();

    // 4. Asignar evento al botón guardar
    const guardarBtn = document.getElementById('guardar-btn');
    if (guardarBtn) {
        guardarBtn.addEventListener('click', saveProject);
    }
});

async function saveProject() {
    const inputFile = document.getElementById('project-image');
    const title = document.getElementById('project-title').value.trim();
    const description = document.getElementById('project-description').value.trim();
    
    // Obtiene el usuario autenticado desde storage (o 'Anónimo' si no hay)
    const author = storage.getUser() || 'Anónimo';

    if (!title) {
        alert('Falta el título del proyecto');
        return;
    }
    if (!description) {
        alert('Falta la descripción breve');
        return;
    }

    try {
        // Obtener bloques de EditorJS
        const outputData = await editor.save();

        const proyectoDTO = {
            title,
            author,
            description,
            content: JSON.stringify(outputData)
        };

        // 1. Guardar Proyecto (POST)
        const result = await API.projects.create(proyectoDTO);

        if (!result.success) {
            alert('Error al guardar el proyecto: ' + result.message);
            return;
        }

        const newProject = result.data;

        // 2. Subir imagen si seleccionó un archivo
        if (inputFile && inputFile.files.length > 0) {
            await uploadImage(newProject.id, inputFile.files[0]);
        }

        alert('¡Proyecto guardado con éxito!');
        window.location.href = 'index.html';

    } catch (error) {
        console.error('Error al procesar el proyecto:', error);
    }
}

async function uploadImage(id, file) {
    const formData = new FormData();
    formData.append("file", file);

    const result = await API.projects.uploadImage(id, formData);

    if (!result.success) {
        alert('El proyecto se creó pero falló la subida de la imagen.');
    }
}

function inicializarPrevisualizacion() {
    const inputFile = document.getElementById('project-image');
    const imagePreviewContainer = document.getElementById('image-preview');
    const statusMessage = document.getElementById('status-message');

    if (!inputFile || !imagePreviewContainer || !statusMessage) return;

    inputFile.addEventListener('change', function () {
        limpiarPrevisualizacion();

        if (!this.files || this.files.length === 0) return;

        const file = this.files[0];
        const maxSizeBytes = 5 * 1024 * 1024; // 5 MB

        if (!file.type.startsWith('image/')) {
            mostrarEstado('El archivo seleccionado no es una imagen válida.', '#e74c3c');
            this.value = ''; 
            return;
        }

        if (file.size > maxSizeBytes) {
            mostrarEstado('El archivo supera el tamaño máximo permitido de 5MB.', '#e74c3c');
            this.value = ''; 
            return;
        }

        const reader = new FileReader();

        reader.onload = function (e) {
            const imgElement = document.createElement('img');
            imgElement.src = e.target.result;
            imgElement.alt = 'Previsualización de portada';
            imgElement.className = 'preview-img';

            const removeBtn = document.createElement('button');
            removeBtn.textContent = '✕ Eliminar imagen';
            removeBtn.className = 'btn-remove-preview';
            removeBtn.type = 'button';

            removeBtn.addEventListener('click', () => {
                inputFile.value = ''; 
                limpiarPrevisualizacion();
                mostrarEstado('Imagen eliminada.', '#7f8c8d');
            });

            imagePreviewContainer.appendChild(imgElement);
            imagePreviewContainer.appendChild(removeBtn);

            // 💡 Hacemos visible la caja al cargar la imagen
            imagePreviewContainer.style.display = 'block';

            mostrarEstado(`Archivo listo: ${file.name}`, '#27ae60');
        };

        reader.readAsDataURL(file);
    });

    function mostrarEstado(mensaje, color) {
        statusMessage.textContent = mensaje;
        statusMessage.style.color = color;
    }

    function limpiarPrevisualizacion() {
        imagePreviewContainer.innerHTML = '';
        statusMessage.textContent = '';
        // 💡 Ocultamos la caja si se elimina o resetea la imagen
        imagePreviewContainer.style.display = 'none';
    }
}