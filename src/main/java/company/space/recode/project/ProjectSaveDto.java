package company.space.recode.project;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ProjectSaveDto {
    private List<Project> projectList;
    private List<MultipartFile> fileList;
}
