package company.space.recode.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class FileService {

    private final FileRepository fileRepository;

    @Autowired
    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public List<File> findFileByexternalSeq(String externalSeq){
        return fileRepository.findByExternalSeq(externalSeq, Sort.by(Sort.Direction.ASC, "seqCode" ));
    }

}
