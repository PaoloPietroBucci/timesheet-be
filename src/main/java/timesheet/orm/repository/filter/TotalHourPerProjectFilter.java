package timesheet.orm.repository.filter;

import org.springframework.stereotype.Component;
import timesheet.libs.BaseEntity;
import timesheet.orm.view.TotalHourPerProject;
import timesheet.orm.view.TotalHourPerProject_;
import timesheet.util.GenericUtil;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Component
public class TotalHourPerProjectFilter {


    public List<Predicate> filter(CriteriaBuilder builder, Root<TotalHourPerProject> from, TotalHourPerProject filter) {
        List<Predicate> predicates = new ArrayList<>();
        if (filter == null) {
            return predicates;
        }
        if (GenericUtil.isNotNullOrEmpty(filter.getUuid())) {
            predicates.add(builder.equal(from.get(TotalHourPerProject_.uuid), filter.getUuid()));
        }

        if (GenericUtil.isNotNullOrEmpty(filter.getProjectName())) {
            predicates.add(builder.equal(from.get(TotalHourPerProject_.projectName), filter.getProjectName()));
        }
        if (GenericUtil.isNotNullOrEmpty(filter.getSum())) {
            predicates.add(builder.equal(from.get(TotalHourPerProject_.sum), filter.getSum()));
        }

        return predicates;
    }
}
