package project.mvc.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.mvc.domain.Post;
import project.mvc.domain.PostView;
import project.mvc.service.PostManager;

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
        model.addAttribute("isSortAlpUp", true);
        model.addAttribute("isSortAlpDown", false);
        model.addAttribute("isSortAutUp", false);
        model.addAttribute("isSortAutDown", false);
        model.addAttribute("isSortComUp", false);
        model.addAttribute("isSortComDown", false);

        return "mainPage";
    }

    @GetMapping("/sortByAlphabeticallyDown")
    public String mainPageSortByAlphabeticallyDown(Model model) {
        List<PostView> posts = postManager.getAllPostsView();
        Collections.reverse(posts);
        model.addAttribute("posts", posts);
        model.addAttribute("isSortAlpUp", false);
        model.addAttribute("isSortAlpDown", true);
        model.addAttribute("isSortAutUp", false);
        model.addAttribute("isSortAutDown", false);
        model.addAttribute("isSortComUp", false);
        model.addAttribute("isSortComDown", false);
        return "mainPage";
    }

    @GetMapping("/sortByAuthorSizeUp")
    public String mainPageSortByAuthorSizeUp(Model model) {
        model.addAttribute("posts", postManager.getAllPostsViewSortByAuthorSize());
        model.addAttribute("isSortAlpUp", false);
        model.addAttribute("isSortAlpDown", false);
        model.addAttribute("isSortAutUp", true);
        model.addAttribute("isSortAutDown", false);
        model.addAttribute("isSortComUp", false);
        model.addAttribute("isSortComDown", false);
        return "mainPage";
    }

    @GetMapping("/sortByAuthorSizeDown")
    public String mainPageSortByAuthorSizeDown(Model model) {
        List<PostView> posts = postManager.getAllPostsViewSortByAuthorSize();
        Collections.reverse(posts);
        model.addAttribute("posts", posts);
        model.addAttribute("isSortAlpUp", false);
        model.addAttribute("isSortAlpDown", false);
        model.addAttribute("isSortAutUp", false);
        model.addAttribute("isSortAutDown", true);
        model.addAttribute("isSortComUp", false);
        model.addAttribute("isSortComDown", false);
        return "mainPage";
    }

    @GetMapping("/sortByCommentSizeUp")
    public String mainPageSortByCommentSizeUp(Model model) {
        model.addAttribute("posts", postManager.getAllPostsViewSortByCommentSize());
        model.addAttribute("isSortAlpUp", false);
        model.addAttribute("isSortAlpDown", false);
        model.addAttribute("isSortAutUp", false);
        model.addAttribute("isSortAutDown", false);
        model.addAttribute("isSortComUp", true);
        model.addAttribute("isSortComDown", false);
        return "mainPage";
    }

    @GetMapping("/sortByCommentSizeDown")
    public String mainPageSortByCommentSizeDown(Model model) {
        List<PostView> posts = postManager.getAllPostsViewSortByCommentSize();
        Collections.reverse(posts);
        model.addAttribute("posts", posts);
        model.addAttribute("isSortAlpUp", false);
        model.addAttribute("isSortAlpDown", false);
        model.addAttribute("isSortAutUp", false);
        model.addAttribute("isSortAutDown", false);
        model.addAttribute("isSortComUp", false);
        model.addAttribute("isSortComDown", true);
        return "mainPage";
    }

}
