package com.worklog.demo.services;

import com.worklog.demo.Domain.Project;
import com.worklog.demo.persistence.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }
    public Project saveProject(Project project) {
        if(project.getTitol()==null) {
            throw new IllegalArgumentException("No té titol");
        }
        if(project.getAutor()==null) {
            throw new IllegalArgumentException("No té autor");
        }
        if(project.getContingut()==null) {
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
                    existingProject.setTitol(updatedProject.getTitol());
                    existingProject.setAutor(updatedProject.getAutor());
                    existingProject.setContingut(updatedProject.getContingut());
                    return projectRepository.save(existingProject);
                });
    }
}
