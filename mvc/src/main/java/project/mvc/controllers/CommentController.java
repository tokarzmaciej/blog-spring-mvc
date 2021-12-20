package project.mvc.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import project.mvc.domain.Comment;
import project.mvc.service.CommentManager;
import project.mvc.service.PostManager;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
public class CommentController {

    private final PostManager postManager;
    private final CommentManager commentManager;

    public CommentController(PostManager postManager, CommentManager commentManager) {
        this.postManager = postManager;
        this.commentManager = commentManager;
    }


    @GetMapping("/comment/post/{idPost}")
    public String postDetailsComment(Model model, @PathVariable String idPost) {
        model.addAttribute("post", postManager.getPostView(idPost).get(0));
        model.addAttribute("modalOpen", "modal is-active");
        model.addAttribute("comment", new Comment());
        model.addAttribute("buttonValue", "Comment");
        model.addAttribute("commentToEdit", new Comment("", "", "", ""));
        model.addAttribute("linkToEditPost", "/comment/post/" + idPost);

        return "postDetailView";
    }

    @PostMapping("/comment/post/{idPost}")
    public String commentPost(Model model, @PathVariable String idPost, @Valid Comment comment, Errors errors) {
        if (errors.hasErrors()) {
            model.addAttribute("post", postManager.getPostView(idPost).get(0));
            model.addAttribute("modalOpen", "modal is-active");
            model.addAttribute("buttonValue", "Comment");
            model.addAttribute("commentToEdit", new Comment("", "", "", ""));
            model.addAttribute("linkToEditPost", "/comment/post/" + idPost);
            return "postDetailView";
        } else {
            Comment commentAdd = commentManager.addComment(comment, idPost);
            log.info("Comment added: " + commentAdd);
        }
        return "redirect:/comment/post/" + idPost;
    }

    @GetMapping("/post/delete/comment/{idComment}")
    public String deleteComment(@PathVariable String idComment) {
        String idPost = commentManager.getComment(idComment).get(0).getId_post();
        if (commentManager.deleteCommentByIdComment(idComment)) {
            return "redirect:/comment/post/" + idPost;
        } else {
            return "redirect:/noPage";

        }
    }

    @GetMapping("/post/comment/edit/{idComment}")
    public String editCommentGet(@PathVariable String idComment, Model model) {
        String idPost = commentManager.getComment(idComment).get(0).getId_post();
        List<Comment> commentToEdit = commentManager.getComment(idComment);
        if (commentToEdit.size() == 1) {
            model.addAttribute("post", postManager.getPostView(idPost).get(0));
            model.addAttribute("comment", new Comment());
            model.addAttribute("modalOpen", "modal is-active");
            model.addAttribute("buttonValue", "Edit");
            model.addAttribute("linkToEditPost", "/post/comment/edit/" + idComment);
            model.addAttribute("commentToEdit", commentToEdit.get(0));
        } else {
            return "redirect:/noPage";
        }

        return "postDetailView";
    }

    @PostMapping("/post/comment/edit/{idComment}")
    public String editCommentPost(Model model, @PathVariable String idComment, @Valid Comment comment, Errors errors) {
        if (errors.hasErrors()) {
            String idPost = commentManager.getComment(idComment).get(0).getId_post();
            List<Comment> commentToEdit = commentManager.getComment(idComment);
            model.addAttribute("post", postManager.getPostView(idPost).get(0));
            model.addAttribute("modalOpen", "modal is-active");
            model.addAttribute("buttonValue", "Edit");
            model.addAttribute("linkToEditPost", "/post/comment/edit/" + idComment);
            model.addAttribute("commentToEdit", commentToEdit.get(0));
            return "postDetailView";
        } else {
            Comment commentToEdit = commentManager.editComment(idComment, comment);
            log.info("Comment edited: " + commentToEdit);
            return "redirect:/comment/post/" + commentToEdit.getId_post();
        }

    }

}
