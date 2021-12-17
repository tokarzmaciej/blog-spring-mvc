package project.mvc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import project.mvc.validators.IsImageFile;
import project.mvc.validators.IsNotEmpty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostForm {
    private String id;

    @Size(min = 1, message = "You must select at least one author")
    private List<String> authorsPost;
    @NotBlank(message = "Content is required")
    @Size(min = 10, message = "Content should be start at least ten characters")
    private String post_content;
    @NotBlank(message = "Tags is required")
    private String tags;
    @IsImageFile
    @IsNotEmpty
    private MultipartFile imageFile;
    @IsNotEmpty
    private MultipartFile attachment;

}
