package company.space.recode.resume;

import lombok.Data;

import java.util.List;

@Data
public class ResumeFormList {
    private List<ResumeUpdateForm> resumeUpdateForms;
    private List<ResumeSaveForm> resumeSaveForms;
}
