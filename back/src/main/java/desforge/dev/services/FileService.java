package desforge.dev.services;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileService implements IImageService {
    @Value("${image.upload.path}")
    private String imageDir;

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(Paths.get(imageDir));
        } catch (IOException e) {
            throw new RuntimeException("Impossible de créer le dossier d'upload", e);
        }
    }

    @Override
    public String storeFile(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Le fichier est vide");
            }

            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null) {
                originalFilename = "fichier_" + System.currentTimeMillis();
            }

            String fileName = System.currentTimeMillis() + "_" + originalFilename;
            Path dirPath = Paths.get(imageDir);

            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }

            Path filePath = dirPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return fileName;

        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la sauvegarde du fichier", e);
        }
    }


}
