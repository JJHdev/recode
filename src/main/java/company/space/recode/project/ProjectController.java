package company.space.recode.project;

import company.space.recode.file.File;
import company.space.recode.file.FileService;
import company.space.recode.user.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class ProjectController {

    private final ProjectService projectService;
    private final FileService fileService;

    @Autowired
    public ProjectController(ProjectService projectService, FileService fileService) {
        this.projectService = projectService;
        this.fileService = fileService;
    }

    @GetMapping("/viewProject.do")
    public String viewProject(Model model, HttpServletRequest request) {
        User userInfo = getUserInfo();
        List<Project> projectList = projectService.findProjectByRegiId(userInfo.getUserId());
        List<List<File>> FileList = new ArrayList<>();

        for(Project project : projectList){
            FileList.add(fileService.findFileByexternalSeq(project.getFileNo()));
        }

        model.addAttribute("fileList", FileList);
        model.addAttribute("projectList", projectList);
        return "project/viewProject";
    }

    @GetMapping("/regiProject.do")
    public String regiProjects(Model model, HttpServletRequest request) {
        User userInfo = getUserInfo();
        List<Project> projectList = projectService.findProjectByRegiId(userInfo.getUserId());
        List<File> fileListResult = new ArrayList<>();

        for(Project project : projectList){
            List<File> fileList = fileService.findFileByexternalSeq(project.getFileNo());
            // Project 객체에 FileYn 값 설정
            if (fileList != null && !fileList.isEmpty()) {
                project.setFileYn("Y");
                for(File file : fileList){
                    fileListResult.add(file);
                }
            } else {
                project.setFileYn("N");
            }
        }

        model.addAttribute("fileList", fileListResult);
        model.addAttribute("projectList", projectList);
        return "project/regiProject";
    }

    @PostMapping("/regiProject.do")
    public String registerProjects(@ModelAttribute ProjectSaveDto projectList) throws IOException {
        User userInfo = getUserInfo();
        List<Project> projects = projectList.getProjectList();
        List<File> files = projectList.getFileList();
        List<Long> delProjectCodeList = projectList.getDelProjectCode();
        List<Long> delFileCodeList = projectList.getDelFileCode();

        for (Long seqCode : delProjectCodeList) {
            if (seqCode != null) { // null 체크만 수행
                projectService.deleteProject(seqCode);
            }
        }

        for (Long seqCode : delFileCodeList) {
            if (seqCode != null) { // null 체크만 수행
                fileService.delete(seqCode);
            }
        }

        // 프로젝트 처리
        for (int i = 0; i < projects.size(); i++) {
            Project project = projects.get(i);
            MultipartFile file = files.get(i).getFile();

            project.setRegiId(userInfo.getUserId());
            // start, end 만 들어오도록 하면 됨
            Project saveProject = projectService.saveProject(project);
            if (saveProject != null) {
                if (files.get(i).getSeqCode() == null || files.get(i).getSeqCode() == 0) {
                    fileService.save(file, saveProject.getFileNo(), "Project_Orig", saveProject.getRegiId());
                } else if (files.get(i).getSeqCode() != null) {
                    fileService.update(files.get(i).getSeqCode(), file, saveProject.getFileNo(), "Project_Orig", saveProject.getRegiId());
                }
            }
        }


        return "redirect:/regiProject.do";
    }



    public User getUserInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof User) {
                User user = (User) principal;
                return user;
            }
        }
        return new User();
    }
}
