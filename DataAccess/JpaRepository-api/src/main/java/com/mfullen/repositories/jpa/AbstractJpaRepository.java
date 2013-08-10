package com.mfullen.repositories.jpa;

import com.google.common.reflect.TypeToken;
import com.mfullen.infrastructure.Repository;
import com.mfullen.model.AbstractModel;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;
import org.hibernate.Session;

/**
 *
 * @author mfullen
 */
public abstract class AbstractJpaRepository<T extends AbstractModel> implements
        Repository<T>
{
    @Inject
    private EntityManager entityManager;
    private CriteriaBuilder builder;
    private TypeToken<T> type = new TypeToken<T>(getClass())
    {
    };

    public CriteriaBuilder getBuilder()
    {
        return builder;
    }

    public EntityManager getEntityManager()
    {
        return entityManager;
    }

    @PostConstruct
    public void init()
    {
        this.builder = entityManager.getCriteriaBuilder();
    }

    public T save(T entity)
    {
        return this.saveOrUpdate(entity);
    }

    public T update(T entity)
    {
        return this.saveOrUpdate(entity);
    }

    protected T saveOrUpdate(T model)
    {
        Session session = this.getEntityManager().unwrap(Session.class);
        session.saveOrUpdate(model);
        session.flush();
        session.clear();
        return model;
    }

    public T delete(T entity)
    {
        entity = this.getEntityManager().merge(entity);
        this.getEntityManager().remove(entity);
        return entity;
    }

    public T getById(long id)
    {
        return this.getEntityManager().find(getType(), id);
    }

    public long getCount()
    {
        CriteriaQuery<Long> query = entityManager.getCriteriaBuilder().createQuery(Long.class);
        Root<T> root = query.from(getType());
        query.select(entityManager.getCriteriaBuilder().count(root));
        return this.getEntityManager().createQuery(query).getSingleResult();
    }

    public Collection<T> getAll()
    {
        CriteriaQuery<T> query = entityManager.getCriteriaBuilder().createQuery(getType());
        query.from(getType());
        return this.getEntityManager().createQuery(query).getResultList();
    }

    protected <V> List<T> findByField(Attribute<T, V> field, V value)
    {
        return findByField(field, value, false);
    }

    protected <V> List<T> findByField(Attribute<T, V> field, V value,
                                      boolean cache)
    {
        CriteriaQuery<T> query = entityManager.getCriteriaBuilder().createQuery(getType());
        Root<T> root = query.from(getType());

        query.where(entityManager.getCriteriaBuilder().equal(root.get(field.getName()), value));
        TypedQuery<T> q = this.getEntityManager().createQuery(query);

        return q.getResultList();
    }

    @SuppressWarnings("unchecked")
    protected Class<T> getType()
    {
        return (Class<T>) type.getRawType();
    }
}
