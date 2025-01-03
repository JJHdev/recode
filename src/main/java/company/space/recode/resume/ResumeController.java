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
        ResumeFormList resumeFormList = new ResumeFormList();
        List<ResumeSaveForm> resumeSaveForms = new ArrayList<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof User) {
                User userObject = (User) principal;
                String userId = userObject.getUserId();
                List<Resume> resumeList  = resumeService.findByRegiId(userId);
                for(Resume resume : resumeList){
                    resumeSaveForms.add(resumeToResumeSaveForm(resume));
                }
                resumeFormList.setResumeSaveForms(resumeSaveForms);
            }
        }
        //List<ResumeSaveForm> saveForms = List.of(new ResumeSaveForm(), new ResumeSaveForm());
        //resumeFormList.setResumeSaveForms(saveForms);
        model.addAttribute("resumeFormList", resumeFormList);
        return "resume/viewResume";
    }

    @GetMapping("/regiResume.do")
    public String regiResume(Model model, HttpServletRequest request) {
        ResumeFormList resumeFormList = new ResumeFormList();
        List<ResumeSaveForm> resumeSaveForms = new ArrayList<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof User) {
                User userObject = (User) principal;
                String userId = userObject.getUserId();
                List<Resume> resumeList  = resumeService.findByRegiId(userId);

                boolean hasExperience = resumeList.stream().anyMatch(resume -> "Experience".equals(resume.getResumeGrbun()));
                boolean hasEducation = resumeList.stream().anyMatch(resume -> "Education".equals(resume.getResumeGrbun()));
                boolean hasSkills = resumeList.stream().anyMatch(resume -> "Skill".equals(resume.getResumeGrbun()));
                boolean hasLanguages =resumeList.stream().anyMatch(resume -> "Languages".equals(resume.getResumeGrbun()));

                if (!hasExperience) {
                    Resume emptyExperience = new Resume();
                    emptyExperience.setResumeGrbun("Experience");
                    resumeList.add(emptyExperience);
                }

                if (!hasEducation) {
                    Resume emptyEducation = new Resume();
                    emptyEducation.setResumeGrbun("Education");
                    resumeList.add(emptyEducation);
                }

                if (!hasSkills) {
                    Resume emptySkills = new Resume();
                    emptySkills.setResumeGrbun("Skill");
                    resumeList.add(emptySkills);
                }

                if (!hasLanguages) {
                    Resume emptyLanguages = new Resume();
                    emptyLanguages.setResumeGrbun("Languages");
                    resumeList.add(emptyLanguages);
                }

                for(Resume resume : resumeList){
                    resumeSaveForms.add(resumeToResumeSaveForm(resume));
                }
                resumeFormList.setResumeSaveForms(resumeSaveForms);
            }
        }
        //List<ResumeSaveForm> saveForms = List.of(new ResumeSaveForm(), new ResumeSaveForm());
        //resumeFormList.setResumeSaveForms(saveForms);
        model.addAttribute("resumeFormList", resumeFormList);
        return "resume/regiResume";
    }

    @PostMapping("/regiResume.do")
    public String insertResume(Model model, HttpServletRequest request) {
        model.addAttribute("resume", new ResumeSaveForm());
        return "resume/regiResume";
    }

    public ResumeSaveForm resumeToResumeSaveForm(Resume resume) {
        ResumeSaveForm saveForm = new ResumeSaveForm();
        saveForm.setSeqCode(resume.getSeqCode());
        saveForm.setResumeGrbun(resume.getResumeGrbun());
        saveForm.setTitle(resume.getTitle());
        saveForm.setSubContent(resume.getSubContent());
        saveForm.setContent(resume.getContent());
        saveForm.setStartDate(resume.getStartDate());
        saveForm.setEndDate(resume.getEndDate());
        saveForm.setRegiId(resume.getRegiId());
        saveForm.setRegiDate(resume.getRegiDate());
        saveForm.setUpdtId(resume.getUpdtId());
        saveForm.setUpdtDate(resume.getUpdtDate());
        return saveForm;
    }
}
