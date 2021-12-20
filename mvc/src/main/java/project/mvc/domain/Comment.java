package project.mvc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private String id;
    @NotBlank(message = "Username is required")
    private String username;
    private String id_post;
    @NotBlank(message = "Content is required")
    private String comment_content;
}
