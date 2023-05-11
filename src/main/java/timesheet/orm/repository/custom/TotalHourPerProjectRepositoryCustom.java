package timesheet.orm.repository.custom;

import timesheet.orm.view.HourUserProject;
import timesheet.orm.view.TotalHourPerProject;

import java.util.List;

public interface TotalHourPerProjectRepositoryCustom {

    Long count(TotalHourPerProject filter);


    List<TotalHourPerProject> findAll(TotalHourPerProject filter);

    void softDelete(TotalHourPerProject input);

    void softDelete(List<TotalHourPerProject> input);
}
