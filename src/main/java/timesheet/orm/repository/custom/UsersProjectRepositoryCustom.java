package timesheet.orm.repository.custom;

import timesheet.orm.entity.UsersProject;

import java.util.List;

public interface UsersProjectRepositoryCustom {

    Long count(UsersProject filter);

    List<UsersProject> findAll(UsersProject filter);

    void softDelete(UsersProject input);

    void softDelete(List<UsersProject> input);

}
