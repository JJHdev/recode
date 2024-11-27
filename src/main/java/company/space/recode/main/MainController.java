package company.space.recode.main;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;


@Slf4j
@Controller
public class MainController {
    @GetMapping("/")
    public String homePage(@ModelAttribute("accessToken") String accessToken, Model model,  HttpServletRequest request) {
        // FlashAttribute로 전달된 토큰 확인
        if (accessToken != null) {
            model.addAttribute("accessToken", accessToken);
        }
        return "index";
    }

    @GetMapping("/nextPage")
    public String homePage1(@ModelAttribute("accessToken") String accessToken, Model model,  HttpServletRequest request) {
        // FlashAttribute로 전달된 토큰 확인

        return "index1";
    }

}
