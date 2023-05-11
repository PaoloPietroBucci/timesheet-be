package timesheet.orm.repository.impl;


import timesheet.libs.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import timesheet.orm.entity.Project;
import timesheet.orm.entity.Project_;
import timesheet.orm.repository.custom.ProjectRepositoryCustom;
import timesheet.orm.repository.filter.ProjectFilter;
import timesheet.util.GenericUtil;
import timesheet.util.RepositoryUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class ProjectRepositoryImpl implements ProjectRepositoryCustom {

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    protected ProjectFilter projectFilter;

    @Override
    public Long count(Project filter) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Project> root = query.from(Project.class);
        List<Predicate> predicates = new ArrayList<>(projectFilter.filter(builder, root, filter));
        query.where(predicates.toArray(new Predicate[]{}));
        query.select(builder.count(root.get(Project_.id)));
        return entityManager.createQuery(query).getSingleResult();
    }

    @Override
    public List<Project> findAll(Project filter) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Project> query = builder.createQuery(Project.class);
        Root<Project> root = query.from(Project.class);
        List<Predicate> predicates = new ArrayList<>(projectFilter.filter(builder, root, filter));
        query.where(predicates.toArray(new Predicate[]{}));
        query.select(root).distinct(true);
        List<Order> orders = Collections.singletonList(builder.asc(root.get(Project_.name)));
        query.orderBy(orders);
        return RepositoryUtil.addPagination(entityManager.createQuery(query), GenericUtil.isNotNullOrEmpty(filter) ? filter.getPagination() : null);
    }

    @Override
    public void softDelete(Project input) {
        input.preDelete();
        entityManager.merge(input);
    }

    @Override
    public void softDelete(List<Project> input) {
        input.forEach(BaseEntity::preDelete);
        entityManager.merge(input);
    }
}
