package com.mfullen.repositories.inmemory;

import com.mfullen.model.Post;
import com.mfullen.repositories.PostRepository;

/**
 *
 * @author mfullen
 */
public class InMemoryPostRepository extends InMemoryRepoBase<Post> implements
        PostRepository
{
}
