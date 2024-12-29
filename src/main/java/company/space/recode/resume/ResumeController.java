package company.space.recode.resume;

import company.space.recode.user.UserSaveForm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class ResumeController {
    @GetMapping("/viewResume.do")
    public String goResume(Model model, HttpServletRequest request) {
        return "resume/viewResume";
    }
    @GetMapping("/regiResume.do")
    public String regiResume(Model model, HttpServletRequest request) {
        model.addAttribute("resume", new ResumeSaveForm());
        return "resume/regiResume";
    }
    @PostMapping("/regiResume.do")
    public String insertResume(Model model, HttpServletRequest request) {
        model.addAttribute("resume", new ResumeSaveForm());
        return "resume/regiResume";
    }
}
