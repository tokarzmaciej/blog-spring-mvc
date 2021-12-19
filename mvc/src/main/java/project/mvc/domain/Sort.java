package project.mvc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Sort {
    private Boolean isSortAlpUp;
    private Boolean isSortAlpDown;
    private Boolean isSortAutUp;
    private Boolean isSortAutDown;
    private Boolean isSortComUp;
    private Boolean isSortComDown;
}
