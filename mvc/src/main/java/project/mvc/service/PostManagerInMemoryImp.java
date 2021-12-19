package project.mvc.service;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.mvc.domain.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostManagerInMemoryImp implements PostManager {

    @Setter
    private List<Post> db;
    private final PostAndAuthorManager postAndAuthorManager;
    private final AttachmentManager attachmentManager;
    private final CommentManager commentManager;


    public PostManagerInMemoryImp(@Autowired List<Post> db, PostAndAuthorManager postAndAuthorManager,
                                  AttachmentManager attachmentManager, CommentManager commentManager) {
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

        addAttachmentsForPost(postForm, idPost);

        db.add(postToAdd);
        return postToAdd;
    }

    private void addAttachmentsForPost(PostForm postForm, String idPost) {
        String imageName = postForm.getImageFile().getOriginalFilename();
        String urlForImage = "http://localhost:8080/files/image/" + imageName;
        Attachment image = new Attachment(idPost, urlForImage);
        attachmentManager.addAttachment(image);

        String attachmentName = postForm.getAttachment().getOriginalFilename();
        String urlForAttachment = "http://localhost:8080/files/attachment/" + attachmentName;
        Attachment attachment = new Attachment(idPost, urlForAttachment);
        attachmentManager.addAttachment(attachment);
    }

    @Override
    public List<Post> getAllPosts() {
        return db;
    }

    @Override
    public List<Post> getPost(String idPost) {
        return db.stream()
                .filter(post -> Objects.equals(post.getId(), idPost))
                .collect(Collectors.toList());
    }

    @Override
    public Post editPost(String idPost, PostForm postForm) {
        Post postToEdit = new Post(idPost, postForm.getPost_content(), postForm.getTags());
        postAndAuthorManager.setDb(
                postAndAuthorManager
                        .getAllPostAndAuthor()
                        .stream().filter(row -> !Objects.equals(row.getId_post(), idPost))
                        .collect(Collectors.toList()));

        for (String idAuthor : postForm.getAuthorsPost()) {
            PostAndAuthor postAndAuthor = new PostAndAuthor(idPost, idAuthor);
            postAndAuthorManager.addPostAndAuthor(postAndAuthor);
        }

        attachmentManager.setDb(
                attachmentManager
                        .getAllAttachments()
                        .stream().filter(attachment -> !Objects.equals(attachment.getId_post(), idPost))
                        .collect(Collectors.toList()));

        addAttachmentsForPost(postForm, idPost);
        setDb(db.stream().map(post -> {
            if (Objects.equals(post.getId(), idPost)) {
                return postToEdit;
            } else {
                return post;
            }
        }).collect(Collectors.toList()));

        return postToEdit;
    }

    @Override
    public List<PostView> getAllPostsView() {
        return db
                .stream()
                .map(post -> new PostView(post.getId(), post.getPost_content(), post.getTags(),
                        attachmentManager.getAllAttachmentsForPost(post.getId()),
                        commentManager.getAllCommentsForPost(post.getId()),
                        postAndAuthorManager.getAllAuthorsForPost(post.getId())
                ))
                .sorted((author1, author2) -> author2.getTags().
                        compareTo(author1.getTags()))
                .collect(Collectors.toList());
    }

    @Override
    public List<PostView> getAllPostsViewSortByAuthorSize() {
        return getAllPostsView()
                .stream()
                .sorted((author1, author2) -> {
                    if (author1.getAuthors().size() ==
                            author2.getAuthors().size()) {
                        return 0;
                    } else if (author1.getAuthors().size() <
                            author2.getAuthors().size()) {
                        return -1;
                    }
                    return 1;
                }).collect(Collectors.toList());

    }

    @Override
    public List<PostView> getAllPostsViewSortByCommentSize() {
        return getAllPostsView()
                .stream()
                .sorted((author1, author2) -> {
                    if (author1.getComments().size() ==
                            author2.getComments().size()) {
                        return 0;
                    } else if (author1.getComments().size() <
                            author2.getComments().size()) {
                        return -1;
                    }
                    return 1;
                }).collect(Collectors.toList());
    }

    @Override
    public List<PostView> getAllPostsForSearch(String value) {
        return getAllPostsView()
                .stream()
                .filter(post -> post.getTags().contains(value) || post.getPost_content().contains(value))
                .collect(Collectors.toList());
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
