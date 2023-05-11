package timesheet.libs.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Translation {

    private String answer;
    private String content;
    private String description;
    private String language;
    private String link;
    private String name;
    private String question;
    private String subtitle;
    private String title;
    private String tutorial;
    private String value;

}
