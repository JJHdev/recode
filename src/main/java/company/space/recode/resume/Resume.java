package company.space.recode.resume;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "RESUME")
@Data
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "resume_key_generator")
    @SequenceGenerator(name = "resume_key_generator", sequenceName = "resume_key_seq", allocationSize = 1)
    @Column(name = "SEQ_CODE")
    private Long seqCode;

    @Column(name = "RESUME_GRBUN", nullable = false, unique = true)
    private String resumeGrbun;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "SUB_CONTENT")
    @ToString.Exclude
    private String subContent;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "START_DATE")
    private LocalDateTime startDate;

    @Column(name = "END_DATE")
    private LocalDateTime endDate;

    @Column(name = "REGI_ID", updatable = false)
    private String regiId;

    @Column(name = "REGI_DATE", updatable = false)
    private LocalDateTime regiDate;

    @Column(name = "UPDT_ID")
    private String updtId;

    @Column(name = "UPDT_DATE")
    private LocalDateTime updtDate;

    @PrePersist
    protected void onCreate() {
        this.regiDate = LocalDateTime.now();
        this.updtDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updtDate = LocalDateTime.now();
    }

}
