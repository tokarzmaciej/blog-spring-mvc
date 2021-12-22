
package project.mvc.service;

import project.mvc.domain.Author;
import project.mvc.domain.AuthorView;
import project.mvc.domain.Post;

import java.util.List;

public interface AuthorManager {

    List<Author> getAllAuthors();

    Author getAuthor(String idAuthor);

    List<AuthorView> getAllAuthorViews();

    List<Post> getAllPostsForUserName(String username);

    List<AuthorView> getAuthorView(String username);

    List<AuthorView> getAuthorViewForSearch(String value);

    void writeToCsv();
}