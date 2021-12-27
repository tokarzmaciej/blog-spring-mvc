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
class AuthorsManagerInMemoryImpl implements AuthorManager {

    @Setter
    private List<Author> db;
    private final CommentManager commentManager;


    AuthorsManagerInMemoryImpl(@Autowired List<Author> db, CommentManager commentManager) {
        this.db = db;
        this.commentManager = commentManager;
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
    public List<Post> getAllPostsForUserName(String username, List<PostAndAuthor> postAndAuthors, List<Post> posts) {
        return postAndAuthors.stream()
                .filter(row -> Objects.equals(getAuthor(row.getId_author()).getUsername(), username))
                .map(post -> posts.stream()
                        .filter(p -> Objects.equals(p.getId(), post.getId_post()))
                        .collect(Collectors.toList()).get(0))
                .collect(Collectors.toList());
    }


    @Override
    public List<AuthorView> getAllAuthorViews(List<PostAndAuthor> postAndAuthors, List<Post> posts) {
        List<AuthorView> listAuthorViews = db.stream().map(author -> {
            String username = author.getUsername();
            String name = author.getFirst_name() + " " + author.getLast_name();
            return new AuthorView(username, name, commentManager.getCommentsForUsername(username),
                    getAllPostsForUserName(username, postAndAuthors, posts));
        }).collect(Collectors.toList());

        commentManager.getAllComments().forEach(comment -> {
            String username = comment.getUsername();
            String name = "";
            if (listAuthorViews.stream().noneMatch(author -> Objects.equals(author.getUsername(), username))) {
                listAuthorViews.add(new AuthorView(username, name, commentManager.getCommentsForUsername(username), Collections.emptyList()));
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
    public List<AuthorView> getAuthorView(String username, List<PostAndAuthor> postAndAuthors, List<Post> posts) {
        return getAllAuthorViews(postAndAuthors, posts).stream().
                filter(author -> Objects.equals(author.getUsername(), username))
                .collect(Collectors.toList());
    }

    @Override
    public List<AuthorView> getAuthorViewForSearch(String value, List<PostAndAuthor> postAndAuthors, List<Post> posts) {
        return getAllAuthorViews(postAndAuthors, posts).stream().
                filter(author -> author.getUsername().contains(value))
                .collect(Collectors.toList());
    }

    @Override
    public void writeToCsv() {
        File folder = new File("./upload-dir/export");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String filePath = "./upload-dir/export/authors.csv";
        File file = new File(filePath);
        try {
            FileWriter outputFile = new FileWriter(file);

            CSVWriter writer = new CSVWriter(outputFile);

            String[] header = {"id", "first_name", "last_name", "username"};
            writer.writeNext(header);

            db.forEach(author -> {
                String[] data = {author.getId(), author.getFirst_name(), author.getLast_name(), author.getUsername()};
                writer.writeNext(data);
            });

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void csvToBeans(String content) {
        List<Author> importedData = new ArrayList<>();

        Arrays.stream(content.split("\n")).skip(1)
                .forEach(row -> {
                    List<String> line = Arrays.stream(row.split(",")).collect(Collectors.toList());
                    Author author = new Author(line.get(0).substring(1, line.get(0).length() - 1),
                            line.get(1).substring(1, line.get(1).length() - 1),
                            line.get(2).substring(1, line.get(2).length() - 1),
                            line.get(3).substring(1, line.get(3).length() - 1));
                    importedData.add(author);
                });
        setDb(importedData);
    }
}
