package timesheet.orm.repository;

import timesheet.orm.entity.Project;
import timesheet.orm.repository.custom.ProjectRepositoryCustom;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


public interface ProjectRepository extends PagingAndSortingRepository<Project, Long>, ProjectRepositoryCustom {
}
