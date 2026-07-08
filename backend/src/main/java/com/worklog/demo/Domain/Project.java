package com.worklog.demo.Domain;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Entity
@Table(name = "proyectos")
@EntityListeners(AuditingEntityListener.class) // 👈 Escucha eventos de la base de datos
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private String description;

    @JdbcTypeCode(SqlTypes.JSON)
    private String content;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private Instant createdAt;


    public Project() {}

    public Project(String title, String author, String description){
        this.title = title;
        this.author = author;
        this.description = description;
        this.content = null;
    }
    public Project(String title,String author, String description,String content){
        this.title = title;
        this.author = author;
        this.description = description;
        this.content = content;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
