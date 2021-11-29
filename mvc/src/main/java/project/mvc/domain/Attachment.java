package project.mvc.domain;

import lombok.Data;

@Data
public class Attachment {
    private int id;
    private int idPost;
    private String filename;
}
