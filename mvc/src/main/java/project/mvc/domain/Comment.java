package project.mvc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Comment {
    private int id;
    private String username;
    private int id_post;
    private String comment_content;
}
