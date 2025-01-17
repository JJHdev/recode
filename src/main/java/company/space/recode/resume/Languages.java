package company.space.recode.resume;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "LANGUAGES")
@Data
public class Languages {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "languages_key_generator")
    @SequenceGenerator(name = "languages_key_generator", sequenceName = "languages_key_seq", allocationSize = 1)
    @Column(name = "SEQ_CODE")
    private Long seqCode;

    @Column(name = "TITLE")
    private String title;

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
