package timesheet.orm.repository.filter;

import org.springframework.stereotype.Component;
import timesheet.libs.BaseEntity;
import timesheet.orm.entity.ProjectType_;
import timesheet.orm.entity.Users;
import timesheet.orm.entity.Users_;
import timesheet.util.GenericUtil;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Component
public class UsersFilter {

    public List<Predicate> filter(CriteriaBuilder builder, From<? extends BaseEntity, Users> from, Users filter) {
        List<Predicate> predicates = new ArrayList<>();
        if (filter == null) {
            return predicates;
        }
        if (GenericUtil.isNotNullOrEmpty(filter.getId())) {
            predicates.add(builder.equal(from.get(Users_.id), filter.getId()));
        }

        if (GenericUtil.isNotNullOrEmpty(filter.getName())) {
            predicates.add(builder.like(from.get(Users_.name), GenericUtil.like(filter.getName().toUpperCase())));
        }
        if (GenericUtil.isNotNullOrEmpty(filter.getSurname())) {
            predicates.add(builder.like(from.get(Users_.surname), GenericUtil.like(filter.getSurname().toUpperCase())));
        }
        if (GenericUtil.isNotNullOrEmpty(filter.getMail())) {
            predicates.add(builder.like(from.get(Users_.mail), GenericUtil.like(filter.getMail().toUpperCase())));
        }
        if (GenericUtil.isNotNullOrEmpty(filter.getRole())) {
            predicates.add(builder.equal(from.get(Users_.role), filter.getRole()));
        }
        if (GenericUtil.isNotNullOrEmpty(filter.getPassword())) {
            predicates.add(builder.like(from.get(Users_.password), GenericUtil.like(filter.getPassword().toUpperCase())));
        }
        if (GenericUtil.isNotNullOrEmpty(filter.getInfo())) {
            predicates.add(builder.like(from.get(Users_.info), GenericUtil.like(filter.getInfo().toUpperCase())));
        }

        return predicates;
    }

}
