package timesheet.orm.repository.custom;

import timesheet.orm.entity.Timetable;

import java.util.List;

public interface TimetableRepositoryCustom {

    Long count(Timetable filter);

    Long sum(Timetable filter);

    List<Timetable> findAll(Timetable filter);

    void softDelete(Timetable input);

    void softDelete(List<Timetable> input);
}
