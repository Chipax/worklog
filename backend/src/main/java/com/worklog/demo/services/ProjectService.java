package com.worklog.demo.services;

import com.worklog.demo.DTO.DTOs.ProjectDTO;
import com.worklog.demo.DTO.mappers.ProjectMapper;
import com.worklog.demo.Domain.Project;
import com.worklog.demo.persistence.ProjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final StorageService storageService; // Tu interfaz de almacenamiento
    @Autowired
    public ProjectService(ProjectRepository projectRepository, StorageService storageService) {
        this.projectRepository = projectRepository;
        this.storageService = storageService;
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
    @Transactional
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
    @Transactional
    public boolean deleteProject(Long id) {
        return projectRepository.findById(id)
                .map(project -> {
                    projectRepository.delete(project);
                    return true;
                })
                .orElse(false);
    }
    @Transactional
    public Optional<Project> updateProject(Long id, ProjectDTO.Update updatedProject) {
        return projectRepository.findById(id)
                .map(existingProject -> {
                    ProjectMapper.updateEntityFromDto(existingProject, updatedProject);

                    return projectRepository.save(existingProject);
                });
    }

    @Transactional
    public Optional<ProjectDTO.Response> addImageToProject(Long id, MultipartFile file) {
        // 1. Validar primero que el proyecto exista en la BD antes de procesar la imagen
        if (!projectRepository.existsById(id)) {
            return Optional.empty();
        }

        // 2. Guardar el archivo físicamente usando tu StorageService (Fuera de la transacción)
        // Esto devuelve solo el nombre único o la ruta relativa web (ej: "uuid_foto.jpg")
        String fileName = storageService.save(file);

        // 3. Generar la URL pública o ruta accesible
        String imageUrl = "/uploads/" + fileName;

        // 4. Actualizar la entidad en la BD en un método separado con @Transactional
        return Optional.of(updateProjectImageUrl(id, imageUrl));
    }
    @Transactional
    protected ProjectDTO.Response updateProjectImageUrl(Long id, String imageUrl) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

        // Asignamos la nueva URL al atributo de la entidad
        project.setImageUrl(imageUrl);

        // Guardamos los cambios y mapeamos la respuesta a DTO
        Project updatedProject = projectRepository.save(project);
        return ProjectMapper.toDTO(updatedProject);
    }

}
