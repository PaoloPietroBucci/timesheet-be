package timesheet.orm.repository.custom;

import timesheet.orm.view.HourUserProject;

import java.util.List;

public interface HourUserProjectRepositoryCustom {

    Long count(HourUserProject filter);


    List<HourUserProject> findAll(HourUserProject filter);

    void softDelete(HourUserProject input);

    void softDelete(List<HourUserProject> input);
}
