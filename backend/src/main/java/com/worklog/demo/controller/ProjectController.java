package com.worklog.demo.controller;

import com.worklog.demo.DTO.DTOs.ProjectDTO;
import com.worklog.demo.services.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<List<ProjectDTO.Response>> findAll(){
        List<ProjectDTO.Response> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects);
    }

    //Response entity perque aixi et dona mes informacio al retornar el objecte.

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO.Response> findById(@PathVariable Long id){
        return projectService.getProjectById(id)
                .map(project -> ResponseEntity.ok().body(project))
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<ProjectDTO.Response> createProduct(@RequestBody ProjectDTO.Create projectDTO) {
        ProjectDTO.Response savedProject = projectService.saveProject(projectDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProject);
    }

    @PostMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProjectDTO.Response> uploadImage(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {

        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return projectService.addImageToProject(id, file)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
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
