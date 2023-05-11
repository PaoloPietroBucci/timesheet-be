package timesheet.orm.view;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;
import timesheet.libs.BaseEntity;
import timesheet.libs.models.Pagination;
import timesheet.orm.entity.Timetable;

import javax.persistence.*;

@Entity(name = "TotalHourPerProject")
@Table(name = "total_hour_perproject")
@Immutable
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TotalHourPerProject  {

    @Id
    String uuid;

    @Column(name = "project_name")
    String projectName;
    @Column(name = "sum")
    Double sum;

    @Transient
    protected Pagination pagination;

    public TotalHourPerProject clean() {
        TotalHourPerProject out = new TotalHourPerProject();
        out.setSum(this.getSum());
        out.setProjectName(this.projectName);
        return out;
    }

    public <T> T lite() {
        return null;
    }
}
