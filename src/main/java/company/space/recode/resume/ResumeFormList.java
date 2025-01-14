package company.space.recode.resume;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ResumeFormList {

    private List<Experience> experiencesList;
    private List<Education> educationList;
    private List<Skill> skillList;
    private List<Languages> languagesList;

    private List<String> delExperiencesCode;
    private List<String> delEducationCode;
    private List<String> delSkillCode;
    private List<String> delLanguagesCode;

}
