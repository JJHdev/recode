package company.space.recode.hello;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdateForm {

    @Size(max = 50)
    private String userName;

    @Size(max = 300)
    private String password;

    @Size(max = 300)
    private String passwordChck;

    @Pattern(regexp = "[MF]")
    private String gender;

    @Size(max = 200)
    private String email1;

    @Size(max = 200)
    private String email2;

    @Size(max = 300)
    private String profilePictureUrl;

    @Size(max = 20)
    private String status;

}