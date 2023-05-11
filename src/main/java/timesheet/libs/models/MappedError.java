package timesheet.libs.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MappedError implements Serializable {

    private static final long serialVersionUID = 1L;

    private String errorKey;
    private String field;
    private String value;
    private List<String> messages;

    public MappedError(String errorKey) {
        this.errorKey = errorKey;
    }
}
