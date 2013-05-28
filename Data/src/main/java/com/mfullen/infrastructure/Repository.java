package com.mfullen.infrastructure;

import java.util.Collection;

/**
 *
 * @author mfullen
 */
public interface Repository<TEntity>
{
    TEntity add(TEntity entity);

    TEntity update(TEntity entity);

    TEntity delete(TEntity entity);

    TEntity getById(long id);

    Collection<TEntity> getAll();
}
