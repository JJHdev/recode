package company.space.recode.contact;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "SEND_EMAIL_LOG")
public class ContactEmail {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "send_email_log_seq")
    @SequenceGenerator(name = "send_email_log_seq", sequenceName = "send_email_log_seq", allocationSize = 1)
    @Column(name = "SEQ_CODE", unique = true, nullable = false)
    private Long seqCode;

    @Column(name = "SEND_REGI_NAME")
    private String sendRegiName;

    @Column(name = "SEND_REGI_EMAIL")
    private String sendRegiEmail;

    @Column(name = "SEND_REGI_PHONE")
    private String sendRegiPhone;

    @Column(name = "SEND_REGI_NOTE")
    private String sendRegiNote;

    @Column(name = "SEND_STATUS")
    private String sendStatus;

    @Column(name = "REGI_ID")
    private String regiId;

    @Column(name = "REGI_DATE")
    private LocalDateTime regiDate;

    @PrePersist
    protected void onCreate() {
        this.regiDate = LocalDateTime.now();
    }

}