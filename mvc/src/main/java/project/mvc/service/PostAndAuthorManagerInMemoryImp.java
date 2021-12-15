package project.mvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.mvc.domain.PostAndAuthor;

import java.util.List;

@Service
public class PostAndAuthorManagerInMemoryImp implements PostAndAuthorManager {

    @Autowired
    List<PostAndAuthor> db;

    @Override
    public PostAndAuthor addPostAndAuthor(PostAndAuthor postAndAuthor) {
        db.add(postAndAuthor);
        return postAndAuthor;
    }

    @Override
    public List<PostAndAuthor> getAllPostAndAuthor() {
        return db;
    }
}
