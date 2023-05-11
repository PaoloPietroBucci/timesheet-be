package timesheet.orm.repository.filter;

import org.springframework.stereotype.Component;
import timesheet.libs.BaseEntity;
import timesheet.orm.entity.Project;
import timesheet.orm.entity.Project_;
import timesheet.util.GenericUtil;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProjectFilter {
    public List<Predicate> filter(CriteriaBuilder builder, From<? extends BaseEntity, Project> from, Project filter) {
        List<Predicate> predicates = new ArrayList<>();
        if (filter == null) {
            return predicates;
        }
        if (GenericUtil.isNotNullOrEmpty(filter.getId())) {
            predicates.add(builder.equal(from.get(Project_.id), filter.getId()));
        }

        if (GenericUtil.isNotNullOrEmpty(filter.getName())) {
            predicates.add(builder.like(from.get(Project_.name), GenericUtil.like(filter.getName().toUpperCase())));
        }
        if (GenericUtil.isNotNullOrEmpty(filter.getType())) {
            predicates.add(builder.like(from.get(Project_.type), GenericUtil.like(filter.getType().toUpperCase())));
        }

        return predicates;
    }
}
