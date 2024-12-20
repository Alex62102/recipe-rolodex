package com.alexandjoe.recipesite.ejb;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.alexandjoe.recipesite.entity.Users;
import jakarta.persistence.TypedQuery;

/**
 *
 * @author Alexander Jimenez
 */
@Stateless
public class UsersFacade extends AbstractFacade<Users> {
    @PersistenceContext(unitName = "recipesitePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsersFacade() {
        super(Users.class);
    }
    
    private Users getSingleUser(String username) {
        String jpql = "SELECT u FROM Users u WHERE u.username = \"" + username + "\"";
        TypedQuery<Users> query = getEntityManager().createQuery(jpql, Users.class);
        return query.getSingleResult();
    }
    
    public boolean matchPassword(String username, String password) {
        return getSingleUser(username).getPassword().equals(password);
    }
    
    public void changePassword(String username, String password) {
        Users u = getSingleUser(username);
        u.setPassword(password);
        edit(u);
    }
    
    public boolean userExists(String username) {
        String jpql = "SELECT COUNT(u) FROM Users u WHERE u.username = :username";
        TypedQuery<Long> query = getEntityManager().createQuery(jpql, Long.class);
        query.setParameter("username", username);
        return query.getSingleResult() > 0;
    }

}