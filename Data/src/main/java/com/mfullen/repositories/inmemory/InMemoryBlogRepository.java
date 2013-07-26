package com.mfullen.repositories.inmemory;

import com.mfullen.model.Blog;
import com.mfullen.repositories.BlogRepository;

/**
 *
 * @author mfullen
 */
public class InMemoryBlogRepository extends InMemoryRepoBase<Blog> implements
        BlogRepository
{
    public InMemoryBlogRepository()
    {
    }
}
