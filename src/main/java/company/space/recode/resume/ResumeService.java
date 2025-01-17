package company.space.recode.resume;

import company.space.recode.user.User;
import company.space.recode.user.UserSaveForm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
        return resumeExperienceRepository.findByRegiId(regiId, Sort.by(Sort.Direction.ASC, "seqCode"));
    }
    public List<Education> findEducationsByRegiId (String regiId){
        return resumeEducationRepository.findByRegiId(regiId, Sort.by(Sort.Direction.ASC, "seqCode"));
    }
    public List<Skill> findSkillsByRegiId (String regiId){
        return resumeSkillRepository.findByRegiId(regiId, Sort.by(Sort.Direction.ASC, "seqCode"));
    }
    public List<Languages> findLanguagesByRegiId (String regiId){
        return resumeLanguagesRepository.findByRegiId(regiId, Sort.by(Sort.Direction.ASC, "seqCode"));
    }

    public Experience experienceRegi(Experience experience){
        return resumeExperienceRepository.save(experience);
    }
    public Education educationRegi(Education education){
        return resumeEducationRepository.save(education);
    }
    public Skill skillRegi(Skill skill){
        return resumeSkillRepository.save(skill);
    }
    public Languages languagesRegi(Languages languages){
        return resumeLanguagesRepository.save(languages);
    }

    public void deleteExperience(Experience seqCode) {
        resumeExperienceRepository.delete(seqCode);
    }

    public void deleteEducation(Education seqCode) {
        resumeEducationRepository.delete(seqCode);
    }

    public void deleteSkill(Skill seqCode) {
        resumeSkillRepository.delete(seqCode);
    }

    public void deleteLanguages(Languages seqCode) {
        resumeLanguagesRepository.delete(seqCode);
    }

}
