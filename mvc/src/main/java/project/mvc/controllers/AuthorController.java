package project.mvc.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import project.mvc.domain.AuthorView;
import project.mvc.domain.Search;
import project.mvc.service.AuthorManager;
import project.mvc.service.PostAndAuthorManager;
import project.mvc.service.PostManager;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
public class AuthorController {

    private final AuthorManager authorManager;
    private final PostAndAuthorManager postAndAuthorManager;
    private final PostManager postManager;

    public AuthorController(AuthorManager authorManager, PostAndAuthorManager postAndAuthorManager, PostManager postManager) {
        this.authorManager = authorManager;
        this.postAndAuthorManager = postAndAuthorManager;
        this.postManager = postManager;
    }


    @GetMapping("/authors")
    public String getAuthors(Model model) {
        model.addAttribute("authors", authorManager.getAllAuthorViews(postAndAuthorManager.getAllPostAndAuthor(),
                postManager.getAllPosts()));
        model.addAttribute("search", new Search());
        return "authors";
    }

    @GetMapping("/author/{username}")
    public String getAuthor(Model model, @PathVariable String username) {
        List<AuthorView> author = authorManager.getAuthorView(username, postAndAuthorManager.getAllPostAndAuthor(),
                postManager.getAllPosts());
        if (author.size() == 1) {
            model.addAttribute("author", author.get(0));
            return "listCommentsForAuthor";
        } else {
            return "redirect:/noPage";
        }
    }

    @GetMapping("/author/search/{value}")
    public String searchAuthor(Model model, @PathVariable String value) {
        model.addAttribute("authors", authorManager.getAuthorViewForSearch(value, postAndAuthorManager.getAllPostAndAuthor(),
                postManager.getAllPosts()));
        model.addAttribute("search", new Search());
        return "authors";
    }

    @PostMapping("/author/search")
    public String searchAuthor(@Valid Search search, Errors errors) {
        if (errors.hasErrors()) {
            return "authors";
        }
        return "redirect:/author/search/" + search.getValue();
    }
}