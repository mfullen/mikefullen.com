package com.mfullen.repositories.inmemory;

import com.mfullen.model.Comment;
import com.mfullen.repositories.CommentRepository;

/**
 *
 * @author mfullen
 */
public class InMemoryCommentRepository extends InMemoryRepoBase<Comment> implements
        CommentRepository
{
}
