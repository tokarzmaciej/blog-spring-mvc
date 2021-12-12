package project.mvc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Post {
    private String id;
    private String post_content;
    private String tags;
}
