package com.worklog.demo.DTO.DTOs;

import com.worklog.demo.Domain.Project;

import java.time.Instant;

public record  ProjectDTO (
    Long id,
    String title,
    String author,
    String description,
    String content,
    Instant createdAt
) {

}
