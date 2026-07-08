package com.worklog.demo.services;

import com.worklog.demo.DTO.DTOs.ProjectDTO;
import com.worklog.demo.DTO.mappers.ProjectMapper;
import com.worklog.demo.Domain.Project;
import com.worklog.demo.controller.ProjectController;
import com.worklog.demo.persistence.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private ProjectController projectsRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<ProjectDTO> getAllProjects() {


        return projectRepository.findAll().stream()
                .map(ProjectMapper::toDTO)
                .toList();
    }

    public Optional<ProjectDTO> getProjectById(Long id) {
        return projectRepository.findById(id).map(ProjectMapper::toDTO);
    }
    public Project saveProject(Project projectDTO) {
        if(projectDTO.getTitle()==null) {
            throw new IllegalArgumentException("No té titol");
        }
        if(projectDTO.getAuthor()==null) {
            throw new IllegalArgumentException("No té autor");
        }
        if(projectDTO.getContent()==null) {
            throw new IllegalArgumentException("No té contingut");
        }
        return projectRepository.save(project);
    }

    public boolean deleteProject(Long id) {
        return projectRepository.findById(id)
                .map(project -> {
                    projectRepository.delete(project);
                    return true;
                })
                .orElse(false);
    }
    public Optional<Project> updateProject(Long id, Project updatedProject) {
        return projectRepository.findById(id)
                .map(existingProject -> {
                    existingProject.setTitle(updatedProject.getTitle());
                    existingProject.setAuthor(updatedProject.getAuthor());
                    existingProject.setContent(updatedProject.getContent());
                    return projectRepository.save(existingProject);
                });
    }
}
