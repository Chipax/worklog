package com.worklog.demo.DTO.mappers;

import com.worklog.demo.DTO.DTOs.ProjectDTO;
import com.worklog.demo.Domain.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {

    public static ProjectDTO.Response toDTO(Project project) {
        return new ProjectDTO.Response(
                project.getId(),
                project.getTitle(),
                project.getAuthor(),
                project.getDescription(),
                project.getContent(),
                project.getCreatedAt()
        );
    }

    public static Project toEntity(ProjectDTO.Response dto){
        if(dto == null) return null;
        Project project = new Project();
        project.setId(dto.id());
        project.setTitle(dto.title());
        project.setAuthor(dto.author());
        project.setDescription(dto.description());
        project.setContent(dto.content());
        project.setCreatedAt(dto.createdAt());
        return project;
    }
    public static Project toEntity(ProjectDTO.Create dto){
        if(dto == null) return null;
        Project project = new Project();
        project.setTitle(dto.title());
        project.setAuthor(dto.author());
        project.setDescription(dto.description());
        project.setContent(dto.content());
        return project;
    }

    public static void updateEntityFromDto(Project project, ProjectDTO.Update dto) {
        if (project == null || dto == null) return;
        project.setTitle(dto.title());
        project.setAuthor(dto.author());
        project.setDescription(dto.description());
        project.setContent(dto.content());
    }


}
