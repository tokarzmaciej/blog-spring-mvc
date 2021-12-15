package project.mvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.mvc.domain.Post;
import project.mvc.domain.PostAndAuthor;
import project.mvc.domain.PostForm;

import java.util.List;
import java.util.UUID;

@Service
public class PostManagerInMemoryImp implements PostManager {

    private final PostAndAuthorManager postAndAuthorManager;
    @Autowired
    List<Post> db;

    public PostManagerInMemoryImp(PostAndAuthorManager postAndAuthorManager) {
        this.postAndAuthorManager = postAndAuthorManager;
    }

    @Override
    public Post addPost(PostForm postForm) {
        String idPost = UUID.randomUUID().toString();
        Post postToAdd = new Post(idPost, postForm.getPost_content(), postForm.getTags());
        for (String idAuthor : postForm.getAuthorsPost()) {
            PostAndAuthor postAndAuthor = new PostAndAuthor(idPost, idAuthor);
            postAndAuthorManager.addPostAndAuthor(postAndAuthor);
        }
        db.add(postToAdd);
        return postToAdd;
    }

    @Override
    public List<Post> getAllPosts() {
        return db;
    }
}
