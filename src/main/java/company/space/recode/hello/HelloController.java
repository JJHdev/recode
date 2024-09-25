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
        Email email = new Email();
        email.setEmail("goodjob321@hanmail.net");
        email.setEmailStatus(true);
        helloService.sayHello(email);
        return "hello";
    }
}
