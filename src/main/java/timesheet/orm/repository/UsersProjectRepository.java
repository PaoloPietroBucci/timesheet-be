package timesheet.orm.repository;

import timesheet.orm.entity.UsersProject;
import timesheet.orm.repository.custom.UsersProjectRepositoryCustom;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UsersProjectRepository extends PagingAndSortingRepository<UsersProject, Long>, UsersProjectRepositoryCustom {

}
