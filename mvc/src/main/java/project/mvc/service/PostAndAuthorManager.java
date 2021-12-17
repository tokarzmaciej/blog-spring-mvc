package project.mvc.service;

import project.mvc.domain.PostAndAuthor;

import java.util.List;

public interface PostAndAuthorManager {
    PostAndAuthor addPostAndAuthor(PostAndAuthor postAndAuthor);

    List<PostAndAuthor> getAllPostAndAuthor();

    void deletePostAndAuthorByIdPost(String idPost);

    void setDb(List<PostAndAuthor> db);

    List<String> getIdAuthorsForPost(String idPost);

}
