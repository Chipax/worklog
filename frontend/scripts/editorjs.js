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