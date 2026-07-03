package com.worklog.demo.persistence;

import com.worklog.demo.Domain.Projecte;
import org.springframework.data.jpa.repository.JpaRepository;


public interface projecteRepository extends JpaRepository<Projecte,Long> {
}
