package company.space.recode.user;

import ch.qos.logback.core.model.Model;
import company.space.recode.hello.HelloUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
        HelloUser user = new HelloUser();
        user.setUserId("admin");
        user.setUserName("이름입력");
        user.setPassword("123456");
        user.setGender("M");
        user.setEmail("goodjob321@hanmail.net");
        user.setStatus("ACTIVE");
        //userService.sayHello(user);
        return "user/login";
    }
}
