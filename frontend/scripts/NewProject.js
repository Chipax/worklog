const guardarBtn = document.getElementById('guardar-btn');

const url = 'http://localhost:8080/api/project';

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

guardarBtn.addEventListener('click', () => {
  //Obtindre datos de EditorJS
  editor.save().then((outputData) => {
    const title = document.getElementById('project-title').value;
    const description = document.getElementById('project-description').value;
    const user = "Jan Manté";

    // Validació de camps obligatoris.
    if(!title){
      alert('Falta titulo al proyecto');
      return;
    }
    if(!description){
      alert('Falta descripcion');
      return;
    }
    // Construim el JSON que rebra el POST de la API
    const proyectoDTO = {
            title: title,
            author: "Usuario Actual", // Aquí puedes poner el autor real más adelante
            description: description,
            content: JSON.stringify(outputData) // Convertimos el JSON de EditorJS a String (así no se quejará H2)
        };

      //4. fer el Fetch a la api

      fetch(url,{
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(proyectoDTO)
      })
      .then(response => response.json())
      .then(data => {
        alert('Projecte Guardat amb exit');
        console.log(data);
      })
      .catch(error => console.error('Error: ', error));
  }).catch((error) => {
    console.error('Fallo al guardar los datos del editor: ',error);
  })

});
