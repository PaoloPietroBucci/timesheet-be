package timesheet.orm.repository.filter;

import org.springframework.stereotype.Component;
import timesheet.libs.BaseEntity;
import timesheet.orm.entity.ProjectType_;
import timesheet.orm.entity.Timetable;
import timesheet.orm.entity.Timetable_;
import timesheet.util.GenericUtil;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
@Component
public class TimetableFilter {

    public List<Predicate> filter(CriteriaBuilder builder, From<? extends BaseEntity, Timetable> from, Timetable filter) {
        List<Predicate> predicates = new ArrayList<>();
        if (filter == null) {
            return predicates;
        }
        if (GenericUtil.isNotNullOrEmpty(filter.getId())) {
            predicates.add(builder.equal(from.get(Timetable_.id), filter.getId()));
        }
        if (GenericUtil.isNotNullOrEmpty(filter.getUserId())) {
            predicates.add(builder.equal(from.get(Timetable_.userId), filter.getUserId()));
        }
        if (GenericUtil.isNotNullOrEmpty(filter.getProjectId())) {
            predicates.add(builder.equal(from.get(Timetable_.projectId), filter.getProjectId()));
        }
        if (GenericUtil.isNotNullOrEmpty(filter.getDate())) {
            predicates.add(builder.equal(from.get(Timetable_.date), filter.getDate()));
        }

        if (GenericUtil.isNotNullOrEmpty(filter.getDuration())) {
            predicates.add(builder.equal(from.get(Timetable_.duration), filter.getDuration()));
        }
        if (GenericUtil.isNotNullOrEmpty(filter.getDateFrom())) {
            predicates.add(builder.greaterThanOrEqualTo(from.get(Timetable_.date), filter.getDateFrom()));
        }
        if (GenericUtil.isNotNullOrEmpty(filter.getDateTo())) {
            predicates.add(builder.lessThanOrEqualTo(from.get(Timetable_.date), filter.getDateTo()));
        }



        return predicates;
    }
}
