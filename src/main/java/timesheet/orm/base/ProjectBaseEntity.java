package timesheet.orm.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import timesheet.libs.BaseAuditedEntity;
import timesheet.orm.entity.Timetable;
import timesheet.orm.entity.Users;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@MappedSuperclass
@Table(name = "project")
@JsonInclude(JsonInclude.Include.NON_NULL)

public abstract class ProjectBaseEntity extends BaseAuditedEntity {

    protected static final long serialVersionUID = 1L;


    @Column(name = "name")
    @NotNull
    protected String name;

    @Column(name = "type")
    @NotNull
    protected String type;

    @ManyToMany(mappedBy = "projectList")
    protected List<Users> usersList;










}
