package com.worklog.demo.controller;

import com.worklog.demo.Domain.Project;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProjectControllerTest {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";

    @LocalServerPort
    private int port;

    @Test
    void findAll() {
        RestClient restClient = RestClient.create("http://localhost:" + port);
        Project nuevoProyecto = new Project("Worklog", "Jan Manté", "A simple worklog application");
        Project nuevoProyecto2 = new Project("Worklog2", "Jan Manté", "A simple worklog application");

        ResponseEntity<Project> respuesta = restClient.post()
                .uri("/project")
                .body(nuevoProyecto)
                .retrieve()
                .toEntity(Project.class);
        ResponseEntity<Project> respuesta2 = restClient.post()
                .uri("/project")
                .body(nuevoProyecto2)
                .retrieve()
                .toEntity(Project.class);

        ResponseEntity<Project[]> respuestaGet = restClient.get()
                .uri("/project")
                .retrieve()
                .toEntity(Project[].class);
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        Project[] proyectos = respuestaGet.getBody();

        assertNotNull(proyectos);
        assertEquals(2, proyectos.length);
        System.out.println(proyectos[0]);
        System.out.println(proyectos[1]);
        System.out.println(ANSI_GREEN+"¡Proyectos obtenidos con éxito usando RestClient!"+ANSI_RESET);
    }

    @Test
    void findById() {
        RestClient restClient = RestClient.create("http://localhost:" + port);
        Project nuevoProyecto = new Project("Worklog", "Jan Manté", "A simple worklog application");

        ResponseEntity<Project> respuesta = restClient.post()
                .uri("/project")
                .body(nuevoProyecto)
                .retrieve()
                .toEntity(Project.class);

        Long id = respuesta.getBody().getId();

        ResponseEntity<Project> respuestaGet = restClient.get()
                .uri("/project/" + id)
                .retrieve()
                .toEntity(Project.class);

        assertEquals(HttpStatus.OK, respuestaGet.getStatusCode());
        assertNotNull(respuestaGet.getBody());
        System.out.println(ANSI_GREEN+"¡Proyecto obtenido con éxito usando RestClient!"+ANSI_RESET);
    }

    @Test
    void createProduct() {

        RestClient restClient = RestClient.create("http://localhost:" + port);
        // ==========================================
        // 1. TEST @PostMapping (Crear)
        // ==========================================
        Project nuevoProyecto = new Project("Worklog", "Jan Manté", "A simple worklog application");

        ResponseEntity<Project> respuesta = restClient.post()
                .uri("/project")
                .body(nuevoProyecto)
                .retrieve()
                .toEntity(Project.class);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
        System.out.println(ANSI_GREEN+"¡Proyecto creado con éxito usando RestClient!"+ANSI_RESET);

    }

    @Test
    void updateProject() {
        RestClient restClient = RestClient.create("http://localhost:" + port);
        Project nuevoProyecto = new Project("Worklog", "Jan Manté", "A simple worklog application");
        Project nuevoProyecto2 = new Project("Worklog2", "Jan Manté", "A simple worklog application");

        ResponseEntity<Project> responsePost = restClient.post()
                .uri("/project")
                .body(nuevoProyecto)
                .retrieve()
                .toEntity(Project.class);

        Long id = responsePost.getBody().getId();

        ResponseEntity<Project> responsePut = restClient.put()
                .uri("/project/" + id)
                .body(nuevoProyecto2)
                .retrieve()
                .toEntity(Project.class);

        Project updatedProject = responsePut.getBody();

        assertEquals(HttpStatus.OK, responsePut.getStatusCode());
        assertNotNull(updatedProject);
        assertEquals("Worklog2", updatedProject.getTitol());
        System.out.println(ANSI_GREEN+"¡Proyecto actualizado con éxito usando RestClient!"+ANSI_RESET);
    }

    @Test
    void deleteProject() {
    }
}