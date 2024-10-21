package company.space.recode.sys.syscode;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "SYS_CODE")
@Data
public class SysCode {

    public SysCode() {}
    public SysCode(String code, String codeNm) {
        this.code = code;
        this.codeNm = codeNm;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sys_code_key_generator")
    @SequenceGenerator(name = "sys_code_key_generator", sequenceName = "SYS_CODE_SEQ", allocationSize = 1)
    @Column(name = "SEQ")
    private Long seq;

    @Column(name = "PARENT_CODE")
    private String parentCode;

    @Column(name = "CODE", nullable = false)
    private String code;

    @Column(name = "CODE_NM", nullable = false)
    private String codeNm;

    @Column(name = "ODR")
    private Long odr;

    @Column(name = "USE_YN", nullable = false)
    private String useYn;

    @Column(name = "REGI_ID")
    private String regiId;

    @Column(name = "REGI_DATE")
    private LocalDateTime regiDate;

    @PrePersist
    protected void onCreate() {
        this.regiDate = LocalDateTime.now();
        this.useYn = "Y";
    }
}
