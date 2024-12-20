package com.alexandjoe.recipesite.web;

import java.io.Serializable;
import java.util.ResourceBundle;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.faces.model.DataModel;
import jakarta.faces.model.ListDataModel;
import jakarta.faces.model.SelectItem;
import jakarta.inject.Named;
import com.alexandjoe.recipesite.ejb.LogStatus;
import com.alexandjoe.recipesite.ejb.RecipesFacade;
import com.alexandjoe.recipesite.entity.Recipes;
import com.alexandjoe.recipesite.web.util.JsfUtil;
import com.alexandjoe.recipesite.web.util.PaginationHelper;

/**
 * 
 * @author Alexander Jimenez
 */

@Named
@SessionScoped
public class RecipesController implements Serializable {
    private static final long serialVersionUID = -8163374738411860012L;
    private Recipes current;
    private DataModel items = null;
    private DataModel filteredItems = null;
    @EJB private RecipesFacade ejbFacade; // Made private by IDE suggestion
    @EJB private LogStatus ejbLog; // Access to user logged-in
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public RecipesController() {
    }

    // Method to get the logged-in user's username
    private String getLoggedInUser() {
        String username = getLogStatus().getUsername();
        System.out.println(username+"under getLoggeinUbser");
        return username;
    }
    
    public Recipes getSelected() {
        if (current == null) {
            current = new Recipes();
            selectedItemIndex = -1;
        }
        return current;
    }

    private RecipesFacade getFacade() {
        return ejbFacade;
    }
    
    private LogStatus getLogStatus(){
        return ejbLog;
    }
    
    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem()+getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Recipes)getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Recipes();
        selectedItemIndex = -1;
        return "recipelist";
    }
    
    public String create() {
        current.setPoster(getLoggedInUser()); // Associate recipe with logged-in user
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("RecipeCreated"));
            recreateModel();
            recreateFilteredModel(); // Refresh the filtered model
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
    
    public String prepareEdit() {
        current = (Recipes)getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }
    
    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("RecipesUpdated"));
            recreateModel();
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Recipes)getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreateModel();
        return "List";
    }
    
    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("RecipesDeleted"));
            recreateModel();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count-1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex+1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }
    
        // Method to fetch filtered recipes
    public DataModel getFilteredItems() {
        if (filteredItems == null) {
            String loggedInUser = getLoggedInUser();
            if (loggedInUser != null) {
                filteredItems = new ListDataModel(getFacade().findByPoster(loggedInUser));
            }
        }
        return filteredItems;
    }
    
        public void recreateFilteredModel() {
        filteredItems = null;
    }
        
    // Use this method to refresh the filteredItems list when navigating
    public String goToMyRecipes() {
        recreateFilteredModel();
        return "myrecipes"; // Navigate to the filtered recipes view
    }
        
    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    @FacesConverter(forClass=Recipes.class)
    public static class RecipesControllerConverter implements Converter<Recipes> {

        @Override
        public Recipes getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RecipesController controller = (RecipesController)facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "contactController");
            return controller.ejbFacade.find(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Recipes object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Recipes) {
                Recipes o = (Recipes) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + 
                        object + 
                        " is of type " + 
                        object.getClass().getName() + 
                        "; expected type: " +
                        RecipesController.class.getName());
            }
        }

    }
}