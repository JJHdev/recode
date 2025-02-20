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
    public String homePage(Model model,  HttpServletRequest request) {
        return "main/index";
    }
}
