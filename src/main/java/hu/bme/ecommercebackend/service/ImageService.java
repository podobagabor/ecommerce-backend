package hu.bme.ecommercebackend.service;

import hu.bme.ecommercebackend.model.Image;
import hu.bme.ecommercebackend.repository.ImageRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
        resetFiles();
    }

    public Image saveImage(MultipartFile image) {
        try {
            String uplodDir = "files/";
            Files.createDirectories(Paths.get(uplodDir));

            String fileName = UUID.randomUUID() + "-" + image.getOriginalFilename();
            Path filePath = Paths.get(uplodDir + fileName);
            Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            String url = "files/" + fileName;
            return imageRepository.save(new Image(url));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Image getImage(Long id) {
        return imageRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Unknown entity."));
    }

    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    public String deleteImage(Long id) {
        Image imageEntity = imageRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Unknown entity"));
        imageRepository.deleteById(id);
        try {
            Path filePath = Paths.get("files", imageEntity.getImageUrl());
            Files.deleteIfExists(filePath);
        } catch (Exception e) {
            throw new RuntimeException("Delete failed: " + imageEntity.getImageUrl(), e);
        }
        return "Success";
    }

    public String deleteImageFromStorage(String url) {
        try {
            Path filePath = Paths.get(url);
            Files.deleteIfExists(filePath);
        } catch (Exception e) {
            throw new RuntimeException("Delete failed: " + url, e);
        }
        return "Success";
    }

    public void resetFiles() {
        Path folderPath = Paths.get("files");

        try (Stream<Path> files = Files.list(folderPath)) {
            files.forEach(file -> {
                try {
                    Files.delete(file);
                } catch (IOException e) {
                    throw new RuntimeException("Delete failed: " + file.toString(), e);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException("Delete failed: " + folderPath, e);
        }
    }

}
