package timesheet.orm.repository.custom;

import timesheet.orm.entity.Users;

import java.util.List;
import java.util.Optional;

public interface UsersRepositoryCustum {

    Long count(Users filter);

    List<Users> findAll(Users filter);

    void softDelete(Users input);

    void softDelete(List<Users> input);
    Optional<Users> findByUsername(String username);



}
