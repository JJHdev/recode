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
import org.springframework.web.bind.annotation.PostMapping;

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

        model.addAttribute("FileList", FileList);
        model.addAttribute("projectList", projectList);
        return "project/viewProject";
    }

    @GetMapping("/regiProject.do")
    public String regiProjects(Model model, HttpServletRequest request) {
        User userInfo = getUserInfo();
        List<Project> project = projectService.findProjectByRegiId(userInfo.getUserId());

        model.addAttribute("project", project);
        return "project/regiProject";
    }

    @PostMapping("/regiProject.do")
    public String registerProjects(Model model, HttpServletRequest request) {
        User userInfo = getUserInfo();
        List<Project> project = projectService.findProjectByRegiId(userInfo.getUserId());

        model.addAttribute("project", project);
        return "project/regiProject";
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
