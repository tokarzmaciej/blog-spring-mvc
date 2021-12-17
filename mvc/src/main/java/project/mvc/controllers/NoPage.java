package project.mvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NoPage {
    @GetMapping("/noPage")
    public String noPage() {
        return "noPage";
    }
}


