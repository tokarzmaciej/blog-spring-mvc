package project.mvc.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import project.mvc.domain.Search;
import project.mvc.domain.Sort;
import project.mvc.service.AuthorManager;

import javax.validation.Valid;

@Slf4j
@Controller
public class AuthorController {

    private final AuthorManager authorManager;

    public AuthorController(AuthorManager authorManager) {
        this.authorManager = authorManager;
    }


    @GetMapping("/authors")
    public String getAuthors(Model model) {
        model.addAttribute("authors", authorManager.getAllAuthorViews());
        model.addAttribute("search", new Search());
        return "authors";
    }

    @GetMapping("/author/{username}")
    public String getAuthor(Model model, @PathVariable String username) {
        model.addAttribute("author", authorManager.getAuthorView(username).get(0));
        return "listCommentsForAuthor";
    }

    @GetMapping("/author/search/{value}")
    public String searchAuthor(Model model, @PathVariable String value) {
        model.addAttribute("authors", authorManager.getAuthorViewForSearch(value));
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