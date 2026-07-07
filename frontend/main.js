const projectList = document.querySelector("#projectList");
let URL = "http://localhost:8080/project";

async function cargarProyectos(){
    try {
        //
        const respuesta = await fetch(URL);
        if(!respuesta.ok) throw new Error('Error al conectar con la API');

        const proyectos = await respuesta.json();
        

        projectList.innerHTML = '';

        proyectos.forEach(proyecto => {

            projectList.innerHTML += `
            <div class="project">
                <div class="project_imagen">
                </div>
                <div class="project_info">
                    <div class="project_title">
                        <h2>${proyecto.titol}</h2>
                    </div>
                    <div class="project_description">
                        <p>${proyecto.contingut}</p>
                    <div class="project_author">
                        <p>${proyecto.autor}</p>
                    </div>
                    <div class="project_tech">
                        <p class="tech">Spring boot</p>
                        <p class="tech">Html+css+js</p>
                        <p class="tech">docker</p>
                        <p class="tech">SQL</p>
                    </div>
                </div>
            </div>
            `;
        });
        
    } catch(error) {
        console.error('Hubo un problema', error);
        projectList.innerHTML = '<p>No se pudieron cargar los proyectos</p>'; 
    }
}

cargarProyectos();

/*<div class="project">
                <div class="project_imagen">
                </div>
                <div class="project_info">
                    <div class="project_title">
                        <h2>${proyecto.titol}</h2>
                    </div>
                    <div class="project_description">
                        <p>${proyecto.contingut}</p>
                    <div class="project_author">
                        <p>${proyecto.autor}</p>
                    </div>
                    <div class="project_tech">
                        <p class="tech">Spring boot</p>
                        <p class="tech">Html+css+js</p>
                        <p class="tech">docker</p>
                        <p class="tech">SQL</p>
                    </div>
                </div>
            </div> */