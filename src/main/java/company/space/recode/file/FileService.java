package company.space.recode.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
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

    public String save(MultipartFile multipartFile, String externalSeq, String fileType, String regiId) throws IOException {
        try {
            File file = new File();
            if(!multipartFile.isEmpty()){
                Path fullPath = fileConponent.getUploadFullPath(fileType, regiId);
                String saveFileName = fileConponent.getSaveFileName(multipartFile);

                file.setExternalSeq(externalSeq);
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

    public String update(Long seqCode, MultipartFile multipartFile, String externalSeq, String fileType, String regiId) throws IOException {
        if(seqCode == null){
            return "키값이 없습니다.";
        }
        if(multipartFile.isEmpty()){
            return "저장할 파일이 없습니다.";
        }
        File fileDel = fileRepository.findFileByseqCode(seqCode, Sort.by(Sort.Direction.ASC, "seqCode" ));
        String filePath = fileDel.getFilePath();
        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalStateException("File path is missing for file with seqCode: " + seqCode);
        }
        Path path = Paths.get(filePath);
        if (Files.exists(path)) {
            Files.delete(path); // 파일 삭제
        }

        if(externalSeq == null || externalSeq.isEmpty()){
            externalSeq = fileDel.getExternalSeq();
        }
        if(fileType == null || fileType==null){
            fileType = fileDel.getFileType();
        }
        if(regiId == null || regiId.isEmpty()){
            regiId = fileDel.getRegiId();
        }

        try {
            File fileSave = new File();

            Path fullPath = fileConponent.getUploadFullPath(fileType, regiId);
            String saveFileName = fileConponent.getSaveFileName(multipartFile);

            fileSave.setSeqCode(fileDel.getSeqCode());
            fileSave.setExternalSeq(externalSeq);
            fileSave.setFileOriginName(multipartFile.getOriginalFilename());
            fileSave.setFileCopyName(saveFileName);
            fileSave.setFilePath(fullPath.resolve(saveFileName).toString());
            fileSave.setFileSize(multipartFile.getSize());
            fileSave.setFileType(fileType);
            fileSave.setRegiId(regiId);

            fileConponent.saveFile(multipartFile, fullPath, saveFileName);
            fileRepository.save(fileSave);

            return "저장완료";

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

    public void delete(Long seqCode) throws IOException {

        File file = fileRepository.findFileByseqCode(seqCode, Sort.by(Sort.Direction.ASC, "seqCode" ));
        String filePath = file.getFilePath();
        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalStateException("File path is missing for file with seqCode: " + seqCode);
        }

        Path path = Paths.get(filePath);
        if (Files.exists(path)) {
            Files.delete(path); // 파일 삭제
        }
        fileRepository.deleteById(seqCode);

    }

}
