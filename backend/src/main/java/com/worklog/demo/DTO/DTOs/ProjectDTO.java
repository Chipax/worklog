package com.worklog.demo.DTO.DTOs;

import com.worklog.demo.Domain.Project;

import java.time.Instant;

public class  ProjectDTO {

    public record Create(
            String title,
            String author,
            String description,
            String content
    ){}

    public record Update(
            Long id,
            String title,
            String author,
            String description,
            String content
    ){}

    public record Response(
            Long id,
            String title,
            String author,
            String description,
            String content,
            Instant createdAt
    ){}
}
