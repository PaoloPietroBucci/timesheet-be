package timesheet.orm.repository.custom;

import timesheet.orm.entity.Project;

import java.util.List;

public interface ProjectRepositoryCustom {


    Long count(Project filter);

    List<Project> findAll(Project filter);

    void softDelete(Project input);

    void softDelete(List<Project> input);

}
