package project.mvc.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import project.mvc.domain.Post;
import project.mvc.domain.PostForm;
import project.mvc.service.AuthorManager;
import project.mvc.service.PostManager;
import project.mvc.storage.StorageFileNotFoundException;
import project.mvc.storage.StorageService;

import javax.validation.Valid;

@Slf4j
@Controller
public class PostController {

    private final PostManager postManager;
    private final AuthorManager authorManager;
    private final StorageService storageService;

    public PostController(PostManager postManager, AuthorManager authorManager, StorageService storageService) {
        this.postManager = postManager;
        this.authorManager = authorManager;
        this.storageService = storageService;
    }

    @GetMapping("/files/image/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFileToImage(@PathVariable String filename) {

        Resource file = storageService.loadAsResourceImage(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("/files/attachment/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFileToAttachment(@PathVariable String filename) {

        Resource file = storageService.loadAsResourceAttachment(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("/post/create")
    public String form(Model model) {
        model.addAttribute("postForm", new PostForm());
        model.addAttribute("authors", authorManager.getAllAuthors());

        return "postForm";
    }

    @PostMapping("/post/create")
    public String createPost(Model model, @Valid PostForm postForm, Errors errors) {
        if (errors.hasErrors()) {
            model.addAttribute("authors", authorManager.getAllAuthors());
            return "postForm";
        } else {
            storageService.storeImage(postForm.getImageFile());
            storageService.storeAttachment(postForm.getAttachment());
            Post postToAdd = postManager.addPost(postForm);
            log.info("Post created: " + postToAdd);

        }
        return "redirect:/";
    }

    @GetMapping("/post/delete/{idPost}")
    public String delete(@PathVariable String idPost) {
        if (postManager.deletePost(idPost)) {
            return "redirect:/";
        } else {
            return "redirect:/noPage";

        }
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
