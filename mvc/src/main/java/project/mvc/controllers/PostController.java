package project.mvc.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.mvc.domain.Comment;
import project.mvc.domain.Post;
import project.mvc.domain.PostForm;
import project.mvc.service.AuthorManager;
import project.mvc.service.PostAndAuthorManager;
import project.mvc.service.PostManager;
import project.mvc.storage.StorageFileNotFoundException;
import project.mvc.storage.StorageService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


@Slf4j
@Controller
public class PostController {

    private final PostManager postManager;
    private final AuthorManager authorManager;
    private final StorageService storageService;
    private final PostAndAuthorManager postAndAuthorManager;

    public PostController(PostManager postManager, AuthorManager authorManager,
                          StorageService storageService, PostAndAuthorManager postAndAuthorManager) {
        this.postManager = postManager;
        this.authorManager = authorManager;
        this.storageService = storageService;
        this.postAndAuthorManager = postAndAuthorManager;
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
    public String create(Model model) {
        List<String> selectedAuthors = new ArrayList<>();
        Post newPost = new Post("", "", "");
        model.addAttribute("postForm", new PostForm());
        model.addAttribute("authors", authorManager.getAllAuthors());
        model.addAttribute("link", "/post/create");
        model.addAttribute("function", "Creating a post");
        model.addAttribute("postToEdit", newPost);
        model.addAttribute("selectedAuthors", selectedAuthors);


        return "postForm";
    }

    @PostMapping("/post/create")
    public String createPost(Model model, @Valid PostForm postForm, Errors errors, RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            model.addAttribute("authors", authorManager.getAllAuthors());
            return "postForm";
        } else {
            Post postToAdd = postManager.addPost(postForm, save(postForm));
            log.info("Post created: " + postToAdd);
            redirectAttributes.addFlashAttribute("info", "Created new post");
        }
        return "redirect:/";
    }

    private List<String> save(@Valid PostForm postForm) {
        storageService.storeImage(postForm.getImageFile());
        List<String> attachments = new ArrayList<>();
        Arrays.asList(postForm.getAttachment()).forEach(file -> {
            if (!file.isEmpty()) {
                String idFile = UUID.randomUUID().toString();
                String filename = idFile + file.getOriginalFilename();
                attachments.add(filename);
                try {
                    storageService.storeAttachment(file.getInputStream(), filename);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return attachments;
    }

    @GetMapping("/post/delete/{idPost}")
    public String delete(@PathVariable String idPost, RedirectAttributes redirectAttributes) {
        if (postManager.deletePost(idPost)) {
            redirectAttributes.addFlashAttribute("infoDelete", "Deleted post");
            return "redirect:/";
        } else {
            return "redirect:/noPage";
        }
    }

    @GetMapping("/post/edit/{idPost}")
    public String edit(@PathVariable String idPost, Model model) {
        List<Post> postToEdit = postManager.getPost(idPost);
        if (postToEdit.size() == 1) {
            model.addAttribute("postForm", new PostForm());
            model.addAttribute("authors", authorManager.getAllAuthors());
            model.addAttribute("link", "/post/edit/" + idPost);
            model.addAttribute("function", "Editing a post");
            model.addAttribute("postToEdit", postManager.getPost(idPost).get(0));
            model.addAttribute("selectedAuthors", postAndAuthorManager.getIdAuthorsForPost(idPost));
        } else {
            return "redirect:/noPage";
        }

        return "postForm";
    }

    @PostMapping("/post/edit/{idPost}")
    public String editPost(Model model, @PathVariable String idPost, @Valid PostForm postForm,
                           Errors errors, RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            model.addAttribute("authors", authorManager.getAllAuthors());
            model.addAttribute("postToEdit", postManager.getPost(idPost).get(0));
            model.addAttribute("selectedAuthors", postAndAuthorManager.getIdAuthorsForPost(idPost));
            return "postForm";
        } else {
            Post postToEdit = postManager.editPost(idPost, postForm, save(postForm));
            log.info("Post edited: " + postToEdit);
            redirectAttributes.addFlashAttribute("info", "Edited post");
        }
        return "redirect:/";
    }

    @GetMapping("/post/{idPost}")
    public String postDetails(Model model, @PathVariable String idPost) {
        model.addAttribute("post", postManager.getPostView(idPost).get(0));
        model.addAttribute("modalOpen", "modal");
        model.addAttribute("comment", new Comment());
        model.addAttribute("buttonValue", "Comment");

        return "postDetailView";
    }


    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound() {
        return ResponseEntity.notFound().build();
    }
}
