package project.mvc.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import project.mvc.domain.Post;
import project.mvc.service.AuthorManager;
import project.mvc.service.PostManager;

import java.util.List;

@RestController
public class RestApiController {
    private final PostManager postManager;
    private final AuthorManager authorManager;

    public RestApiController(PostManager postManager, AuthorManager authorManager) {

        this.postManager = postManager;
        this.authorManager = authorManager;
    }

    @GetMapping("/api/post/{idPost}")
    ResponseEntity<?> getPost(@PathVariable String idPost) {
        List<Post> getPost = postManager.getPost(idPost);
        if (getPost.size() != 1) {
            return new ResponseEntity<>("Not Found Post", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(getPost, HttpStatus.OK);
    }

    @GetMapping("/api/posts/author/{username}")
    ResponseEntity<?> getPostsForUsernameAuthor(@PathVariable String username) {
        List<Post> getPostsForAuthor = authorManager.getAllPostsForUserName(username);
        if (getPostsForAuthor.size() <= 0) {
            return new ResponseEntity<>("Not Found Posts", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(getPostsForAuthor, HttpStatus.OK);
    }


}
