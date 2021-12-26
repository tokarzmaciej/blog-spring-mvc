package project.mvc.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import project.mvc.domain.*;
import project.mvc.service.*;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


@Slf4j
@Controller
public class Imports {

    private final PostManager postManager;
    private final AuthorManager authorManager;
    private final CommentManager commentManager;
    private final AttachmentManager attachmentManager;
    private final PostAndAuthorManager postAndAuthorManager;

    public Imports(PostManager postManager, AuthorManager authorManager,
                   CommentManager commentManager, AttachmentManager attachmentManager,
                   PostAndAuthorManager postAndAuthorManager) {
        this.postManager = postManager;
        this.authorManager = authorManager;
        this.commentManager = commentManager;
        this.attachmentManager = attachmentManager;
        this.postAndAuthorManager = postAndAuthorManager;
    }


    @GetMapping("/imports")
    public String imports(Model model) {
        model.addAttribute("importPosts", new ImportPosts());
        model.addAttribute("importAuthors", new ImportAuthors());
        model.addAttribute("importComments", new ImportComments());
        model.addAttribute("importAttachments", new ImportAttachments());
        model.addAttribute("importPostAndAuthor", new ImportPostAndAuthor());

        return "imports";
    }


    @PostMapping("/import/posts")
    public String importPosts(Model model, @Valid ImportPosts importPosts, Errors errors) throws IOException {
        if (errors.hasErrors()) {
            model.addAttribute("importAuthors", new ImportAuthors());
            model.addAttribute("importComments", new ImportComments());
            model.addAttribute("importAttachments", new ImportAttachments());
            model.addAttribute("importPostAndAuthor", new ImportPostAndAuthor());
            return "imports";
        } else {
            String contentToString = new String(importPosts.getFileCsv().getBytes(), StandardCharsets.UTF_8);
            postManager.csvToBeans(contentToString);
            log.info("Imported posts");
        }
        return "redirect:/";
    }

    @PostMapping("/import/authors")
    public String importAuthors(Model model, @Valid ImportAuthors importAuthors, Errors errors) throws IOException {
        if (errors.hasErrors()) {
            model.addAttribute("importPosts", new ImportPosts());
            model.addAttribute("importComments", new ImportComments());
            model.addAttribute("importAttachments", new ImportAttachments());
            model.addAttribute("importPostAndAuthor", new ImportPostAndAuthor());
            return "imports";
        } else {
            String contentToString = new String(importAuthors.getFileCsv().getBytes(), StandardCharsets.UTF_8);
            authorManager.csvToBeans(contentToString);
            log.info("Imported authors");
        }
        return "redirect:/";
    }

    @PostMapping("/import/comments")
    public String importComments(Model model, @Valid ImportComments importComments, Errors errors) throws IOException {
        if (errors.hasErrors()) {
            model.addAttribute("importPosts", new ImportPosts());
            model.addAttribute("importAuthors", new ImportAuthors());
            model.addAttribute("importAttachments", new ImportAttachments());
            model.addAttribute("importPostAndAuthor", new ImportPostAndAuthor());
            return "imports";
        } else {
            String contentToString = new String(importComments.getFileCsv().getBytes(), StandardCharsets.UTF_8);
            commentManager.csvToBeans(contentToString);
            log.info("Imported comments");
        }
        return "redirect:/";
    }

    @PostMapping("/import/attachments")
    public String importAttachments(Model model, @Valid ImportAttachments importAttachments, Errors errors) throws IOException {
        if (errors.hasErrors()) {
            model.addAttribute("importPosts", new ImportPosts());
            model.addAttribute("importAuthors", new ImportAuthors());
            model.addAttribute("importComments", new ImportComments());
            model.addAttribute("importPostAndAuthor", new ImportPostAndAuthor());
            return "imports";
        } else {
            String contentToString = new String(importAttachments.getFileCsv().getBytes(), StandardCharsets.UTF_8);
            attachmentManager.csvToBeans(contentToString);
            log.info("Imported attachments");
        }
        return "redirect:/";
    }

    @PostMapping("/import/postAndAuthor")
    public String importPostAndAuthor(Model model, @Valid ImportPostAndAuthor importPostAndAuthor, Errors errors) throws IOException {
        if (errors.hasErrors()) {
            model.addAttribute("importPosts", new ImportPosts());
            model.addAttribute("importAuthors", new ImportAuthors());
            model.addAttribute("importComments", new ImportComments());
            model.addAttribute("importAttachments", new ImportAttachments());
            return "imports";
        } else {
            String contentToString = new String(importPostAndAuthor.getFileCsv().getBytes(), StandardCharsets.UTF_8);
            postAndAuthorManager.csvToBeans(contentToString);
            log.info("Imported posts and authors");
        }
        return "redirect:/";
    }

}
