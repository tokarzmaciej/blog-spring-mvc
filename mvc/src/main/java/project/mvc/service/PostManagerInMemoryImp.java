package project.mvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.mvc.domain.Attachment;
import project.mvc.domain.Post;
import project.mvc.domain.PostAndAuthor;
import project.mvc.domain.PostForm;

import java.util.List;
import java.util.UUID;

@Service
public class PostManagerInMemoryImp implements PostManager {

    private final PostAndAuthorManager postAndAuthorManager;
    private final AttachmentManager attachmentManager;
    @Autowired
    List<Post> db;

    public PostManagerInMemoryImp(PostAndAuthorManager postAndAuthorManager, AttachmentManager attachmentManager) {
        this.postAndAuthorManager = postAndAuthorManager;
        this.attachmentManager = attachmentManager;
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
}
