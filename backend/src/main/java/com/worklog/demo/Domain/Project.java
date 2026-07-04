package com.worklog.demo.Domain;

import jakarta.persistence.*;

@Entity
@Table(name = "productos")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titol;
    private String autor;
    private String contingut;

    public Project() {}

    public Project(String titol, String autor, String contingut){
        this.titol = titol;
        this.autor = autor;
        this.contingut = contingut;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getTitol() {
        return titol;
    }
    public void setTitol(String titol) {
        this.titol = titol;
    }
    public String getAutor() {
        return autor;
    }
    public void setAutor(String autor) {
        this.autor = autor;
    }
    public String getContingut() {
        return contingut;
    }
    public void setContingut(String contingut) {
        this.contingut = contingut;
    }
}
