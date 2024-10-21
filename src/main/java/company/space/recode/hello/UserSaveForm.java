package company.space.recode.hello;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserSaveForm {

    @NotBlank
    private String userId;

    @NotBlank
    @Size(max = 50)
    private String userName;

    @NotBlank
    @Size(max = 300)
    private String password;

    @NotBlank
    @Size(max = 300)
    private String passwordChck;

    @NotNull
    @Pattern(regexp = "[MF]")
    private String gender;

    private String status = "ACTIVE";

    @Size(max = 200)
    @NotBlank
    private String email1;

    @Size(max = 200)
    @NotBlank
    private String email2;

    @Size(max = 300)
    private String profilePictureUrl;

    public boolean isFullEmailValid() {
        if (email1 == null || email1.isEmpty() || email2 == null || email2.isEmpty()) {
            return false;
        }
        String fullEmail = email1 + "@" + email2;
        return fullEmail.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
    }

    public boolean isPasswordMatching() {
        return password != null && password.equals(passwordChck);
    }
}