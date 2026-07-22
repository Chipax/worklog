
//seleccion de elements de configuracion
const projectList = document.querySelector("#projectList");//selecciona el div on vull posar les tarjetes de projectes
let URL = "http://localhost:8080/api/project";//URL del projecte
let URLimg = "http://localhost:8080";
async function cargarProyectos(){
    try {
        //espera fins que la url retorna una resposta
        const respuesta = await fetch(URL);
        //si falla envia un error
        if(!respuesta.ok) throw new Error('Error al conectar con la API');
        //Agafem el json de la resposta
        const proyectos = await respuesta.json();
        
        // buidem el projectList HTML(Per si hagues quedat algo)
        projectList.innerHTML = '';

        // per cada projecte de el GET de projectes executa el innerHTML, creant aixi les tarjetes
        proyectos.forEach(proyecto => {
            var imageURL = "../Images/SpringBootPicture.png"
            var imageALT = "SpringBoot image"
            projectList.innerHTML += `
            <div class="project">
                <div class="project_imagen">
                    <img src = "${URLimg}${proyecto.imageUrl}">
                </div>
                <div class="project_info">
                    <div class="project_title">
                        <h2>${proyecto.title}</h2>
                    </div>
                    <div class="project_description">
                        <p>${proyecto.description}</p>
                    <div class="project_author">
                        <p>${proyecto.author}</p>
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