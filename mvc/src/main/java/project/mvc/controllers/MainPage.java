package project.mvc.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class MainPage {

    @GetMapping("/")
    public String mainPage(Model model) {
        return "mainPage";
    }

}
