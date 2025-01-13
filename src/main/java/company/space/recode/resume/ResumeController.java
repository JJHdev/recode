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
        for (Experience experience : resumeFormList.getExperiencesList()) {
            System.out.println(experience);
        }
        for (Education education : resumeFormList.getEducationList()) {
            System.out.println(education);
        }
        for (Skill skill : resumeFormList.getSkillList()) {
            System.out.println(skill);
        }
        for (Languages languages : resumeFormList.getLanguagesList()) {
            System.out.println(languages);
        }
        return "redirect:/resume"; // 처리 후 리다이렉트
    }

}
