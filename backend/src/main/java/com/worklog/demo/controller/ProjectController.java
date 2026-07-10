package com.worklog.demo.controller;

import com.worklog.demo.DTO.DTOs.ProjectDTO;
import com.worklog.demo.services.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.worklog.demo.DTO.mappers.ProjectMapper.toDTO;

@RestController
@RequestMapping("api/project")
@CrossOrigin(origins = "*") // <--- ESTA LÍNEA SOLUCIONA EL CORS
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }


    @GetMapping
    public List<ProjectDTO.Response> findAll(){
        return projectService.getAllProjects();
    }

    //Response entity perque aixi et dona mes informacio al retornar el objecte.

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO.Response> findById(@PathVariable Long id){
        return projectService.getProjectById(id)
                .map(project -> ResponseEntity.ok().body(project))
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ProjectDTO.Response createProduct(@RequestBody ProjectDTO.Create projectDTO) {
        return projectService.saveProject(projectDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectDTO.Response> updateProject(@PathVariable Long id, @RequestBody ProjectDTO.Update updated){
        return projectService.updateProject(id, updated)
                .map(productUpdated -> ResponseEntity.ok().body(toDTO(productUpdated)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id){
        if(projectService.deleteProject(id)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
