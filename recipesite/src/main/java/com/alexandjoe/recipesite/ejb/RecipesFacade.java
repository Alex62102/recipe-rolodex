package com.alexandjoe.recipesite.ejb;

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

    public RecipesFacade() {
        super(Recipes.class);
    }

}