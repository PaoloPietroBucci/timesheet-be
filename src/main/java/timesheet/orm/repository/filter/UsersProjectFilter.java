package timesheet.orm.repository.filter;

import org.springframework.stereotype.Component;
import timesheet.libs.BaseEntity;
import timesheet.orm.entity.UsersProject;
import timesheet.orm.entity.UsersProject_;
import timesheet.util.GenericUtil;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Component
public class UsersProjectFilter {
    public List<Predicate> filter(CriteriaBuilder builder, From<? extends BaseEntity, UsersProject> from, UsersProject filter) {
        List<Predicate> predicates = new ArrayList<>();
        if (filter == null) {
            return predicates;
        }
        if (GenericUtil.isNotNullOrEmpty(filter.getId())) {
            predicates.add(builder.equal(from.get(UsersProject_.id), filter.getId()));
        }

        if (GenericUtil.isNotNullOrEmpty(filter.getProjectId())) {
            predicates.add(builder.equal(from.get(UsersProject_.projectId), filter.getProjectId()));
        }
        if (GenericUtil.isNotNullOrEmpty(filter.getUserId())) {
            predicates.add(builder.equal(from.get(UsersProject_.userId), GenericUtil.like(filter.getUserId())));
        }

        return predicates;
    }
}
