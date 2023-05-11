package timesheet;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import timesheet.libs.models.Pagination;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaginationResponse implements Serializable {

    private Long totalElements;
    private List<?> list;
    private Pagination pagination;
    private Object order;
    private Boolean alternative;
    private Boolean allClosed;

    public PaginationResponse(Long totalElements, List<?> list, Pagination pagination, Object order) {
        this.totalElements = totalElements;
        this.list = list;
        this.pagination = pagination;
        this.order = order;
    }

    public PaginationResponse(Long number) {
    }
}
