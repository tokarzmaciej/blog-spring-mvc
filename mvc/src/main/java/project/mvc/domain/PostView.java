package project.mvc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PostView {

    private String id;
    private String post_content;
    private String tags;
    private List<Attachment> attachments;
    private List<Attachment> images;
    private List<Comment> comments;
    private List<Author> authors;
}
