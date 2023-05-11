package timesheet.orm.repository;

import timesheet.orm.entity.ProjectType;
import timesheet.orm.repository.custom.ProjectTypeRepositoryCustom;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProjectTypeRepository extends PagingAndSortingRepository<ProjectType, Long>, ProjectTypeRepositoryCustom {
}
