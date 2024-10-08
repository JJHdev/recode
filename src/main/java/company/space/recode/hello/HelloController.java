package company.space.recode.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private final HelloService helloService;

    @Autowired
    public HelloController (HelloService helloService) {
        this.helloService = helloService;
    }
    @GetMapping("/")
    public String hello() {
        User user = new User();
        user.setUserId("admin");
        user.setUserName("이름입력");
        user.setPassword("123456");
        user.setGender("M");
        user.setEmail("goodjob321@hanmail.net");
        user.setStatus("ACTIVE");
        helloService.sayHello(user);
        return "hello";
    }
}
