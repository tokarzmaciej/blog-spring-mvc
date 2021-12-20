package project.mvc.service;

import project.mvc.domain.Post;
import project.mvc.domain.PostForm;
import project.mvc.domain.PostView;

import java.util.List;

public interface PostManager {
    Post addPost(PostForm postForm);

    List<Post> getAllPosts();

    Boolean deletePost(String idPost);

    List<Post> getPost(String idPost);

    List<PostView> getPostView(String idPost);

    Post editPost(String idPost, PostForm postForm);

    List<PostView> getAllPostsView();

    List<PostView> getAllPostsViewSortByAuthorSize();

    List<PostView> getAllPostsViewSortByCommentSize();

    List<PostView> getAllPostsForSearch(String value);

}
