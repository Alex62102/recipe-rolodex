package com.alexandjoe.recipesite.ejb;

import java.util.List;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.alexandjoe.recipesite.entity.Recipes;

/**
 *
 * @author Alexander Jimenez
 */
@Stateless
public class RecipesFacade extends AbstractFacade<Recipes> {
    @PersistenceContext(unitName = "recipesitePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<Recipes> findByPoster(String poster) {
    return em.createQuery("SELECT r FROM Recipes r WHERE r.poster = :poster", Recipes.class)
             .setParameter("poster", poster)
             .getResultList();
}

    public RecipesFacade() {
        super(Recipes.class);
    }

}