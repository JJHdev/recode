package company.space.recode.file;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "FILE")
@Data
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "file_key_generator")
    @SequenceGenerator(name = "file_key_generator", sequenceName = "file_key_seq", allocationSize = 1)
    @Column(name = "SEQ_CODE")
    private Long seqCode;

    @Column(name = "EXTERNAL_SEQ", nullable = false)
    private String externalSeq;

    @Column(name = "FILE_ORIG_NAME", nullable = false)
    private String fileOriginName;

    @Column(name = "FILE_COPY_NAME", nullable = false)
    private String fileCopyName;

    @Column(name = "FILE_PATH", nullable = false)
    private String filePath;

    @Column(name = "FILE_SIZE")
    private Long fileSize;

    @Column(name = "FILE_TYPE")
    private String fileType;

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
