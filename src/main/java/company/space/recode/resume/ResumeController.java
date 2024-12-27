package company.space.recode.resume;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class ResumeController {
    @GetMapping("/viewResume.do")
    public String goResume(Model model, HttpServletRequest request) {
        // FlashAttribute로 전달된 토큰 확인
        return "resume/resume";
    }
    @GetMapping("/regiResume.do")
    public String regiResume(Model model, HttpServletRequest request) {
        // FlashAttribute로 전달된 토큰 확인
        return "resume/resume";
    }
}
