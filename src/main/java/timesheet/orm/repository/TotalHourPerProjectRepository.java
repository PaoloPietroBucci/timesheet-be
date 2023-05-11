package timesheet.orm.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import timesheet.orm.repository.custom.TotalHourPerProjectRepositoryCustom;
import timesheet.orm.view.TotalHourPerProject;

public interface TotalHourPerProjectRepository extends PagingAndSortingRepository<TotalHourPerProject, Long>, TotalHourPerProjectRepositoryCustom {
}
