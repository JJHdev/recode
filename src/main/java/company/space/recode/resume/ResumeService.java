package company.space.recode.resume;

import company.space.recode.user.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@Service
public class ResumeService {

    private final ResumeExperienceRepository resumeExperienceRepository;
    private final ResumeEducationRepository resumeEducationRepository;
    private final ResumeSkillRepository resumeSkillRepository;
    private final ResumeLanguagesRepository resumeLanguagesRepository;

    @Autowired
    public ResumeService(ResumeExperienceRepository resumeExperienceRepository, ResumeEducationRepository resumeEducationRepository
                        ,ResumeSkillRepository resumeSkillRepository, ResumeLanguagesRepository resumeLanguagesRepository) {
        this.resumeExperienceRepository = resumeExperienceRepository;
        this.resumeEducationRepository = resumeEducationRepository;
        this.resumeSkillRepository = resumeSkillRepository;
        this.resumeLanguagesRepository = resumeLanguagesRepository;
    }

    public List<Experience> findExperiencesByRegiId (String regiId){
        return resumeExperienceRepository.findByRegiId(regiId);
    }
    public List<Education> findEducationsByRegiId (String regiId){
        return resumeEducationRepository.findByRegiId(regiId);
    }
    public List<Skill> findSkillsByRegiId (String regiId){
        return resumeSkillRepository.findByRegiId(regiId);
    }
    public List<Languages> findLanguagesByRegiId (String regiId){
        return resumeLanguagesRepository.findByRegiId(regiId);
    }

}
