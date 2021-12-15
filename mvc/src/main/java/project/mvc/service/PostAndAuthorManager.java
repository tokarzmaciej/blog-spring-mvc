package project.mvc.service;

import project.mvc.domain.PostAndAuthor;

import java.util.List;

public interface PostAndAuthorManager {
    PostAndAuthor addPostAndAuthor(PostAndAuthor postAndAuthor);

    List<PostAndAuthor> getAllPostAndAuthor();
}
