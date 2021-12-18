package project.mvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.mvc.domain.Attachment;
import project.mvc.domain.Author;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
class AuthorsManagerInMemoryImpl implements AuthorManager {


    @Autowired
    List<Author> db;

    @Override
    public Author addAuthor(Author author) {
        Author authorToAdd = new Author(author.getId(), author.getFirst_name(), author.getLast_name(), author.getUsername());
        db.add(authorToAdd);
        return authorToAdd;
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

}
