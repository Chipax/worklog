package com.worklog.demo.services;

import com.worklog.demo.DTO.DTOs.ProjectDTO;
import com.worklog.demo.DTO.mappers.ProjectMapper;
import com.worklog.demo.Domain.Project;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ProjectServiceTest {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_PURPLE = "\u001B[35m";

    @Autowired
    private ProjectService projectService;


    @Test
    void testGuardarIrecuperarProjecte() {
        System.out.println(ANSI_PURPLE + "Test: test Guardar I recuperar Projecte" + ANSI_RESET);
        Project project = new Project();
        project.setTitle("Test Project");
        project.setAuthor("Test Author");
        project.setDescription("Test Description");

        ProjectDTO.Response savedProjectDTO = projectService.saveProject(new ProjectDTO.Create(project.getTitle(), project.getAuthor(), project.getDescription(), project.getContent()));
        Project savedProject = ProjectMapper.toEntity(savedProjectDTO);
        assertNotNull(savedProject.getId(), "El projecte guardat hauria de tenir un ID assignat.");

        ProjectDTO.Response retrievedProjectDTO = projectService.getProjectById(savedProjectDTO.id()).orElse(null);
        Project retrievedProject = ProjectMapper.toEntity(retrievedProjectDTO);
        assertNotNull(retrievedProject, "El projecte recuperat no hauria de ser nul.");
        assertEquals("Test Project", retrievedProject.getTitle(), "El títol del projecte recuperat no coincideix.");
        assertEquals("Test Author", retrievedProject.getAuthor(), "L'autor del projecte recuperat no coincideix.");
    }

    @Test
    void testGuardarProjecteSenseTitol() {
        System.out.println(ANSI_PURPLE + "Test: test Guardar Projecte sense Titol" + ANSI_RESET);
        Project project = new Project();
        project.setAuthor("Test Author");
        project.setDescription("Test Description");

        assertThrows(IllegalArgumentException.class, () -> projectService.saveProject(new ProjectDTO.Create(project.getTitle(), project.getAuthor(), project.getDescription(), project.getContent())));
    }

    @Test
    void getAllProjects() {
        System.out.println(ANSI_PURPLE + "Test: test Get All Projects" + ANSI_RESET);


        Project project = new Project("Test Project", "Test Author", "Test Description");
        project.setDescription("Test Description");
        Project projec2 = new Project("Test Project2", "Test Author2", "Test Description2");
        projec2.setDescription("Test Description2");

        projectService.saveProject(new ProjectDTO.Create(project.getTitle(), project.getAuthor(), project.getDescription(), project.getContent()));
        projectService.saveProject(new ProjectDTO.Create(projec2.getTitle(), projec2.getAuthor(), projec2.getDescription(), projec2.getContent()));

        List<ProjectDTO.Response> projectsAfter = projectService.getAllProjects();


        assertTrue(projectsAfter.size() >= 2);

        System.out.println(ANSI_GREEN + "✔ ¡ÉXITO! Se recuperaron los proyectos correctamente." + ANSI_RESET);

    }

    @Test
    void getProjectById() {
        System.out.println(ANSI_PURPLE + "Test: Get projects by ID " + ANSI_RESET);


        Project project = new Project("Test Project", "Test Author", "Test Description");
        project.setDescription("Test Description");
        Project projec2 = new Project("Test Project2", "Test Author2", "Test Description2");
        projec2.setDescription("Test Description2");

        ProjectDTO.Response savedDTO1 = projectService.saveProject(new ProjectDTO.Create(project.getTitle(), project.getAuthor(), project.getDescription(), project.getContent()));
        ProjectDTO.Response savedDTO2 = projectService.saveProject(new ProjectDTO.Create(projec2.getTitle(), projec2.getAuthor(), projec2.getDescription(), projec2.getContent()));

        ProjectDTO.Response retrievedProjectDTO = projectService.getProjectById(savedDTO1.id()).orElse(null);
        ProjectDTO.Response retrievedProject2DTO = projectService.getProjectById(savedDTO2.id()).orElse(null);

        Project retrievedProject = ProjectMapper.toEntity(retrievedProjectDTO);
        Project retrievedProject2 = ProjectMapper.toEntity(retrievedProject2DTO);

        assertNotNull(retrievedProject, "El projecte recuperat no hauria de ser nul.");
        assertEquals("Test Project", retrievedProject.getTitle(), "El títol del projecte recuperat no coincideix.");
        assertEquals("Test Author", retrievedProject.getAuthor(), "L'autor del projecte recuperat no coincideix.");
        assertEquals("Test Description", retrievedProject.getDescription(), "El contingut del projecte recuperat no coincideix.");

        assertNotNull(retrievedProject2, "El projecte recuperat no hauria de ser nul.");
        assertEquals("Test Project2", retrievedProject2.getTitle(), "El títol del projecte recuperat no coincideix.");
        assertEquals("Test Author2", retrievedProject2.getAuthor(), "L'autor del projecte recuperat no coincideix.");
        assertEquals("Test Description2", retrievedProject2.getDescription(), "El contingut del projecte recuperat no coincideix.");

    }



    @Test
    void deleteProject() {System.out.println(ANSI_PURPLE + "Test: Delete projects by ID " + ANSI_RESET);


        Project project = new Project("Test Project", "Test Author", "Test Description");
        project.setDescription("Test Description");
        Project project2 = new Project("Test Project2", "Test Author2", "Test Description2");
        project2.setDescription("Test Description2");

        ProjectDTO.Response savedDTO1 = projectService.saveProject(new ProjectDTO.Create(project.getTitle(), project.getAuthor(), project.getDescription(), project.getContent()));
        ProjectDTO.Response savedDTO2 = projectService.saveProject(new ProjectDTO.Create(project2.getTitle(), project2.getAuthor(), project2.getDescription(), project2.getContent()));

        ProjectDTO.Response retrievedProjectDTO = projectService.getProjectById(savedDTO1.id()).orElse(null);
        ProjectDTO.Response retrievedProject2DTO = projectService.getProjectById(savedDTO2.id()).orElse(null);

        Project retrievedProject = ProjectMapper.toEntity(retrievedProjectDTO);
        Project retrievedProject2 = ProjectMapper.toEntity(retrievedProject2DTO);

        assertNotNull(retrievedProject, "El projecte recuperat no hauria de ser nul.");
        assertEquals("Test Project", retrievedProject.getTitle(), "El títol del projecte recuperat no coincideix.");
        assertEquals("Test Author", retrievedProject.getAuthor(), "L'autor del projecte recuperat no coincideix.");

        assertTrue(projectService.deleteProject(retrievedProject.getId()));
        if(retrievedProject2 != null) {
            assertTrue(projectService.deleteProject(retrievedProject2.getId()));
        }

        assertTrue(projectService.getProjectById(retrievedProject.getId()).isEmpty());
        if(retrievedProject2 != null) {
            assertTrue(projectService.getProjectById(retrievedProject2.getId()).isEmpty());
        }

    }

    @Test
    void updateProject() {
        System.out.println(ANSI_PURPLE + "Test: Update projects by ID " + ANSI_RESET);

        Project project = new Project("Test Project", "Test Author", "Test Description");
        project.setDescription("Test Description");
        Project project2 = new Project("Test Project2", "Test Author2", "Test Description2");
        project2.setDescription("Test Description2");


        ProjectDTO.Response savedDTO = projectService.saveProject(new ProjectDTO.Create(project.getTitle(), project.getAuthor(), project.getDescription(), project.getContent()));

        ProjectDTO.Response retrievedProjectDTO = projectService.getProjectById(savedDTO.id()).orElse(null);
        Project retrievedProject = ProjectMapper.toEntity(retrievedProjectDTO);

        if(retrievedProject != null) {
            projectService.updateProject(retrievedProject.getId(), new ProjectDTO.Update(savedDTO.id(), project2.getTitle(), project2.getAuthor(), project2.getDescription(), project2.getContent()));
        }

        ProjectDTO.Response updatedProjectDTO = projectService.getProjectById(savedDTO.id()).orElse(null);
        Project updatedProject = ProjectMapper.toEntity(updatedProjectDTO);

        assertNotNull(updatedProject, "El projecte recuperat no hauria de ser nul.");
        assertEquals("Test Project2", updatedProject.getTitle(), "El títol del projecte recuperat no coincideix.");
        assertEquals("Test Author2", updatedProject.getAuthor(), "L'autor del projecte recuperat no coincideix.");
        assertEquals("Test Description2", updatedProject.getDescription(), "El contingut del projecte recuperat no coincideix.");
    }
}