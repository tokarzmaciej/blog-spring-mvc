package project.mvc.service;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.mvc.domain.Comment;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CommentManagerInMemoryImp implements CommentManager {

    @Setter
    private List<Comment> db;

    public CommentManagerInMemoryImp(@Autowired List<Comment> db) {
        this.db = db;
    }

    @Override
    public Comment addComment(Comment comment) {
        db.add(comment);
        return comment;
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
}
