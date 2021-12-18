package project.mvc.service;

import project.mvc.domain.Comment;

import java.util.List;

public interface CommentManager {

    Comment addComment(Comment comment);

    List<Comment> getAllComments();

    void deleteCommentByIdPost(String idPost);

    List<Comment> getAllCommentsForPost(String idPost);

}
