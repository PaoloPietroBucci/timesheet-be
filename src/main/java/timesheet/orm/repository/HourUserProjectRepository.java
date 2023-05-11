package timesheet.orm.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import timesheet.orm.repository.custom.HourUserProjectRepositoryCustom;
import timesheet.orm.repository.custom.TotalHourPerProjectRepositoryCustom;
import timesheet.orm.view.TotalHourPerProject;

public interface HourUserProjectRepository extends PagingAndSortingRepository<TotalHourPerProject, Long>, HourUserProjectRepositoryCustom {
}
