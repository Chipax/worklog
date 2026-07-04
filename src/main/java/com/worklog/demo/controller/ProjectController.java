package com.worklog.demo.controller;

import com.worklog.demo.Domain.Project;
import com.worklog.demo.services.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }


    @GetMapping
    public List<Project> findAll(){
        return projectService.getAllProjects();
    }

    //Response entity perque aixi et dona mes informacio al retornar el objecte.

    @GetMapping("/{id}")
    public ResponseEntity<Project> findById(@PathVariable Long id){
        return projectService.getProjectById(id)
                .map(project -> ResponseEntity.ok().body(project))
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public Project createProduct(@RequestBody Project project) {
        return projectService.saveProject(project);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project updated){
        return projectService.updateProject(id,updated)
                .map(productUpdated -> ResponseEntity.ok().body(productUpdated))
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
