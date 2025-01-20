package company.space.recode.project;

import company.space.recode.component.sequence.SequenceGeneratorService;
import groovy.util.logging.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final SequenceGeneratorService sequenceGeneratiorService;

    @Autowired
    public ProjectService (ProjectRepository projectRepository, SequenceGeneratorService sequenceGeneratiorService) {
        this.projectRepository = projectRepository;
        this.sequenceGeneratiorService = sequenceGeneratiorService;
    }

    public List<Project> findProjectByRegiId(String regiId) {
        return projectRepository.findByRegiId(regiId , Sort.by(Sort.Direction.ASC,"seqCode"));
    }

    public Project saveProject(Project project) {
        if (project.getFileNo() == null) {
            String fileNo = sequenceGeneratiorService.getNextSequenceValue("file_external_key_seq");
            project.setFileNo(fileNo);
        }
        return projectRepository.save(project);
    }

}
