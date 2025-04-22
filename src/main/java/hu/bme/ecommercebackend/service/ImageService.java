package hu.bme.ecommercebackend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class ImageService {

    public ImageService() {
        //Todo:kitalálni hogy mizu legyen vele
       // resetFiles();
    }

    public String saveImage(MultipartFile image) {
        if(image != null) {
            try {
                String uplodDir = "files/";
                Files.createDirectories(Paths.get(uplodDir));

                String fileName = UUID.randomUUID() + "-" + image.getOriginalFilename();
                Path filePath = Paths.get(uplodDir + fileName);
                Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                return "files/" + fileName;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return "";
    }

    public void deleteImage(String url) {
        if(url != null) {
            try {
                Path filePath = Paths.get("files", url);
                Files.deleteIfExists(filePath);
            } catch (Exception e) {
                throw new RuntimeException("Delete failed: " + url, e);
            }
        }
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
