package company.space.recode.resume;

import company.space.recode.user.User;
import company.space.recode.user.UserSaveForm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class ResumeController {

    private ResumeService resumeService;

    @Autowired
    public ResumeController(ResumeService resumeService){
        this.resumeService = resumeService;
    }

    @GetMapping("/viewResume.do")
    public String goResume(Model model, HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof User) {
                User user = (User) principal;
                String userId = user.getUserId();

                // 각 리스트를 서비스에서 가져와 모델에 추가
                List<Experience> experiencesList = resumeService.findExperiencesByRegiId(userId);
                List<Education> educationList = resumeService.findEducationsByRegiId(userId);
                List<Skill> skillList = resumeService.findSkillsByRegiId(userId);
                List<Languages> languagesList = resumeService.findLanguagesByRegiId(userId);

                model.addAttribute("experiencesList", experiencesList);
                model.addAttribute("educationList", educationList);
                model.addAttribute("skillList", skillList);
                model.addAttribute("languagesList", languagesList);
            }
        }

        return "resume/viewResume";
    }

    @GetMapping("/regiResume.do")
    public String regiResume(Model model, @ModelAttribute ResumeFormList resumeFormList, HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof User) {

                User userObject = (User) principal;
                String userId = userObject.getUserId();
                List<Experience> experiencesList  = resumeService.findExperiencesByRegiId(userId);
                List<Education> educationList  = resumeService.findEducationsByRegiId(userId);
                List<Skill> skillList  = resumeService.findSkillsByRegiId(userId);
                List<Languages> languagesList  = resumeService.findLanguagesByRegiId(userId);

                resumeFormList.setExperiencesList(experiencesList);
                resumeFormList.setEducationList(educationList);
                resumeFormList.setSkillList(skillList);
                resumeFormList.setLanguagesList(languagesList);
            }
        }

        //List<ResumeSaveForm> saveForms = List.of(new ResumeSaveForm(), new ResumeSaveForm());
        //resumeFormList.setResumeSaveForms(saveForms);
        model.addAttribute("resumeFormList", resumeFormList);
        return "resume/regiResume";
    }

    @PostMapping("/regiResume.do")
    public String registerResume(@ModelAttribute ResumeFormList resumeFormList) {
        // resumeFormList 객체는 클라이언트로부터 전달된 데이터를 바인딩
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        User userObject = (User) principal;
        String userId = userObject.getUserId();

        if (resumeFormList.getExperiencesList() != null) {
            for (Experience experience : resumeFormList.getExperiencesList()) {
                experience.setRegiId(userId);
                resumeService.experienceRegi(experience);
            }
        }
        if (resumeFormList.getEducationList() != null) {
            for (Education education : resumeFormList.getEducationList()) {
                education.setRegiId(userId);
                resumeService.educationRegi(education);
            }
        }
        if (resumeFormList.getSkillList() != null) {
            for (Skill skill : resumeFormList.getSkillList()) {
                skill.setRegiId(userId);
                resumeService.skillRegi(skill);
            }
        }
        if (resumeFormList.getLanguagesList() != null) {
            for (Languages languages : resumeFormList.getLanguagesList()) {
                languages.setRegiId(userId);
                resumeService.languagesRegi(languages);
            }
        }

        // 경험 삭제 처리
        if (resumeFormList.getDelExperiencesCode() != null) {
            for (String seqCode : resumeFormList.getDelExperiencesCode()) {
                Experience experience = new Experience();
                try {
                    Long seqCodeLong = Long.valueOf(seqCode); // String -> Long 변환
                    experience.setSeqCode(seqCodeLong);
                } catch (NumberFormatException e) {
                    // seqCode가 숫자가 아닐 경우 처리
                    throw new IllegalArgumentException("Invalid seqCode: " + seqCode, e);
                }
                resumeService.deleteExperience(experience);
            }
        }

        // 교육 삭제 처리
        if (resumeFormList.getDelEducationCode() != null) {
            for (String seqCode : resumeFormList.getDelEducationCode()) {
                Education education = new Education();
                try {
                    Long seqCodeLong = Long.valueOf(seqCode); // String -> Long 변환
                    education.setSeqCode(seqCodeLong);
                } catch (NumberFormatException e) {
                    // seqCode가 숫자가 아닐 경우 처리
                    throw new IllegalArgumentException("Invalid seqCode: " + seqCode, e);
                }
                resumeService.deleteEducation(education);
            }
        }

        // 스킬 삭제 처리
        if (resumeFormList.getDelSkillCode() != null) {
            for (String seqCode : resumeFormList.getDelSkillCode()) {
                Skill skill = new Skill();
                try {
                    Long seqCodeLong = Long.valueOf(seqCode); // String -> Long 변환
                    skill.setSeqCode(seqCodeLong);
                } catch (NumberFormatException e) {
                    // seqCode가 숫자가 아닐 경우 처리
                    throw new IllegalArgumentException("Invalid seqCode: " + seqCode, e);
                }
                resumeService.deleteSkill(skill);
            }
        }

        // 언어 삭제 처리
        if (resumeFormList.getDelLanguagesCode() != null) {
            for (String seqCode : resumeFormList.getDelLanguagesCode()) {
                Languages languages = new Languages();
                try {
                    Long seqCodeLong = Long.valueOf(seqCode); // String -> Long 변환
                    languages.setSeqCode(seqCodeLong);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid seqCode: " + seqCode, e);
                }
                resumeService.deleteLanguages(languages);
            }
        }
        return "redirect:/regiResume.do"; // 처리 후 리다이렉트
    }
}
