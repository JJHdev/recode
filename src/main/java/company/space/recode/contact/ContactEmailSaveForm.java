package company.space.recode.contact;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
public class ContactEmailSaveForm {

    @Size(max = 50)
    private String sendRegiName;

    @NotBlank
    @Size(max = 200)
    private String sendRegiEmail;

    @Size(max = 20)
    private String sendRegiPhone;

    private String sendRegiNote;

    private String sendStatus;

    @Size(max = 50)
    private String regiId;

    private LocalDateTime regiDate;

    @PrePersist
    protected void onCreate() {
        this.regiDate = LocalDateTime.now();
    }

}