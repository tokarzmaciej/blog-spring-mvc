package project.mvc.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import project.mvc.domain.Post;
import project.mvc.domain.PostForm;
import project.mvc.service.AuthorManager;
import project.mvc.service.PostManager;

import javax.validation.Valid;

@Slf4j
@Controller
public class PostController {

    private final PostManager postManager;
    private final AuthorManager authorManager;

    public PostController(PostManager postManager, AuthorManager authorManager) {
        this.postManager = postManager;
        this.authorManager = authorManager;
    }


    @GetMapping("/create/post")
    public String form(Model model) {
        model.addAttribute("postForm", new PostForm());
        model.addAttribute("authors", authorManager.getAllAuthors());

        return "postForm";
    }

    @PostMapping("/create/post")
    public String createPost(Model model, @Valid PostForm postForm, Errors errors) {
        if (errors.hasErrors()) {
            model.addAttribute("authors", authorManager.getAllAuthors());
            return "postForm";
        } else {
            Post postToAdd = postManager.addPost(postForm);
            log.info("Post created: " + postToAdd);
        }
        return "redirect:/";
    }

}
