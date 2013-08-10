/*
 * Copyright PWF Technology LLC
 */
package com.mfullen.model;

import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;

/**
 *
 * @author mfullen
 */
public class DbCreationTest
{
    private static final String P_UNIT = "mfullen_models_test";
    private EntityManagerFactory factory;
    private EntityManager entityManager;
    private Session session;
    private final String firstName = "Mike";
    private final String lastName = "Fullen";
    private final String email = "mike@mikefullen.com";
    private final String userName = "mfullen";
    private UserModel user;

    @Before
    public void setup()
    {
        this.factory = Persistence.createEntityManagerFactory(P_UNIT);
        this.entityManager = this.factory.createEntityManager();
        this.session = this.entityManager.unwrap(Session.class);
        this.session.beginTransaction();
        this.setupUser();
    }

    @After
    public void tearDown()
    {
        this.factory.close();
    }

    @Test
    @Ignore
    public void testCreateDb()
    {
        final long id = 1;
        this.user.setId(null);
        session.saveOrUpdate(this.user);

        UserModel existingUserModel = this.entityManager.find(UserModel.class, id);
        assertNotNull(existingUserModel);
        assertEquals(firstName, existingUserModel.getFirstName());
        assertEquals(lastName, existingUserModel.getLastName());
        assertEquals(email, existingUserModel.getEmail());
        assertEquals(userName, existingUserModel.getUserName());
    }

    private void saveOrUpdate(Object model)
    {
        this.session.saveOrUpdate(model);
    }

    @Test
    public void testCreateBlog()
    {
        saveOrUpdate(this.user);

        UserModel userFromDb = entityManager.find(UserModel.class, 1L);
        assertNotNull(userFromDb);

        Blog firstBlogFromDb = entityManager.find(Blog.class, 1L);

        if (firstBlogFromDb == null)
        {
            Blog blog = new Blog();
            blog.setUser(userFromDb);
            blog.setDatePosted(new Timestamp(System.currentTimeMillis()));
            saveOrUpdate(blog);
            firstBlogFromDb = entityManager.find(Blog.class, 1L);
        }

        assertNotNull(firstBlogFromDb);

        Post post1 = new Post();
        post1.setContent("This is the first post");
        post1.setUser(userFromDb);
        post1.setDatePosted(new Timestamp(System.currentTimeMillis()));
        post1.setBlog(firstBlogFromDb);

        Post post2 = new Post();
        post2.setContent("This is the second post");
        post2.setUser(userFromDb);
        post2.setDatePosted(new Timestamp(System.currentTimeMillis()));
        post2.setBlog(firstBlogFromDb);

        Comment comment = new Comment();
        comment.setUser(userFromDb);
        comment.setContent("This is a comment");
        comment.setDatePosted(new Timestamp(System.currentTimeMillis()));
        comment.setPost(post1);

        List<Comment> comments = new ArrayList<>();
        comments.add(comment);
        post1.setComments(comments);

        List<Post> posts = new ArrayList<>();
        posts.add(post1);
        posts.add(post2);

        firstBlogFromDb.setPosts(posts);
        saveOrUpdate(firstBlogFromDb);


        CriteriaQuery<Blog> query = entityManager.getCriteriaBuilder().createQuery(Blog.class);
        query.from(Blog.class);
        List<Blog> blogs = entityManager.createQuery(query).getResultList();
        assertFalse(blogs.isEmpty());
        assertEquals(blogs.size(), 1);

        //get saved posts
        CriteriaQuery<Post> postQuery = entityManager.getCriteriaBuilder().createQuery(Post.class);
        postQuery.from(Post.class);
        List<Post> postsFromDb = entityManager.createQuery(postQuery).getResultList();
        assertFalse(postsFromDb.isEmpty());
        assertEquals(postsFromDb.size(), 2);


        Blog blogFromDb = blogs.get(0);
        Post postFromDb = blogFromDb.getPosts().iterator().next();
        assertEquals(blogFromDb.getPosts().size(), 2);
        assertEquals(postFromDb.getBlog().getId().intValue(), 1);
        assertEquals(postFromDb.getUser().getId().intValue(), 1);
    }

    private void setupUser()
    {
        this.user = new UserModel();
        this.user.setFirstName(firstName);
        this.user.setLastName(lastName);
        this.user.setEmail(email);
        this.user.setUserName(userName);
    }
}
