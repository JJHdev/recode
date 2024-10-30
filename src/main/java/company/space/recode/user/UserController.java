package company.space.recode.user;

import company.space.recode.component.Utils.ServiceResult;
import company.space.recode.sys.syscode.SysCode;
import company.space.recode.sys.syscode.SysCodeService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
@Controller
@RequestMapping("user")
public class UserController {

    private final UserService userService;
    private final SysCodeService sysCodeService;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserController(UserService userService, SysCodeService sysCodeService, PasswordEncoder passwordEncoder) {
        this.sysCodeService = sysCodeService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    private ConcurrentHashMap<String,List<SysCode>> resultMap;
    @PostConstruct
    public void init() {
        resultMap = new ConcurrentHashMap<>();
        resultMap.put("emailCodes", sysCodeService.findSysCode("EMAIL_CODE"));
        resultMap.put("sexCodes", sysCodeService.findSysCode("SEX_CODE"));
    }
    @ModelAttribute("sysCodes")
    public ConcurrentHashMap<String,List<SysCode>> sysCodeList() {
        return resultMap;
    }

    @GetMapping("/login")
    public String openLogin(@ModelAttribute("loginForm") LoginForm form) {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute("loginForm") LoginForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "user/login";
        }
        User user = userService.login(form.getUserId(), passwordEncoder.encode(form.getPassword()));
        if (user == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "user/login";
        }
        //로그인 성공 처리 TODO
        return  "redirect:/";
    }

    @GetMapping("/regiUser")
    public String openRegiUser(Model model) {
        model.addAttribute("user", new UserSaveForm());
        return "user/regiUser";
    }

    @PostMapping("/regiUser")
    public String regiUser(@Validated @ModelAttribute("user") UserSaveForm saveForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        if(saveForm.isFullEmailValid()){
            bindingResult.reject("totalEmail", new Object[]{saveForm.getFullEmail()} , null);
        }
        if(!saveForm.isPasswordMatching()){
            bindingResult.reject("MatchPassword", new Object[]{saveForm.getPassword(), saveForm.getPasswordChck()} , null);
        }
        if (bindingResult.hasErrors()) {
            return "user/regiUser";
        }

        saveForm.setPassword(passwordEncoder.encode(saveForm.getPassword()));
        User savedUser = userService.userRegi(saveForm);
        redirectAttributes.addAttribute("itemId", savedUser.getUserKey());
        redirectAttributes.addAttribute("status", true);

        model.addAttribute("user", saveForm);
        return "redirect:/user/regiUser";
    }

    @PostMapping("/checkUser")
    public <T> ResponseEntity openCheckUser(@RequestParam("userId") String userId, Model model) {
        ServiceResult<String> result = userService.checkUser(userId);

        if(result.isSuccess()){
            return ResponseEntity.ok().body(Map.of("success" , true, "message" , userId +"의 아이디는 사용하실수 있습니다."));
        }else{
            String errorMessage = result.getErrorMessage();
            return ResponseEntity.ok().body(Map.of("success", false, "message", userId +"로 가입된 계정이 있습니다."));
        }
    }

}
