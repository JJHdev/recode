package company.space.recode.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    private final HelloService helloService;

    @Autowired
    public HelloController (HelloService helloService) {
        this.helloService = helloService;
    }
    @GetMapping("/")
    public String hello() {
        HelloUser user = new HelloUser();
        user.setUserId("admin");
        user.setUserName("이름입력");
        user.setPassword("123456");
        user.setGender("M");
        user.setEmail("goodjob321@hanmail.net");
        user.setStatus("ACTIVE");
        //helloService.sayHello(user);
        return "index";
    }
}
