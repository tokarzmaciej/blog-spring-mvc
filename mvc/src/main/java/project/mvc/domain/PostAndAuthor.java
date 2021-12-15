package project.mvc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class PostAndAuthor {
    private String id_post;
    private String id_author;
}
