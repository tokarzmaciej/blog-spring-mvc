package project.mvc.domain;

import lombok.Data;

@Data
public class Comment {
    private int id;
    private String username;
    private int idPost;
    private String commentContent;
}
