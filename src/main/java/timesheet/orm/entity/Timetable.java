package timesheet.orm.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Where;
import timesheet.orm.base.TimetableBaseEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Entity(name = "Timetable")
@Table(name = "timetable")
@DynamicInsert
@Where(clause = "deleted = false")

public class Timetable extends TimetableBaseEntity {

    private static final long serialVersionUID = 1L;

    @Transient
    private LocalDate dateFrom;

    @Transient
    private LocalDate dateTo;

    @Override
    public Timetable clean() {

        Timetable out = new Timetable();
        out.setId(this.id);
        out.setDate(this.date);
        out.setDuration(this.duration);
        out.setUsers(this.users.lite());
        out.setProject(this.project.lite());
        return out;
    }

    @Override
    public Timetable lite() {

        Timetable out = new Timetable();
        out.setId(this.id);
        out.setDate(this.date);
        out.setDuration(this.duration);
        out.setUsers(this.users.lite());
        out.setProject(this.project.lite());

        return out;

    }




}
