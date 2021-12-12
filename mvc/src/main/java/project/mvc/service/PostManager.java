package project.mvc.service;

import project.mvc.domain.Post;

import java.util.List;

public interface PostManager {
    Post addPost(Post post);

    List<Post> getAllPosts();
}
