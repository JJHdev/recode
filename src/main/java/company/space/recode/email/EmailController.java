package company.space.recode.email;

import company.space.recode.component.Utils.ServiceResult;
import company.space.recode.contact.ContactEmailSaveForm;
import company.space.recode.user.User;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    private static final Logger log = LoggerFactory.getLogger(EmailController.class);

    // 인증코드 메일 발송
    @PostMapping("/email/send")
    public ResponseEntity<String> mailSend(EmailDto emailDto) throws MessagingException {
        try{
            emailService.sendEmail(emailDto.getEmail());
            return ResponseEntity.ok("인증코드가 발송되었습니다.");
        }catch (Exception e){
            log.error("Error sending email to {}: {}", emailDto.getEmail(), e.getMessage());
            return ResponseEntity.status(500).body("Error sending email");
        }
    }

    @PostMapping("/email/contactSend")
    public ResponseEntity<String> contactSend(ContactEmailSaveForm contactEmailSaveForm) throws MessagingException {
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Object principal = authentication.getPrincipal();
            User userObject = (User) principal;
            contactEmailSaveForm.setRegiId(userObject.getUserId());

            emailService.sendContactEmail(contactEmailSaveForm);
            return ResponseEntity.ok("문의메일이 정상적으로 발송되었습니다.");
        }catch (Exception e){
            log.error("Error sending email to {}: {}", contactEmailSaveForm.getSendRegiEmail(), e.getMessage());
            return ResponseEntity.status(500).body("Error sending email");
        }
    }

    // 인증코드 인증
    @PostMapping("/email/verify")
    public <T> ResponseEntity verify(EmailDto emailDto) {

        System.out.printf("emailDto: %s\n", emailDto);

        ServiceResult<Email> result = emailService.verifyEmailCode(emailDto);

        if(result.isSuccess()){
            Email registeredEmail = result.getData();
            return ResponseEntity.ok().body(Map.of("success" , true, "redirect" , "/"));
        }else{
            String errorMessage = result.getErrorMessage();
            return ResponseEntity.ok().body(Map.of("success", false, "message", errorMessage));
        }
    }
}
