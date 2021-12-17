package project.mvc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Comment {
    private String id;
    private String username;
    private String id_post;
    private String comment_content;
}
