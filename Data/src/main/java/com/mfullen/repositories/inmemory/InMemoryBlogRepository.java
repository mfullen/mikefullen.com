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
        this.add(createNewBlog("My first blog"));
        this.add(createNewBlog("My Second blog"));
    }

    public static Blog createNewBlog(String title)
    {
        Blog blog = new Blog();
        blog.setDatePosted(new Date());
        blog.setTitle(title);

        Post post1 = new Post();
        post1.setBody("Test post");
        post1.setDatePosted(new Date());

        List<Post> posts = new ArrayList<Post>();
        posts.add(post1);

        blog.setPosts(posts);


        return blog;
    }
}
