package project.mvc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Author {
    private String id;
    private String first_name;
    private String last_name;
    private String username;

}
