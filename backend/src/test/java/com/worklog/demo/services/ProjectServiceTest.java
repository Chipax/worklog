package com.worklog.demo.services;

import com.worklog.demo.Domain.Project;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProjectServiceTest {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";

    @Autowired
    private ProjectService projectService;


    @Test
    void testGuardarIrecuperarProjecte() {
        System.out.println(ANSI_PURPLE + "Test: test Guardar I recuperar Projecte" + ANSI_RESET);
        Project project = new Project();
        project.setTitle("Test Project");
        project.setAuthor("Test Author");
        project.setContent("Test Content");

        Project savedProject = projectService.saveProject(project);
        assertNotNull(savedProject.getId(), "El projecte guardat hauria de tenir un ID assignat.");

        Project retrievedProject = projectService.getProjectById(savedProject.getId()).orElse(null);
        assertNotNull(retrievedProject, "El projecte recuperat no hauria de ser nul.");
        assertEquals("Test Project", retrievedProject.getTitle(), "El títol del projecte recuperat no coincideix.");
        assertEquals("Test Author", retrievedProject.getAuthor(), "L'autor del projecte recuperat no coincideix.");
        assertEquals("Test Content", retrievedProject.getContingut(), "El contingut del projecte recuperat no coincideix.");
    }

    @Test
    void testGuardarProjecteSenseTitol() {
        System.out.println(ANSI_PURPLE + "Test: test Guardar Projecte sense Titol" + ANSI_RESET);
        Project project = new Project();
        project.setAuthor("Test Author");
        project.setContent("Test Content");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            projectService.saveProject(project);
        });

    }

    @Test
    void getAllProjects() {
        System.out.println(ANSI_PURPLE + "Test: test Get All Projects" + ANSI_RESET);


        Project project = new Project("Test Project", "Test Author", "Test Content");
        Project projec2 = new Project("Test Project2", "Test Author2", "Test Content2");

        projectService.saveProject(project);
        projectService.saveProject(projec2);

        List<Project> projectsAfter = projectService.getAllProjects();


        assertTrue(projectsAfter.size() >= 2);

        System.out.println(ANSI_GREEN + "✔ ¡ÉXITO! Se recuperaron los proyectos correctamente." + ANSI_RESET);

    }

    @Test
    void getProjectById() {
        System.out.println(ANSI_PURPLE + "Test: Get projects by ID " + ANSI_RESET);


        Project project = new Project("Test Project", "Test Author", "Test Content");
        Project projec2 = new Project("Test Project2", "Test Author2", "Test Content2");

        projectService.saveProject(project);
        projectService.saveProject(projec2);

        Project retrievedProject = projectService.getProjectById(project.getId()).orElse(null);
        Project retrievedProject2 = projectService.getProjectById(projec2.getId()).orElse(null);

        assertNotNull(retrievedProject, "El projecte recuperat no hauria de ser nul.");
        assertEquals("Test Project", retrievedProject.getTitle(), "El títol del projecte recuperat no coincideix.");
        assertEquals("Test Author", retrievedProject.getAuthor(), "L'autor del projecte recuperat no coincideix.");
        assertEquals("Test Content", retrievedProject.getContent(), "El contingut del projecte recuperat no coincideix.");

        assertNotNull(retrievedProject2, "El projecte recuperat no hauria de ser nul.");
        assertEquals("Test Project2", retrievedProject2.getTitle(), "El títol del projecte recuperat no coincideix.");
        assertEquals("Test Author2", retrievedProject2.getAuthor(), "L'autor del projecte recuperat no coincideix.");
        assertEquals("Test Content2", retrievedProject2.getContent(), "El contingut del projecte recuperat no coincideix.");

    }



    @Test
    void deleteProject() {System.out.println(ANSI_PURPLE + "Test: Delete projects by ID " + ANSI_RESET);


        Project project = new Project("Test Project", "Test Author", "Test Content");
        Project project2 = new Project("Test Project2", "Test Author2", "Test Content2");

        projectService.saveProject(project);
        projectService.saveProject(project2);

        Project retrievedProject = projectService.getProjectById(project.getId()).orElse(null);
        Project retrievedProject2 = projectService.getProjectById(project2.getId()).orElse(null);

        assertNotNull(retrievedProject, "El projecte recuperat no hauria de ser nul.");
        assertEquals("Test Project", retrievedProject.getTitle(), "El títol del projecte recuperat no coincideix.");
        assertEquals("Test Author", retrievedProject.getAuthor(), "L'autor del projecte recuperat no coincideix.");
        assertEquals("Test Content", retrievedProject.getContent(), "El contingut del projecte recuperat no coincideix.");

        assertTrue(projectService.deleteProject(retrievedProject.getId()));
        assertTrue(projectService.deleteProject(retrievedProject2.getId()));

        assertTrue(projectService.getProjectById(retrievedProject.getId()).isEmpty());
        assertTrue(projectService.getProjectById(retrievedProject2.getId()).isEmpty());

    }

    @Test
    void updateProject() {
        System.out.println(ANSI_PURPLE + "Test: Update projects by ID " + ANSI_RESET);

        Project project = new Project("Test Project", "Test Author", "Test Content");
        Project project2 = new Project("Test Project2", "Test Author2", "Test Content2");


        projectService.saveProject(project);

        Project retrievedProject = projectService.getProjectById(project.getId()).orElse(null);

        projectService.updateProject(retrievedProject.getId(), project2);

        retrievedProject = projectService.getProjectById(retrievedProject.getId()).orElse(null);

        assertNotNull(retrievedProject, "El projecte recuperat no hauria de ser nul.");
        assertEquals("Test Project2", retrievedProject.getTitle(), "El títol del projecte recuperat no coincideix.");
        assertEquals("Test Author2", retrievedProject.getAuthor(), "L'autor del projecte recuperat no coincideix.");
        assertEquals("Test Content2", retrievedProject.getContent(), "El contingut del projecte recuperat no coincideix.");
    }
}