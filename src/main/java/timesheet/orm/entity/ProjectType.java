package timesheet.orm.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Where;
import timesheet.orm.base.ProjectTypeBaseEntity;

import javax.persistence.*;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Entity(name = "ProjectType")
@Table(name = "project_type")
@DynamicInsert
@Where(clause = "deleted = false")

public class ProjectType extends ProjectTypeBaseEntity {

    private static final long serialVersionUID = 1L;

    @Override
    public ProjectType clean() {
        return this;
    }

    @Override
    public ProjectType lite() {
        return (ProjectType) this;
    }
}
