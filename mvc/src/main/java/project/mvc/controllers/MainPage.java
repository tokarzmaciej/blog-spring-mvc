package project.mvc.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.mvc.service.PostManager;

@Slf4j
@Controller
public class MainPage {

    private final PostManager postManager;

    public MainPage(PostManager postManager) {
        this.postManager = postManager;
    }


    @GetMapping("/")
    public String mainPage(Model model) {

        model.addAttribute("posts", postManager.getAllPosts());
        return "mainPage";
    }

}
