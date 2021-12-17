package project.mvc.service;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.mvc.domain.Attachment;
import project.mvc.domain.Post;
import project.mvc.domain.PostAndAuthor;
import project.mvc.domain.PostForm;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostManagerInMemoryImp implements PostManager {

    @Setter
    private List<Post> db;
    private final PostAndAuthorManager postAndAuthorManager;
    private final AttachmentManager attachmentManager;
    private final CommentManager commentManager;


    public PostManagerInMemoryImp(@Autowired List<Post> db, PostAndAuthorManager postAndAuthorManager, AttachmentManager attachmentManager, CommentManager commentManager) {
        this.postAndAuthorManager = postAndAuthorManager;
        this.attachmentManager = attachmentManager;
        this.commentManager = commentManager;
        this.db = db;
    }

    @Override
    public Post addPost(PostForm postForm) {
        String idPost = UUID.randomUUID().toString();
        Post postToAdd = new Post(idPost, postForm.getPost_content(), postForm.getTags());
        for (String idAuthor : postForm.getAuthorsPost()) {
            PostAndAuthor postAndAuthor = new PostAndAuthor(idPost, idAuthor);
            postAndAuthorManager.addPostAndAuthor(postAndAuthor);
        }

        String imageName = postForm.getImageFile().getOriginalFilename();
        String urlForImage = "http://localhost:8080/files/image/" + imageName;
        Attachment image = new Attachment(idPost, urlForImage);
        attachmentManager.addAttachment(image);

        String attachmentName = postForm.getAttachment().getOriginalFilename();
        String urlForAttachment = "http://localhost:8080/files/attachment/" + attachmentName;
        Attachment attachment = new Attachment(idPost, urlForAttachment);
        attachmentManager.addAttachment(attachment);

        db.add(postToAdd);
        return postToAdd;
    }

    @Override
    public List<Post> getAllPosts() {
        return db;
    }

    @Override
    public List<Post> getPost(String idPost) {
        return db.stream().filter(post -> Objects.equals(post.getId(), idPost)).collect(Collectors.toList());
    }

    @Override
    public Boolean deletePost(String idPost) {
        if (getPost(idPost).size() == 1) {
            setDb(db.stream().filter(post -> !Objects.equals(post.getId(), idPost)).collect(Collectors.toList()));
            postAndAuthorManager.deletePostAndAuthorByIdPost(idPost);
            attachmentManager.deleteAttachmentByIdPost(idPost);
            commentManager.deleteCommentByIdPost(idPost);
            return true;
        } else {
            return false;
        }

    }
}
