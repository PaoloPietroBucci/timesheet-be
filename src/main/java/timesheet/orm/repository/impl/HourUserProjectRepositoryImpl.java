package timesheet.orm.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import timesheet.orm.repository.custom.HourUserProjectRepositoryCustom;
import timesheet.orm.repository.custom.TotalHourPerProjectRepositoryCustom;
import timesheet.orm.repository.filter.HourUserProjectFilter;
import timesheet.orm.repository.filter.TotalHourPerProjectFilter;
import timesheet.orm.view.HourUserProject;
import timesheet.orm.view.HourUserProject_;
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
public class HourUserProjectRepositoryImpl implements HourUserProjectRepositoryCustom {

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    protected HourUserProjectFilter hourUserProjectFilter;

    @Override
    public Long count(HourUserProject filter) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<HourUserProject> root = query.from(HourUserProject.class);
        List<Predicate> predicates = new ArrayList<>(hourUserProjectFilter.filter(builder, root, filter));
        query.where(predicates.toArray(new Predicate[]{}));
        query.select(builder.count(root.get(HourUserProject_.uuid)));
        return entityManager.createQuery(query).getSingleResult();
    }

    @Override
    public List<HourUserProject> findAll(HourUserProject filter) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<HourUserProject> query = builder.createQuery(HourUserProject.class);
        Root<HourUserProject> root = query.from(HourUserProject.class);
        List<Predicate> predicates = new ArrayList<>(hourUserProjectFilter.filter(builder, root, filter));
        query.where(predicates.toArray(new Predicate[]{}));
        query.select(root).distinct(true);
        List<Order> orders = Collections.singletonList(builder.asc(root.get(HourUserProject_.uuid)));
        query.orderBy(orders);
        return RepositoryUtil.addPagination(entityManager.createQuery(query), GenericUtil.isNotNullOrEmpty(filter) ? filter.getPagination() : null);
    }

    @Override
    public void softDelete(HourUserProject input) {

    }

    @Override
    public void softDelete(List<HourUserProject> input) {

    }
}
