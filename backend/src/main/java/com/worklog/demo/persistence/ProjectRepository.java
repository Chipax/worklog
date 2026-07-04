package com.worklog.demo.persistence;

import com.worklog.demo.Domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProjectRepository extends JpaRepository<Project,Long> {
}
