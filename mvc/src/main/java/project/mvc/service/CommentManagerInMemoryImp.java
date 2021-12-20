package project.mvc.service;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.mvc.domain.Comment;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CommentManagerInMemoryImp implements CommentManager {

    @Setter
    private List<Comment> db;

    public CommentManagerInMemoryImp(@Autowired List<Comment> db) {
        this.db = db;
    }

    @Override
    public Comment addComment(Comment comment, String idPost) {
        String idComment = UUID.randomUUID().toString();
        Comment createdComment = new Comment(idComment, comment.getUsername(), idPost, comment.getComment_content());
        db.add(createdComment);
        return createdComment;
    }

    @Override
    public List<Comment> getAllComments() {
        return db;
    }

    @Override
    public void deleteCommentByIdPost(String idPost) {
        setDb(db.stream().filter(comment -> !Objects.equals(comment.getId_post(), idPost))
                .collect(Collectors.toList()));
    }

    @Override
    public List<Comment> getAllCommentsForPost(String idPost) {
        return db.stream().filter(comment -> Objects.equals(comment.getId_post(), idPost))
                .collect(Collectors.toList());
    }

    @Override
    public List<Comment> getComment(String idComment) {
        return db.stream()
                .filter(comment -> Objects.equals(comment.getId(), idComment))
                .collect(Collectors.toList());
    }

    @Override
    public Boolean deleteCommentByIdComment(String idComment) {
        if (getComment(idComment).size() == 1) {
            setDb(db.stream()
                    .filter(comment -> !Objects.equals(comment.getId(), idComment))
                    .collect(Collectors.toList()));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Comment editComment(String idComment, Comment comment) {
        setDb(db.stream().map(com -> {
            if (Objects.equals(com.getId(), idComment)) {
                return new Comment(idComment, comment.getUsername(), com.getId_post(), comment.getComment_content());
            } else {
                return com;
            }
        }).collect(Collectors.toList()));
        return getComment(idComment).get(0);
    }
}
