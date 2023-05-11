package timesheet.orm.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import timesheet.libs.BaseAuditedEntity;
import timesheet.orm.entity.Project;
import timesheet.orm.entity.Users;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@MappedSuperclass
@Table(name = "timetable")
@JsonInclude(JsonInclude.Include.NON_NULL)

public abstract class TimetableBaseEntity extends BaseAuditedEntity {
    protected static final long serialVersionUID = 1L;

    @Column(name = "user_id")
    @NotNull
    protected Long userId;
    @Column(name = "project_id")
    @NotNull
    protected Long projectId;

    @Column(name = "date")
    @NotNull
    protected LocalDate date;
    @Column(name = "duration")
    @NotNull
    protected Long duration;

    @OneToOne
    @JoinColumn(name= "project_id",insertable = false, updatable = false)
    protected Project project;

    @OneToOne
    @JoinColumn(name= "user_id",insertable = false, updatable = false)
     protected Users users;







}
