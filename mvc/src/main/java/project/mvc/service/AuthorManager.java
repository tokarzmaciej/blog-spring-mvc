
package project.mvc.service;

import project.mvc.domain.Author;
import project.mvc.domain.AuthorView;
import project.mvc.domain.Post;
import project.mvc.domain.PostAndAuthor;

import java.util.List;

public interface AuthorManager {

    List<Author> getAllAuthors();

    Author getAuthor(String idAuthor);

    List<AuthorView> getAllAuthorViews(List<PostAndAuthor> postAndAuthors, List<Post> posts);

    List<Post> getAllPostsForUserName(String username,List<PostAndAuthor> postAndAuthors,List<Post> posts);

    List<AuthorView> getAuthorView(String username,List<PostAndAuthor> postAndAuthors,List<Post> posts);

    List<AuthorView> getAuthorViewForSearch(String value,List<PostAndAuthor> postAndAuthors,List<Post> posts);

    void writeToCsv();

    void csvToBeans(String content);

}