package timesheet.orm.entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Where;
import timesheet.orm.base.UsersProjectBaseEntity;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Entity(name = "UsersProject")
@Table(name = "user_project")
@DynamicInsert
@Where(clause = "deleted = false")

public class UsersProject extends UsersProjectBaseEntity {

    private static final long serialVersionUID = 1L;

    @Override
    public UsersProject clean() {
        return this;
    }

    @Override
    public UsersProject lite() {
        return  this;
    }
}
