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
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;

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
        this.setupUser();
    }

    @After
    public void tearDown()
    {
        this.factory.close();
    }

    @Test
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

    @Test
    public void testCreatePost()
    {
        Post post = new Post();
        post.setContent("This is my first post. WEEE!");
        this.user.setId(1L);
        post.setUser(this.user);
        post.setDatePosted(new Timestamp(System.currentTimeMillis()));


        Comment comment = new Comment();
        comment.setUser(user);
        comment.setContent("This is a comment");
        comment.setDatePosted(new Timestamp(System.currentTimeMillis()));

        List<Comment> comments = new ArrayList<>();
        // Comment find = this.entityManager.find(Comment.class, 1L);
        comments.add(comment);
        post.setComments(comments);

        session.saveOrUpdate(post);

        assertEquals(userName, post.getUser().getUserName());
    }

    private void setupUser()
    {
        final long id = 1;
        this.user = new UserModel();
        this.user.setId(id);
        this.user.setFirstName(firstName);
        this.user.setLastName(lastName);
        this.user.setEmail(email);
        this.user.setUserName(userName);
    }
}
