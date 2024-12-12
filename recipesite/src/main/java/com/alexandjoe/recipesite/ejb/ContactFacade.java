package com.alexandjoe.recipesite.ejb;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.alexandjoe.recipesite.entity.Contact;

/**
 *
 * @author ian
 */
@Stateless
public class ContactFacade extends AbstractFacade<Contact> {
    @PersistenceContext(unitName = "recipesitePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ContactFacade() {
        super(Contact.class);
    }

}
