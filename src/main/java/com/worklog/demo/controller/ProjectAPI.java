package com.worklog.demo.controller;

import com.worklog.demo.persistence.projecteRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectAPI {

    private final projecteRepository projecteRepository;

    public ProjectAPI(projecteRepository projecteRepository) {
        this.projecteRepository = projecteRepository;
    }
}
