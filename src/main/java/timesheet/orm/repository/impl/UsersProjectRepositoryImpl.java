package timesheet.orm.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import timesheet.orm.entity.UsersProject;
import timesheet.orm.entity.UsersProject_;
import timesheet.orm.repository.filter.UsersProjectFilter;
import timesheet.orm.repository.custom.UsersProjectRepositoryCustom;
import timesheet.util.GenericUtil;
import timesheet.util.RepositoryUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class UsersProjectRepositoryImpl implements UsersProjectRepositoryCustom {

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    protected UsersProjectFilter usersProjectFilter;

    @Override
    public Long count(UsersProject filter) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<UsersProject> root = query.from(UsersProject.class);
        List<Predicate> predicates = new ArrayList<>(usersProjectFilter.filter(builder, root, filter));
        query.where(predicates.toArray(new Predicate[]{}));
        query.select(builder.count(root.get(UsersProject_.id)));
        return entityManager.createQuery(query).getSingleResult();
    }

    @Override
    public List<UsersProject> findAll(UsersProject filter) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UsersProject> query = builder.createQuery(UsersProject.class);
        Root<UsersProject> root = query.from(UsersProject.class);
        List<Predicate> predicates = new ArrayList<>(usersProjectFilter.filter(builder, root, filter));
        query.where(predicates.toArray(new Predicate[]{}));
        query.select(root).distinct(true);
        List<Order> orders = Collections.singletonList(builder.asc(root.get(UsersProject_.id)));
        query.orderBy(orders);
        return RepositoryUtil.addPagination(entityManager.createQuery(query), GenericUtil.isNotNullOrEmpty(filter) ? filter.getPagination() : null);
    }

    @Override
    public void softDelete(UsersProject input) {
        input.preDelete();
        entityManager.merge(input);
    }

    @Override
    public void softDelete(List<UsersProject> input) {
        input.forEach(element -> {
            element.preDelete();
            entityManager.merge(element);
        });
    }
}
