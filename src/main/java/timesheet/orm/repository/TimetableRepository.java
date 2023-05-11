package timesheet.orm.repository;

import timesheet.orm.entity.Timetable;
import timesheet.orm.repository.custom.TimetableRepositoryCustom;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface TimetableRepository extends PagingAndSortingRepository<Timetable, Long>, TimetableRepositoryCustom {
}
