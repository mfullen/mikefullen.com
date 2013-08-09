package com.mfullen.repositories.inmemory;

import com.mfullen.infrastructure.Repository;
import com.mfullen.model.AbstractModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author mfullen
 */
public class InMemoryRepoBase<T extends AbstractModel> implements
        Repository<T>
{
    private List<T> entities = new ArrayList<T>();
    private long count;

    public T save(T entity)
    {
        if (this.entities.add(entity))
        {
            entity.setId(++count);
        }
        return entity;
    }

    public T update(T entity)
    {
        T exists = this.getById(entity.getId());
        if (exists != null)
        {
            this.entities.remove(entity);
            this.entities.add(exists);
            return exists;
        }
        return entity;
    }

    public T delete(T entity)
    {
        if (this.entities.remove(entity))
        {
            return entity;
        }
        return null;
    }

    public T getById(long id)
    {
        for (T entity : entities)
        {
            if (entity.getId() == id)
            {
                return entity;
            }
        }
        return null;
    }

    public Collection<T> getAll()
    {
        return this.entities;
    }
}