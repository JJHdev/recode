package company.space.recode.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class FileConponent {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public Path getUploadPath() {
        return Paths.get(uploadDir);
    }

    public Path getUploadFullPath(String externalPath, String uploaderName) {
        Path basePath = Paths.get(uploadDir);
        return basePath.resolve(externalPath).resolve(uploaderName);
    }

    public void saveFile(MultipartFile file, Path saveFilePath, String saveFileName) throws IOException {
        try {
            if (!saveFilePath.toFile().exists()) {
                Files.createDirectories(saveFilePath);
            }
            Path filePath = saveFilePath.resolve(saveFileName);
            file.transferTo(filePath.toFile());
            System.out.println("File saved to: " + filePath);
        } catch (IOException e) {
            throw new RuntimeException("Could not store file. Error: " + e.getMessage());
        }
    }

    public String getSaveFileName(MultipartFile file) {

        String originalFileName = file.getOriginalFilename();
        String fileExtension = "";

        if (originalFileName != null && originalFileName.contains(".")) {
            fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }

        String uniqueFileName = UUID.randomUUID().toString();

        return uniqueFileName + fileExtension;
    }
}
