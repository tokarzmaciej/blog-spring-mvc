package project.mvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.mvc.domain.Author;

import java.util.List;
import java.util.UUID;


@Service
class AuthorManagerInMemoryImpl implements AuthorManager {


    @Autowired List<Author> db;

    @Override
    public Author addAuthor(Author author) {
        Author authorToAdd = new Author(UUID.randomUUID().toString(), author.getFirst_name(), author.getLast_name(), author.getUsername());
        db.add(authorToAdd);
        return authorToAdd;
    }

    @Override
    public List<Author> getAllAuthors() {
        return db;
    }

}
