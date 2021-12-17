package project.mvc.service;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.mvc.domain.PostAndAuthor;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PostAndAuthorManagerInMemoryImp implements PostAndAuthorManager {

    @Setter
    private List<PostAndAuthor> db;

    public PostAndAuthorManagerInMemoryImp(@Autowired List<PostAndAuthor> db) {
        this.db = db;
    }

    @Override
    public PostAndAuthor addPostAndAuthor(PostAndAuthor postAndAuthor) {
        db.add(postAndAuthor);
        return postAndAuthor;
    }

    @Override
    public List<PostAndAuthor> getAllPostAndAuthor() {
        return db;
    }

    @Override
    public void deletePostAndAuthorByIdPost(String idPost) {
        setDb(db.stream().filter(row -> !Objects.equals(row.getId_post(), idPost))
                .collect(Collectors.toList()));
    }
}
