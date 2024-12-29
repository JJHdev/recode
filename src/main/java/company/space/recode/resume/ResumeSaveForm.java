package company.space.recode.resume;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
public class ResumeSaveForm {

    private Long seqCode;

    @NotBlank
    @Size(max = 50)
    private String resumeGrbun;

    @NotBlank
    @Size(max = 200)
    private String title;

    @Size(max = 500)
    private String subContent;

    private String content;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String regiId;

    private LocalDateTime regiDate;

    private String updtId;

    private LocalDateTime updtDate;

}
