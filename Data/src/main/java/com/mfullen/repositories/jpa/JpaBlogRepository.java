package com.mfullen.repositories.jpa;

import com.mfullen.model.Blog;
import com.mfullen.repositories.BlogRepository;

/**
 *
 * @author mfullen
 */
public class JpaBlogRepository extends AbstractJpaRepository<Blog> implements
        BlogRepository
{
}
