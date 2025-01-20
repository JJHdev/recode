package company.space.recode.project;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "PROJECT")
@Data
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_key_generator")
    @SequenceGenerator(name = "project_key_generator", sequenceName = "project_key_seq", allocationSize = 1)
    @Column(name = "SEQ_CODE")
    private Long seqCode;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "START_DATE")
    private LocalDate startDate;

    @Column(name = "END_DATE")
    private LocalDate  endDate;

    @Column(name = "FILE_NO")
    private String  fileNo;

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
