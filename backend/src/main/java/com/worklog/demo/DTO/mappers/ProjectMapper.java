package com.worklog.demo.DTO.mappers;

import com.worklog.demo.DTO.DTOs.ProjectDTO;
import com.worklog.demo.Domain.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {

    public static ProjectDTO toDTO(Project project) {
        return new ProjectDTO(
                project.getId(),
                project.getTitle(),
                project.getAuthor(),
                project.getDescription(),
                project.getContent(),
                project.getCreatedAt()
        );
    }
    public Project toEntity(ProjectDTO dto){
        if(dto == null) return null;
        Project project = new Project();
        project.setTitle(dto.title());
        project.setAuthor(dto.author());
        project.setDescription(dto.description());
        project.setContent(dto.content());
        return project;
    }
}
