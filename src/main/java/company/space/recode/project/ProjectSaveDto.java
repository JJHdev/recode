package company.space.recode.project;

import company.space.recode.file.File;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ProjectSaveDto {
    private List<Project> projectList;
    private List<File> fileList; // 기존 파일 정보
}
