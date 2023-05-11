package timesheet.orm.repository.custom;

import timesheet.orm.entity.ProjectType;

import java.util.List;

public interface ProjectTypeRepositoryCustom {


    Long count(ProjectType filter);

    List<ProjectType> findAll(ProjectType filter);

    void softDelete(ProjectType input);

    void softDelete(List<ProjectType> input);


}
