package project.mvc.service;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.mvc.domain.Author;
import project.mvc.domain.AuthorView;
import project.mvc.domain.Post;
import project.mvc.domain.PostAndAuthor;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
class AuthorsManagerInMemoryImpl implements AuthorManager {

    @Setter
    private List<Author> db;
    private final CommentManager commentManager;
    private final List<PostAndAuthor> listPostAndAuthors;
    private final List<Post> listPosts;

    AuthorsManagerInMemoryImpl(@Autowired List<Author> db, CommentManager commentManager,
                               @Autowired List<PostAndAuthor> listPostAndAuthors, @Autowired List<Post> listPosts) {
        this.db = db;
        this.commentManager = commentManager;
        this.listPosts = listPosts;
        this.listPostAndAuthors = listPostAndAuthors;
    }

    @Override
    public List<Author> getAllAuthors() {
        return db;
    }

    @Override
    public Author getAuthor(String idAuthor) {
        return db.stream().filter(author -> Objects.equals(author.getId(), idAuthor))
                .collect(Collectors.toList()).get(0);
    }

    @Override
    public List<Post> getAllPostsForUserName(String username) {


        return listPostAndAuthors.stream()
                .filter(row -> Objects.equals(getAuthor(row.getId_author()).getUsername(), username))
                .map(post -> listPosts.stream()
                        .filter(p -> Objects.equals(p.getId(), post.getId_post()))
                        .collect(Collectors.toList()).get(0))
                .collect(Collectors.toList());
    }


    @Override
    public List<AuthorView> getAllAuthorViews() {
        List<AuthorView> listAuthorViews = db.stream().map(author -> {
            String username = author.getUsername();
            return new AuthorView(username, commentManager.getCommentsForUsername(username),
                    getAllPostsForUserName(username));
        }).collect(Collectors.toList());

        commentManager.getAllComments().forEach(comment -> {
            String username = comment.getUsername();
            if (listAuthorViews.stream().noneMatch(author -> Objects.equals(author.getUsername(), username))) {
                listAuthorViews.add(new AuthorView(username, commentManager.getCommentsForUsername(username), Collections.emptyList()));
            }
        });

        return listAuthorViews.stream()
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
    public List<AuthorView> getAuthorView(String username) {
        return getAllAuthorViews().stream().
                filter(author -> Objects.equals(author.getUsername(), username))
                .collect(Collectors.toList());
    }

    @Override
    public List<AuthorView> getAuthorViewForSearch(String value) {
        return getAllAuthorViews().stream().
                filter(author -> author.getUsername().contains(value))
                .collect(Collectors.toList());
    }

}
