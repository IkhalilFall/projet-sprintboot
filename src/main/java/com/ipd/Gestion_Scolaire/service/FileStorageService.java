package com.ipd.Gestion_Scolaire.service;

import com.ipd.Gestion_Scolaire.exception.FileStorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${app.upload.dir}")
    private String uploadDir;

    public static final List<String> IMAGE_TYPES = List.of("image/jpeg", "image/png");
    public static final List<String> PDF_TYPES = List.of("application/pdf");

    public String storeFile(MultipartFile file, String subDir, List<String> allowedTypes) {
        validateFile(file, allowedTypes);

        String extension = getExtension(file.getOriginalFilename());
        String filename = UUID.randomUUID() + "." + extension;

        try {
            Path dirPath = Paths.get(uploadDir, subDir);
            Files.createDirectories(dirPath);
            Path target = dirPath.resolve(filename);
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
            return subDir + "/" + filename;
        } catch (IOException e) {
            throw new FileStorageException("Erreur lors de l'enregistrement du fichier : " + e.getMessage());
        }
    }

    private void validateFile(MultipartFile file, List<String> allowedTypes) {
        if (file == null || file.isEmpty()) {
            throw new FileStorageException("Le fichier est vide ou absent");
        }
        String contentType = file.getContentType();
        if (contentType == null || !allowedTypes.contains(contentType)) {
            throw new FileStorageException("Type de fichier non autorisé : " + contentType);
        }
        String filename = file.getOriginalFilename();
        if (filename == null || filename.contains("..")) {
            throw new FileStorageException("Nom de fichier invalide");
        }
    }

    private String getExtension(String filename) {
        int i = filename.lastIndexOf('.');
        return (i == -1) ? "" : filename.substring(i + 1);
    }

    public String getUploadDir() {
        return uploadDir;
    }
}