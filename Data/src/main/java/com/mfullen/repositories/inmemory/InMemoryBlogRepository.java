package com.mfullen.repositories.inmemory;

import com.mfullen.model.Blog;
import com.mfullen.model.Post;
import com.mfullen.repositories.BlogRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
