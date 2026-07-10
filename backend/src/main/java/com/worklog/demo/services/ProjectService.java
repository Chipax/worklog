package com.worklog.demo.services;

import com.worklog.demo.DTO.DTOs.ProjectDTO;
import com.worklog.demo.DTO.mappers.ProjectMapper;
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

    public List<ProjectDTO.Response> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(ProjectMapper::toDTO)
                .toList();
    }

    public Optional<ProjectDTO.Response> getProjectById(Long id) {
        return projectRepository.findById(id).map(ProjectMapper::toDTO);
    }

    //Guarda un DTO de creació del projecte, té contingut buit perque s'anyadeix despres.
    public ProjectDTO.Response saveProject(ProjectDTO.Create dto) {

        if(dto.title()==null) {
            throw new IllegalArgumentException("No té titol");
        }
        if(dto.author()==null) {
            throw new IllegalArgumentException("No té autor");
        }
        if(dto.description()==null) {
            throw new IllegalArgumentException("No té contingut");
        }
        Project project = ProjectMapper.toEntity(dto);

        Project savedProject = projectRepository.save(project);

        return ProjectMapper.toDTO(savedProject);
    }

    public boolean deleteProject(Long id) {
        return projectRepository.findById(id)
                .map(project -> {
                    projectRepository.delete(project);
                    return true;
                })
                .orElse(false);
    }
    public Optional<Project> updateProject(Long id, ProjectDTO.Update updatedProject) {
        return projectRepository.findById(id)
                .map(existingProject -> {
                    ProjectMapper.updateEntityFromDto(existingProject, updatedProject);

                    return projectRepository.save(existingProject);
                });
    }
}

