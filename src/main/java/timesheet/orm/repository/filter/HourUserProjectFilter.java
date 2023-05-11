package timesheet.orm.repository.filter;

import org.springframework.stereotype.Component;
import timesheet.orm.view.HourUserProject;
import timesheet.orm.view.HourUserProject_;
import timesheet.orm.view.TotalHourPerProject;
import timesheet.orm.view.TotalHourPerProject_;
import timesheet.util.GenericUtil;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Component
public class HourUserProjectFilter {

    public List<Predicate> filter(CriteriaBuilder builder, Root<HourUserProject> from, HourUserProject filter) {
        List<Predicate> predicates = new ArrayList<>();
        if (filter == null) {
            return predicates;
        }
        if (GenericUtil.isNotNullOrEmpty(filter.getUuid())) {
            predicates.add(builder.equal(from.get(HourUserProject_.uuid), filter.getUuid()));
        }

        if (GenericUtil.isNotNullOrEmpty(filter.getProjectName())) {
            predicates.add(builder.equal(from.get(HourUserProject_.projectName), filter.getProjectName()));
        }

        if (GenericUtil.isNotNullOrEmpty(filter.getUserName())) {
            predicates.add(builder.equal(from.get(HourUserProject_.userName), filter.getUserName()));
        }

        if (GenericUtil.isNotNullOrEmpty(filter.getUserSurname())) {
            predicates.add(builder.equal(from.get(HourUserProject_.userSurname), filter.getUserSurname()));
        }

        if (GenericUtil.isNotNullOrEmpty(filter.getSum())) {
            predicates.add(builder.equal(from.get(HourUserProject_.sum), filter.getSum()));
        }

        return predicates;
    }
}
