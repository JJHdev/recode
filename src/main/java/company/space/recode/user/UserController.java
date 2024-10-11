package company.space.recode.user;

import ch.qos.logback.core.model.Model;
import company.space.recode.hello.HelloUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String userLogin(Model model) {
        return "user/login";
    }

    @GetMapping("/regiUser")
    public String openRegiUser(Model model) {
        return "user/regiUser";
    }

    @PostMapping("/regiUser")
    public String regiUser(Model model) {
        HelloUser user = new HelloUser();
        user.setUserId("admin");
        user.setUserName("이름입력");
        user.setPassword("123456");
        user.setGender("M");
        user.setEmail("goodjob321@hanmail.net");
        user.setStatus("ACTIVE");
        System.out.printf("123123");
        return "user/regiUser";
    }
}
