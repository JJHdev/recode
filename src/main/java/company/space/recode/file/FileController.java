package company.space.recode.file;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Controller
public class FileController {

    @Value("${file.upload-dir}") // 파일 저장 경로
    private String uploadDir;

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/getImages/{fileNo}")
    public ResponseEntity<Resource> getImage(@PathVariable Long fileNo) {
        try {
            File file = fileService.findFileByseqCode(fileNo);
            if (file == null) {
                System.out.println("No file found for fileNo: " + fileNo);
                return ResponseEntity.notFound().build();
            }
            Path filePath = Paths.get(file.getFilePath());
            if (!Files.exists(filePath)) {
                System.out.println("File does not exist at path: " + filePath);
                return ResponseEntity.notFound().build();
            }
            // 파일 리소스 로드
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .header("Content-Disposition", "inline; filename=\"" + filePath.getFileName().toString() + "\"")
                        .body(resource);
            } else {
                System.out.println("Resource is not readable or does not exist: " + filePath);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

}
