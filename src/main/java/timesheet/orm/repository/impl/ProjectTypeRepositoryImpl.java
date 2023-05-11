package timesheet.orm.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import timesheet.libs.BaseEntity;
import timesheet.orm.entity.ProjectType;
import timesheet.orm.entity.ProjectType_;
import timesheet.orm.repository.custom.ProjectTypeRepositoryCustom;
import timesheet.orm.repository.filter.ProjectTypeFilter;
import timesheet.util.GenericUtil;
import timesheet.util.RepositoryUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class ProjectTypeRepositoryImpl implements ProjectTypeRepositoryCustom {

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    protected ProjectTypeFilter projectTypeFilter;

    @Override
    public Long count(ProjectType filter) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<ProjectType> root = query.from(ProjectType.class);
        List<Predicate> predicates = new ArrayList<>(projectTypeFilter.filter(builder, root, filter));
        query.where(predicates.toArray(new Predicate[]{}));
        query.select(builder.count(root.get(ProjectType_.id)));
        return entityManager.createQuery(query).getSingleResult();
    }

    @Override
    public List<ProjectType> findAll(ProjectType filter) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProjectType> query = builder.createQuery(ProjectType.class);
        Root<ProjectType> root = query.from(ProjectType.class);
        List<Predicate> predicates = new ArrayList<>(projectTypeFilter.filter(builder, root, filter));
        query.where(predicates.toArray(new Predicate[]{}));
        query.select(root).distinct(true);
        List<Order> orders = Collections.singletonList(builder.asc(root.get(ProjectType_.type)));
        query.orderBy(orders);
        return RepositoryUtil.addPagination(entityManager.createQuery(query), GenericUtil.isNotNullOrEmpty(filter) ? filter.getPagination() : null);
    }

    @Override
    public void softDelete(ProjectType input) {
        input.preDelete();
        entityManager.merge(input);
    }

    @Override
    public void softDelete(List<ProjectType> input) {
        input.forEach(BaseEntity::preDelete);
        entityManager.merge(input);
    }
}
