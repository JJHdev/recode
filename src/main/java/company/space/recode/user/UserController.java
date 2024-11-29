package company.space.recode.user;

import company.space.recode.component.Utils.ServiceResult;
import company.space.recode.sys.syscode.SysCode;
import company.space.recode.sys.syscode.SysCodeService;
import company.space.recode.token.AuthController;
import company.space.recode.token.JwtResponse;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private final AuthController authController;
    @Value("${jwt.refreshExp}") long REFRESH_TOKEN_TIME;
    @Value("${jwt.accessExp}") long ACCESS_TOKEN_TIME;

    @Autowired
    public UserController(UserService userService, SysCodeService sysCodeService, PasswordEncoder passwordEncoder,AuthController authController) {
        this.sysCodeService = sysCodeService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authController = authController;
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
    public String login(@Validated @ModelAttribute("loginForm") LoginForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return "user/login";
        }
        User user = userService.login(form.getUserId(), form.getPassword());
        if (user.getUserId() == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "user/login";
        }

        ResponseEntity<?> responseToken = authController.authenticateUser(form.getUserId(), form.getPassword());
        if (responseToken.getStatusCode().is2xxSuccessful()) {
            JwtResponse jwtResponse = (JwtResponse) responseToken.getBody();

            createCookie("accessToken",  jwtResponse.getAccessToken() , false , false, (int)ACCESS_TOKEN_TIME, response);
            createCookie("refreshToken", jwtResponse.getRefreshToken(), false , false, (int)REFRESH_TOKEN_TIME, response);
            //createCookie("accessToekn",  jwtResponse.getAccessToken() , false , true, (int)ACCESS_TOKEN_TIME, response);
            //createCookie("refreshToken", jwtResponse.getRefreshToken(), true , true, (int)REFRESH_TOKEN_TIME, response);

            //로그인 성공 처리 TODO
            return "redirect:/";
        } else {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "user/login";
        }
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

    private void createCookie (String cookieName , String jwtToken , Boolean httpOnly, Boolean secure, int cookieTime, HttpServletResponse response){
        Cookie cookie = new Cookie(cookieName, jwtToken);
        cookie.setHttpOnly(httpOnly); // 클라이언트 접근 가능
        cookie.setSecure(secure);   // HTTPS에서만 전송
        cookie.setPath("/");
        cookie.setMaxAge(cookieTime); // 15분
        response.addCookie(cookie);
    }

}
