package company.space.recode.gis;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GisController {
    @GetMapping("/goGis.do")
    public String goGis(){
        return "/gis/gis2";
    }
}
