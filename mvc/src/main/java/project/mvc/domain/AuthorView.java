package project.mvc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AuthorView {
    private String username;
    private List<Comment> comments;
    private List<Post> posts;

}
