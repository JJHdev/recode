package company.space.recode.main;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;


@Slf4j
@Controller
public class MainController {
    @GetMapping("/")
    public String homePage(@ModelAttribute("accessToken") String accessToken, @ModelAttribute("refreshToken") String refreshToken, Model model) {
        // FlashAttribute로 전달된 토큰 확인
        if (accessToken != null) {
            model.addAttribute("accessToken", accessToken);
            model.addAttribute("refreshToken", refreshToken);
        }
        return "main/main";
    }
}
