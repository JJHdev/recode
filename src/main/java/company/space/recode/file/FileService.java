package company.space.recode.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Service
public class FileService {

    private final FileRepository fileRepository;
    private final FileConponent fileConponent;

    @Autowired
    public FileService(FileRepository fileRepository, FileConponent fileConponent) {
        this.fileRepository = fileRepository;
        this.fileConponent = fileConponent;
    }

    public List<File> findFileByexternalSeq(String externalSeq){
        return fileRepository.findByExternalSeq(externalSeq, Sort.by(Sort.Direction.ASC, "seqCode" ));
    }

    public File findFileByseqCode(Long seqCode){
        return fileRepository.findFileByseqCode(seqCode, Sort.by(Sort.Direction.ASC, "seqCode" ));
    }

    public String save(MultipartFile multipartFile, String seqCode, String fileType, String regiId) throws IOException {
        try {
            File file = new File();
            if(!multipartFile.isEmpty()){
                Path fullPath = fileConponent.getUploadFullPath(fileType, regiId);
                String saveFileName = fileConponent.getSaveFileName(multipartFile);

                file.setExternalSeq(seqCode);
                file.setFileOriginName(multipartFile.getOriginalFilename());
                file.setFileCopyName(saveFileName);
                file.setFilePath(fullPath.resolve(saveFileName).toString());
                file.setFileSize(multipartFile.getSize());
                file.setFileType(fileType);
                file.setRegiId(regiId);

                fileConponent.saveFile(multipartFile, fullPath, saveFileName);
                fileRepository.save(file);

                return "저장완료";
            }else {
                return "저장할 파일이 없음";
            }
        } catch (IOException e) {
            // 파일 저장 실패 처리
            throw new RuntimeException("파일 저장 중 오류 발생: " + e.getMessage(), e);
        } catch (InvalidPathException e) {
            // 잘못된 경로 예외 처리
            throw new RuntimeException("잘못된 경로: " + e.getMessage(), e);
        } catch (Exception e) {
            // 기타 예외 처리
            throw new RuntimeException("알 수 없는 오류 발생: " + e.getMessage(), e);
        }
    }

}
