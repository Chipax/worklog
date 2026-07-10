package com.worklog.demo;

import com.worklog.demo.Domain.Project;
import com.worklog.demo.persistence.ProjectRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


// Esta clase es una solucion provisional para hacer testing de mi API, solucion rapida que seguramente no es escalable

@Component
@Profile("!test") // <-- SÚPER IMPORTANTE: Toda esta clase se ignorará si el perfil es 'test'
public class DataLoader {

    private final ProjectRepository projectRepository;

    public DataLoader(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @PostConstruct
    public void cargarDatosPredeterminados() {
        if (projectRepository.count() == 0) {
            projectRepository.save(new Project("Worklog Application", "Jan Manté", "API REST con Spring Boot 4."));
            projectRepository.save(new Project("E-Commerce Platform", "Jan Manté", "Tienda online simulada."));
            projectRepository.save(new Project("Portfolio Web", "Jan Manté", "Sitio web personal."));

            System.out.println("\u001B[32m✔ ¡Base de datos de desarrollo completada! (Ignorada en tests)\u001B[0m");
        }
    }
}