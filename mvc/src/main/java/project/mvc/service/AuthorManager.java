package project.mvc.service;

import project.mvc.domain.Author;

import java.util.List;

public interface AuthorManager {
    Author addAuthor(Author author);
    List<Author> getAllAuthors();

}
