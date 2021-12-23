package project.mvc.service;

import com.opencsv.CSVWriter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.mvc.domain.Comment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommentManagerInMemoryImp implements CommentManager {

    @Setter
    private List<Comment> db;

    public CommentManagerInMemoryImp(@Autowired List<Comment> db) {
        this.db = db;
    }

    @Override
    public Comment addComment(Comment comment, String idPost) {
        String idComment = UUID.randomUUID().toString();
        Comment createdComment = new Comment(idComment, comment.getUsername(), idPost, comment.getComment_content());
        db.add(createdComment);
        return createdComment;
    }

    @Override
    public List<Comment> getAllComments() {
        return db;
    }

    @Override
    public void deleteCommentByIdPost(String idPost) {
        setDb(db.stream().filter(comment -> !Objects.equals(comment.getId_post(), idPost))
                .collect(Collectors.toList()));
    }

    @Override
    public List<Comment> getAllCommentsForPost(String idPost) {
        return db.stream().filter(comment -> Objects.equals(comment.getId_post(), idPost))
                .collect(Collectors.toList());
    }

    @Override
    public List<Comment> getComment(String idComment) {
        return db.stream()
                .filter(comment -> Objects.equals(comment.getId(), idComment))
                .collect(Collectors.toList());
    }

    @Override
    public Boolean deleteCommentByIdComment(String idComment) {
        if (getComment(idComment).size() == 1) {
            setDb(db.stream()
                    .filter(comment -> !Objects.equals(comment.getId(), idComment))
                    .collect(Collectors.toList()));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Comment editComment(String idComment, Comment comment) {
        setDb(db.stream().map(com -> {
            if (Objects.equals(com.getId(), idComment)) {
                return new Comment(idComment, comment.getUsername(), com.getId_post(), comment.getComment_content());
            } else {
                return com;
            }
        }).collect(Collectors.toList()));
        return getComment(idComment).get(0);
    }

    @Override
    public List<Comment> getCommentsForUsername(String username) {
        return db.stream()
                .filter(comment -> Objects.equals(comment.getUsername(), username))
                .collect(Collectors.toList());
    }

    @Override
    public void writeToCsv() {
        File folder = new File("./upload-dir/export");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String filePath = "./upload-dir/export/comments.csv";
        File file = new File(filePath);
        try {
            FileWriter outputFile = new FileWriter(file);

            CSVWriter writer = new CSVWriter(outputFile);

            String[] header = {"id", "username", "id_post", "comment_content"};
            writer.writeNext(header);

            db.forEach(comment -> {
                String[] data = {comment.getId(), comment.getUsername(), comment.getId_post(), comment.getComment_content()};
                writer.writeNext(data);
            });

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void csvToBeans(String content) {
        List<Comment> importedData = new ArrayList<>();

        Arrays.stream(content.split("\n")).skip(1)
                .forEach(row -> {
                    List<String> line = Arrays.stream(row.split(",")).collect(Collectors.toList());
                    Comment comment = new Comment(line.get(0), line.get(1), line.get(2), line.get(3));
                    importedData.add(comment);
                });
        setDb(importedData);
    }
}
