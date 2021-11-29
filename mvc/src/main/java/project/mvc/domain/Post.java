package project.mvc.domain;

import lombok.Data;

@Data
public class Post {
    private int id;
    private String postContent;
    private String tags;
}
