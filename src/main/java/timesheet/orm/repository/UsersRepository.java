package timesheet.orm.repository;

import timesheet.orm.entity.Users;
import timesheet.orm.repository.custom.UsersRepositoryCustum;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UsersRepository extends PagingAndSortingRepository<Users, Long>, UsersRepositoryCustum {
}
