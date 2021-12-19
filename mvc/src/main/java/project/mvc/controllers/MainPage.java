package project.mvc.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import project.mvc.domain.*;
import project.mvc.service.PostManager;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;


@Slf4j
@Controller
public class MainPage {

    private final PostManager postManager;

    public MainPage(PostManager postManager) {
        this.postManager = postManager;
    }


    @GetMapping("/")
    public String mainPage(Model model) {
        model.addAttribute("posts", postManager.getAllPostsView());
        model.addAttribute("search", new Search());
        model.addAttribute("sort",
                new Sort(true, false, false, false, false, false)
        );

        return "mainPage";
    }

    @GetMapping("/sortByAlphabeticallyDown")
    public String mainPageSortByAlphabeticallyDown(Model model) {
        List<PostView> posts = postManager.getAllPostsView();
        Collections.reverse(posts);
        model.addAttribute("posts", posts);
        model.addAttribute("search", new Search());
        model.addAttribute("sort",
                new Sort(false, true, false, false, false, false)
        );
        return "mainPage";
    }

    @GetMapping("/sortByAuthorSizeUp")
    public String mainPageSortByAuthorSizeUp(Model model) {
        model.addAttribute("posts", postManager.getAllPostsViewSortByAuthorSize());
        model.addAttribute("search", new Search());
        model.addAttribute("sort",
                new Sort(false, false, true, false, false, false)
        );
        return "mainPage";
    }

    @GetMapping("/sortByAuthorSizeDown")
    public String mainPageSortByAuthorSizeDown(Model model) {
        List<PostView> posts = postManager.getAllPostsViewSortByAuthorSize();
        Collections.reverse(posts);
        model.addAttribute("posts", posts);
        model.addAttribute("search", new Search());
        model.addAttribute("sort",
                new Sort(false, false, false, true, false, false)
        );

        return "mainPage";
    }

    @GetMapping("/sortByCommentSizeUp")
    public String mainPageSortByCommentSizeUp(Model model) {
        model.addAttribute("posts", postManager.getAllPostsViewSortByCommentSize());
        model.addAttribute("search", new Search());
        model.addAttribute("sort",
                new Sort(false, false, false, false, true, false)
        );
        return "mainPage";
    }

    @GetMapping("/sortByCommentSizeDown")
    public String mainPageSortByCommentSizeDown(Model model) {
        List<PostView> posts = postManager.getAllPostsViewSortByCommentSize();
        Collections.reverse(posts);
        model.addAttribute("posts", posts);
        model.addAttribute("search", new Search());
        model.addAttribute("sort",
                new Sort(false, false, false, false, false, true)
        );
        return "mainPage";
    }

    @GetMapping("/post/search/{value}")
    public String search(Model model, @PathVariable String value) {
        model.addAttribute("posts", postManager.getAllPostsForSearch(value));
        model.addAttribute("search", new Search());
        model.addAttribute("sort",
                new Sort(true, true, false, false, false, false)
        );

        return "mainPage";
    }

    @PostMapping("/post/search")
    public String createPost(@Valid Search search, Errors errors) {
        if (errors.hasErrors()) {
            return "mainPage";
        }
        return "redirect:/post/search/" + search.getValue();

    }
}
