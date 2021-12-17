package project.mvc.service;

import project.mvc.domain.Post;
import project.mvc.domain.PostForm;

import java.util.List;

public interface PostManager {
    Post addPost(PostForm postForm);
    List<Post> getAllPosts();
    Boolean deletePost(String idPost);
    List<Post> getPost(String idPost);
    Post editPost(String idPost,PostForm postForm);

}
