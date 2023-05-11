package timesheet.orm.repository.impl;
import timesheet.libs.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import timesheet.orm.entity.Project;
import timesheet.orm.entity.Timetable;
import timesheet.orm.entity.Timetable_;
import timesheet.orm.repository.custom.TimetableRepositoryCustom;
import timesheet.orm.repository.filter.TimetableFilter;
import timesheet.util.GenericUtil;
import timesheet.util.RepositoryUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class TimetableRepositoryImpl implements TimetableRepositoryCustom {
    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    protected TimetableFilter timetableFilter;

    @Override
    public Long count(Timetable filter) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Timetable> root = query.from(Timetable.class);
        List<Predicate> predicates = new ArrayList<>(timetableFilter.filter(builder, root, filter));
        query.where(predicates.toArray(new Predicate[]{}));
        query.select(builder.count(root.get(Timetable_.id)));
        return entityManager.createQuery(query).getSingleResult();
    }

    @Override
    public Long sum(Timetable filter) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Timetable> root = query.from(Timetable.class);
        List<Predicate> predicates = new ArrayList<>(timetableFilter.filter(builder, root, filter));
        query.where(predicates.toArray(new Predicate[]{}));
        query.select(builder.sum(root.get(Timetable_.duration)));
        return entityManager.createQuery(query).getSingleResult();
    }

    @Override
    public List<Timetable> findAll(Timetable filter) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Timetable> query = builder.createQuery(Timetable.class);
        Root<Timetable> root = query.from(Timetable.class);
        List<Predicate> predicates = new ArrayList<>(timetableFilter.filter(builder, root, filter));
        query.where(predicates.toArray(new Predicate[]{}));
        query.select(root).distinct(true);
        List<Order> orders = Collections.singletonList(builder.asc(root.get(Timetable_.id)));
        query.orderBy(orders);
        return RepositoryUtil.addPagination(entityManager.createQuery(query), GenericUtil.isNotNullOrEmpty(filter) ? filter.getPagination() : null);
    }

    @Override
    public void softDelete(Timetable input) {
        input.preDelete();
        entityManager.merge(input);
    }

    @Override
    public void softDelete(List<Timetable> input) {
        input.forEach(BaseEntity::preDelete);
        entityManager.merge(input);
    }
}
