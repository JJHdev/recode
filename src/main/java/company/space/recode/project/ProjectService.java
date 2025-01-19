package company.space.recode.project;

import groovy.util.logging.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService (ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> findProjectByRegiId(String regiId) {
        return projectRepository.findByRegiId(regiId , Sort.by(Sort.Direction.ASC,"seqCode"));
    }

}
