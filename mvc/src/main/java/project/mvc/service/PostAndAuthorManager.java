package project.mvc.service;

import project.mvc.domain.Author;
import project.mvc.domain.PostAndAuthor;

import java.util.List;

public interface PostAndAuthorManager {
    void addPostAndAuthor(PostAndAuthor postAndAuthor);

    List<PostAndAuthor> getAllPostAndAuthor();

    void deletePostAndAuthorByIdPost(String idPost);

    void setDb(List<PostAndAuthor> db);

    List<String> getIdAuthorsForPost(String idPost);

    List<Author> getAllAuthorsForPost(String idPost);

    void writeToCsv();

    void csvToBeans(String content);

}
