package project.mvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.mvc.domain.Post;

import java.util.List;
import java.util.UUID;

@Service
public class PostManagerInMemoryImp implements PostManager {


    @Autowired List<Post> db;

    @Override
    public Post addPost(Post post) {
        Post postToAdd = new Post(UUID.randomUUID().toString(), post.getPost_content(), post.getTags());
        db.add(postToAdd);
        return postToAdd;
    }

    @Override
    public List<Post> getAllPosts() {
        return db;
    }
}
