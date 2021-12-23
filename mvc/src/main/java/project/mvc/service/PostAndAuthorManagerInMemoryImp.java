package project.mvc.service;

import com.opencsv.CSVWriter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.mvc.domain.Author;
import project.mvc.domain.PostAndAuthor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PostAndAuthorManagerInMemoryImp implements PostAndAuthorManager {

    @Setter
    private List<PostAndAuthor> db;
    private final AuthorManager authorManager;

    public PostAndAuthorManagerInMemoryImp(AuthorManager authorManager, @Autowired List<PostAndAuthor> db) {
        this.db = db;
        this.authorManager = authorManager;
    }

    @Override
    public PostAndAuthor addPostAndAuthor(PostAndAuthor postAndAuthor) {
        db.add(postAndAuthor);
        return postAndAuthor;
    }

    @Override
    public List<PostAndAuthor> getAllPostAndAuthor() {
        return db;
    }

    @Override
    public void deletePostAndAuthorByIdPost(String idPost) {
        setDb(db.stream().filter(row -> !Objects.equals(row.getId_post(), idPost))
                .collect(Collectors.toList()));
    }

    @Override
    public List<String> getIdAuthorsForPost(String idPost) {
        return db.stream().filter(row -> Objects.equals(row.getId_post(), idPost))
                .map(PostAndAuthor::getId_author).collect(Collectors.toList());
    }


    @Override
    public List<Author> getAllAuthorsForPost(String idPost) {
        return db.stream().filter(row -> Objects.equals(row.getId_post(), idPost))
                .map(author -> authorManager.getAuthor(author.getId_author()))
                .collect(Collectors.toList());
    }

    @Override
    public void writeToCsv() {
        File folder = new File("./upload-dir/export");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String filePath = "./upload-dir/export/posts_authors.csv";
        File file = new File(filePath);
        try {
            FileWriter outputFile = new FileWriter(file);

            CSVWriter writer = new CSVWriter(outputFile);

            String[] header = {"id_post", "id_author"};
            writer.writeNext(header);

            db.forEach(row -> {
                String[] data = {row.getId_post(), row.getId_author()};
                writer.writeNext(data);
            });

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void csvToBeans(String content) {
        List<PostAndAuthor> importedData = new ArrayList<>();

        Arrays.stream(content.split("\n")).skip(1)
                .forEach(row -> {
                    List<String> line = Arrays.stream(row.split(",")).collect(Collectors.toList());
                    PostAndAuthor postAndAuthor = new PostAndAuthor(line.get(0), line.get(1));
                    importedData.add(postAndAuthor);
                });
        setDb(importedData);
    }

}
