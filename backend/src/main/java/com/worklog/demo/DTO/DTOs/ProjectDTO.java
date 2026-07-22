package com.worklog.demo.DTO.DTOs;

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
            String imageUrl,
            String content
    ){}

    public record Response(
            Long id,
            String title,
            String author,
            String description,
            String imageUrl,
            String content,
            Instant createdAt
    ){}
}
