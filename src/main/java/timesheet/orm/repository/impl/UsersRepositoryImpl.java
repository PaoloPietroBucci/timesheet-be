package timesheet.orm.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import timesheet.orm.entity.Users;
import timesheet.orm.entity.Users_;
import timesheet.orm.repository.custom.UsersRepositoryCustum;
import timesheet.orm.repository.filter.UsersFilter;
import timesheet.util.GenericUtil;
import timesheet.util.RepositoryUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class UsersRepositoryImpl implements UsersRepositoryCustum {

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    protected UsersFilter usersFilter;

    @Override
    public Long count(Users filter) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Users> root = query.from(Users.class);
        List<Predicate> predicates = new ArrayList<>(usersFilter.filter(builder, root, filter));
        query.where(predicates.toArray(new Predicate[]{}));
        query.select(builder.count(root.get(Users_.id)));
        return entityManager.createQuery(query).getSingleResult();
    }

    @Override
    public List<Users> findAll(Users filter) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Users> query = builder.createQuery(Users.class);
        Root<Users> root = query.from(Users.class);
        List<Predicate> predicates = new ArrayList<>(usersFilter.filter(builder, root, filter));
        query.where(predicates.toArray(new Predicate[]{}));
        query.select(root).distinct(true);
        List<Order> orders = Collections.singletonList(builder.asc(root.get(Users_.id)));
        query.orderBy(orders);
        return RepositoryUtil.addPagination(entityManager.createQuery(query), GenericUtil.isNotNullOrEmpty(filter) ? filter.getPagination() : null);
    }

    @Override
    public Optional<Users> findByUsername(String username) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Users> query = builder.createQuery(Users.class);
        Root<Users> userRoot = query.from(Users.class);
        query.where(builder.equal(userRoot.get(Users_.username), username));
        query.select(userRoot).distinct(true);
        return entityManager.createQuery(query).getResultList().stream().findFirst();
    }

    @Override
    public void softDelete(Users input) {
        input.preDelete();
        entityManager.merge(input);
    }

    @Override
    public void softDelete(List<Users> input) {
        input.forEach(element -> {
            element.preDelete();
            entityManager.merge(element);
        });
    }
}
