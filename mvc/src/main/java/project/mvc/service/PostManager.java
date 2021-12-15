package project.mvc.service;

import project.mvc.domain.Post;
import project.mvc.domain.PostForm;

import java.util.List;

public interface PostManager {
    Post addPost(PostForm postForm);

    List<Post> getAllPosts();
}
