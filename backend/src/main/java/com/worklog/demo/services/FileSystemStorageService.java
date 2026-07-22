package com.worklog.demo.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileSystemStorageService implements StorageService {
    private final Path dir = Paths.get("uploads");
    public FileSystemStorageService(){
        try{
            Files.createDirectories(dir);
        }catch(IOException e){
            throw new RuntimeException("No se pudo crear la carpeta de almacenamiento",e);
        }
    }

    @Override
    public String save(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("El archivo no puede estar vacío");
        }

        // 1. Validar que sea realmente una imagen por su Content-Type
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Solo se permiten archivos de imagen");
        }

        try {
            // 2. Extraer y sanitizar el nombre original
            String originalFilename = Paths.get(file.getOriginalFilename()).getFileName().toString();
            String uniqueName = UUID.randomUUID() + "_" + originalFilename;

            // 3. Resolver la ruta absoluta de forma segura
            Path destinationFile = this.dir.resolve(uniqueName).normalize().toAbsolutePath();

            // 4. Protección Path Traversal: asegurar que el archivo destino vive dentro de 'dir'
            if (!destinationFile.getParent().equals(this.dir.toAbsolutePath())) {
                throw new SecurityException("Intento de navegación fuera del directorio permitido");
            }

            // 5. Transferir el archivo
            file.transferTo(destinationFile);

            return uniqueName;

        } catch (IOException e) {
            throw new RuntimeException("Error al guardar la imagen", e);
        }
    }
}
