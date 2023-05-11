package timesheet.libs.models;

import java.io.Serializable;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Pagination implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer page;
    private Integer elementsPerPage;
}
