package project.mvc.service;

import com.opencsv.CSVWriter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.mvc.domain.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
    public Post addPost(PostForm postForm, List<String> attachments) {
        String idPost = UUID.randomUUID().toString();
        Post postToAdd = new Post(idPost, postForm.getPost_content(), postForm.getTags());
        for (String idAuthor : postForm.getAuthorsPost()) {
            PostAndAuthor postAndAuthor = new PostAndAuthor(idPost, idAuthor);
            postAndAuthorManager.addPostAndAuthor(postAndAuthor);
        }

        addAttachmentsForPost(postForm, idPost, attachments);

        db.add(postToAdd);
        return postToAdd;
    }

    private void addAttachmentsForPost(PostForm postForm, String idPost, List<String> attachments) {
        String imageName = postForm.getImageFile().getOriginalFilename();
        String urlForImage = "http://localhost:8080/files/image/" + imageName;
        Attachment image = new Attachment(idPost, urlForImage);
        attachmentManager.addAttachment(image);

        attachments.forEach(attachment -> {
            String urlForAttachment = "http://localhost:8080/files/attachment/" + attachment;
            Attachment newAttachment = new Attachment(idPost, urlForAttachment);
            attachmentManager.addAttachment(newAttachment);
        });

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
    public List<PostView> getPostView(String idPost) {
        return getAllPostsView().stream()
                .filter(post -> Objects.equals(post.getId(), idPost))
                .collect(Collectors.toList());
    }

    @Override
    public Post editPost(String idPost, PostForm postForm,List<String> attachments) {
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

        addAttachmentsForPost(postForm, idPost,attachments);
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
                        attachmentManager.getAllImagesForPost(post.getId()),
                        commentManager.getAllCommentsForPost(post.getId()),
                        postAndAuthorManager.getAllAuthorsForPost(post.getId())
                ))
                .sorted(Comparator.comparing(PostView::getTags))
                .collect(Collectors.toList());
    }

    @Override
    public List<PostView> getAllPostsViewSortByAuthorSize() {
        return getAllPostsView()
                .stream()
                .sorted((post1, post2) -> {
                    if (post2.getAuthors().size() ==
                            post1.getAuthors().size()) {
                        return 0;
                    } else if (post2.getAuthors().size() <
                            post1.getAuthors().size()) {
                        return -1;
                    }
                    return 1;
                }).collect(Collectors.toList());

    }

    @Override
    public List<PostView> getAllPostsViewSortByCommentSize() {
        return getAllPostsView()
                .stream()
                .sorted((post1, post2) -> {
                    if (post2.getComments().size() ==
                            post1.getComments().size()) {
                        return 0;
                    } else if (post2.getComments().size() <
                            post1.getComments().size()) {
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

    @Override
    public void writeToCsv() {
        File folder = new File("./upload-dir/export");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String filePath = "./upload-dir/export/posts.csv";
        File file = new File(filePath);
        try {
            FileWriter outputFile = new FileWriter(file);

            CSVWriter writer = new CSVWriter(outputFile);

            String[] header = {"id", "post_content", "tags"};
            writer.writeNext(header);

            db.forEach(post -> {
                String[] data = {post.getId(), post.getPost_content(), post.getTags()};
                writer.writeNext(data);
            });

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void csvToBeans(String content) {
        List<Post> importedData = new ArrayList<>();

        Arrays.stream(content.split("\n")).skip(1)
                .forEach(row -> {
                    List<String> line = Arrays.stream(row.split(",")).collect(Collectors.toList());
                    Post post = new Post(line.get(0), line.get(1), line.get(2));
                    importedData.add(post);
                });
        setDb(importedData);
    }

}
