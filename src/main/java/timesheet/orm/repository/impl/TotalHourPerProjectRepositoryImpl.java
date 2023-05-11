package timesheet.orm.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import timesheet.orm.entity.ProjectType;
import timesheet.orm.entity.ProjectType_;
import timesheet.orm.repository.custom.TotalHourPerProjectRepositoryCustom;
import timesheet.orm.repository.filter.TotalHourPerProjectFilter;
import timesheet.orm.view.TotalHourPerProject;
import timesheet.orm.view.TotalHourPerProject_;
import timesheet.util.GenericUtil;
import timesheet.util.RepositoryUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class TotalHourPerProjectRepositoryImpl implements TotalHourPerProjectRepositoryCustom {

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    protected TotalHourPerProjectFilter totalHourPerProjectFilter;

    @Override
    public Long count(TotalHourPerProject filter) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<TotalHourPerProject> root = query.from(TotalHourPerProject.class);
        List<Predicate> predicates = new ArrayList<>(totalHourPerProjectFilter.filter(builder, root, filter));
        query.where(predicates.toArray(new Predicate[]{}));
        query.select(builder.count(root.get(TotalHourPerProject_.uuid)));
        return entityManager.createQuery(query).getSingleResult();
    }

    @Override
    public List<TotalHourPerProject> findAll(TotalHourPerProject filter) {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<TotalHourPerProject> query = builder.createQuery(TotalHourPerProject.class);
            Root<TotalHourPerProject> root = query.from(TotalHourPerProject.class);
            List<Predicate> predicates = new ArrayList<>(totalHourPerProjectFilter.filter(builder, root, filter));
            query.where(predicates.toArray(new Predicate[]{}));
            query.select(root).distinct(true);
            List<Order> orders = Collections.singletonList(builder.asc(root.get(TotalHourPerProject_.uuid)));
            query.orderBy(orders);
            return RepositoryUtil.addPagination(entityManager.createQuery(query), GenericUtil.isNotNullOrEmpty(filter) ? filter.getPagination() : null);
    }

    @Override
    public void softDelete(TotalHourPerProject input) {

    }

    @Override
    public void softDelete(List<TotalHourPerProject> input) {

    }
}
