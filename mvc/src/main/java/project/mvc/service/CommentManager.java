package project.mvc.service;

import project.mvc.domain.Comment;
import project.mvc.domain.Post;
import project.mvc.domain.PostForm;

import java.util.List;

public interface CommentManager {

    Comment addComment(Comment comment, String idPost);

    List<Comment> getAllComments();

    void deleteCommentByIdPost(String idPost);

    List<Comment> getAllCommentsForPost(String idPost);

    List<Comment> getComment(String idComment);

    Boolean deleteCommentByIdComment(String idComment);

    Comment editComment(String idComment, Comment comment);

    List<Comment> getCommentsForUsername(String username);

}
