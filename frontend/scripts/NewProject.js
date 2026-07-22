const guardarBtn = document.getElementById('guardar-btn');
const url = 'http://localhost:8080/api/project';

//Variables per pujar fotos
const inputFile = document.getElementById('project-image');
const statusMessage = document.getElementById('status-message');
const imagePreviewContainer = document.getElementById('image-preview');

//inicialitzacions permanents

inicializarPrevisualizacion()


//Inicialitzador de EditorJS
const editor = new EditorJS({
  /**
   * Id del elemento contenedor. Editor.js buscará el <div id="editorjs">
   */
  holder: 'editorjs',
  
  /**
   * Marcador de posición (Placeholder) que sale cuando está vacío
   */
  placeholder: 'Haz clic aquí para empezar a escribir tu proyecto...',
  
  /**
   * Aquí configurarás las herramientas (Texto, Imagen, Código) más adelante.
   * Por defecto, si dejas esto vacío, ya viene con el bloque de Párrafo básico.
   */
  tools: {
    header :Header,

  }
});

//funcio per guardar projecte
async function saveProject(){
try {
    // Obtener datos de EditorJS
    const outputData = await editor.save();
    
    const title = document.getElementById('project-title').value;
    const description = document.getElementById('project-description').value;
    const user = "Jan Manté";

    // Validació de camps obligatoris
    if (!title) {
      alert('Falta titulo al proyecto');
      return;
    }
    if (!description) {
      alert('Falta descripcion');
      return;
    }

    // Construim el JSON que rebrà el POST de la API
    const proyectoDTO = {
      title: title,
      author: user, // He puesto la variable 'user' que tenías arriba
      description: description,
      content: JSON.stringify(outputData)
    };

    // Hacer la petición a la API
    const response = await fetch(url, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(proyectoDTO)
    });

    if (!response.ok) {
      throw new Error(`Error en la petición: ${response.status}`);
    }

    const data = await response.json();
     alert('Projecte Guardat amb exit '+ data.id +" "+data.title);
    console.log(data);

    // =========================================================================
    // TIP EXTRA: Si aquí ya tienes el ID del proyecto recién creado (data.id),
    // podrías llamar automáticamente a la función que sube la imagen.
    // =========================================================================
    await uploadImage(data.id);

  } catch (error) {
    console.error('Error al guardar el proyecto o en EditorJS: ', error);
  }
}


async function uploadImage(id){

  //1. Gestion de verificacion de foto
    if(inputFile.files.length === 0){
      return;
    }
    //obtenir informacio important de la foto
    const archivoAByr = inputFile.files[0];
    //2. Crear objecte de tipos FormData, i afegir el archiu

    const formData = new FormData();
    formData.append("file",archivoAByr);

    try{
    
      const response = await fetch(`${url}/${id}/image`, {
        method: 'POST',
        body: formData
      });
      //4. Gestionar la resposta(No esta fet)
      if(!response.ok){
        alert("Error");
        throw new Error(`El proyecto se creó pero fallo la subida de la imagen`);
      }
      const projectWImage = await response.json();
      console.log("Imagen subida correctamente. URL: ",projectWImage.imageUrl);

    }catch(connectionError){
      console.error("Error de conexión", connectionError);
    }
}

function inicializarPrevisualizacion() {
  const inputFile = document.getElementById('project-image');
  const imagePreviewContainer = document.getElementById('image-preview');
  const statusMessage = document.getElementById('status-message');

  if (!inputFile || !imagePreviewContainer || !statusMessage) {
    console.warn("No se encontraron los elementos necesarios para la previsualización.");
    return;
  }

  // Evento que se dispara al seleccionar un archivo
  inputFile.addEventListener('change', function () {
    // 1. Limpiar previsualizaciones y mensajes anteriores
    limpiarPrevisualizacion();

    if (!this.files || this.files.length === 0) return;

    const file = this.files[0];
    const maxSizeBytes = 5 * 1024 * 1024; // 5 MB

    // 2. Validar que sea una imagen
    if (!file.type.startsWith('image/')) {
      mostrarEstado('El archivo seleccionado no es una imagen válida.', '#e74c3c');
      this.value = ''; 
      return;
    }

    // 3. Validar el tamaño máximo (5MB)
    if (file.size > maxSizeBytes) {
      mostrarEstado('El archivo supera el tamaño máximo permitido de 5MB.', '#e74c3c');
      this.value = ''; 
      return;
    }

    // 4. Leer el archivo y generar la vista previa (FileReader)
    const reader = new FileReader();

    reader.onload = function (e) {
      // Elemento de imagen
      const imgElement = document.createElement('img');
      imgElement.src = e.target.result;
      imgElement.alt = 'Previsualización de portada';
      imgElement.className = 'preview-img';

      // Botón para eliminar la imagen seleccionada
      const removeBtn = document.createElement('button');
      removeBtn.textContent = '✕ Eliminar imagen';
      removeBtn.className = 'btn-remove-preview';
      removeBtn.type = 'button';

      removeBtn.addEventListener('click', () => {
        inputFile.value = ''; 
        limpiarPrevisualizacion();
        mostrarEstado('Imagen eliminada.', '#7f8c8d');
      });

      // Añadir al contenedor HTML
      imagePreviewContainer.appendChild(imgElement);
      imagePreviewContainer.appendChild(removeBtn);

      mostrarEstado(`Archivo listo: ${file.name}`, '#27ae60');
    };

    reader.readAsDataURL(file);
  });

  // Funciones auxiliares internas para reutilizar código
  function mostrarEstado(mensaje, color) {
    statusMessage.textContent = mensaje;
    statusMessage.style.color = color;
  }

  function limpiarPrevisualizacion() {
    imagePreviewContainer.innerHTML = '';
    statusMessage.textContent = '';
  }
}

guardarBtn.addEventListener('click', saveProject);
