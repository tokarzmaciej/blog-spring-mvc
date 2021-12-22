package project.mvc.service;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.mvc.domain.Author;
import project.mvc.domain.PostAndAuthor;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PostAndAuthorManagerInMemoryImp implements PostAndAuthorManager {

    @Setter
    private List<PostAndAuthor> db;
    private final AuthorManager authorManager;

    public PostAndAuthorManagerInMemoryImp(AuthorManager authorManager, @Autowired List<PostAndAuthor> db) {
        this.db = db;
        this.authorManager = authorManager;
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

    @Override
    public List<String> getIdAuthorsForPost(String idPost) {
        return db.stream().filter(row -> Objects.equals(row.getId_post(), idPost))
                .map(PostAndAuthor::getId_author).collect(Collectors.toList());
    }


    @Override
    public List<Author> getAllAuthorsForPost(String idPost) {
        return db.stream().filter(row -> Objects.equals(row.getId_post(), idPost))
                .map(author -> authorManager.getAuthor(author.getId_author()))
                .collect(Collectors.toList());
    }


}
