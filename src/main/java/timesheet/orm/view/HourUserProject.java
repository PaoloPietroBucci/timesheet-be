package timesheet.orm.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;
import timesheet.libs.models.Pagination;

import javax.persistence.*;

@Entity(name = "HourUserProject")
@Table(name = "hour_user_project")
@Immutable
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)

public class HourUserProject {

        @Id
        String uuid;

        @Column(name = "user_name")
        String userName;
        @Column(name = "user_surname")
        String userSurname;
        @Column(name = "project_name")
        String projectName;
        @Column(name = "sum")
        Double sum;

        @Transient
        protected Pagination pagination;

        public HourUserProject clean() {
            return null;
        }

        public <T> T lite() {
            return null;
        }
}
