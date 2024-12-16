/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.alexandjoe.recipesite.entity;

import java.io.Serializable;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

/**
 *
 * @author jmanno
 */
@Entity
@Table(name = "RECIPES")
@NamedQueries({
    @NamedQuery(name = "Recipes.findAll", query = "SELECT r FROM Recipes r"),
    @NamedQuery(name = "Recipes.findById", query = "SELECT r FROM Recipes r WHERE r.id = :id"),
    @NamedQuery(name = "Recipes.findByName", query = "SELECT r FROM Recipes r WHERE r.name = :name"),
    @NamedQuery(name = "Recipes.findByPoster", query = "SELECT r FROM Recipes r WHERE r.poster = :poster"),
    @NamedQuery(name = "Recipes.findByType", query = "SELECT r FROM Recipes r WHERE r.type = :type"),
    @NamedQuery(name = "Recipes.findByPreptime", query = "SELECT r FROM Recipes r WHERE r.preptime = :preptime"),
    @NamedQuery(name = "Recipes.findByServings", query = "SELECT r FROM Recipes r WHERE r.servings = :servings"),
    @NamedQuery(name = "Recipes.findByCuisine", query = "SELECT r FROM Recipes r WHERE r.cuisine = :cuisine")})
public class Recipe implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "NAME")
    private String name;
    @Basic(optional = false)
    @Column(name = "POSTER")
    private String poster;
    @Column(name = "TYPE")
    private String type;
    @Lob
    @Column(name = "INGREDIENTS")
    private String ingredients;
    @Lob
    @Column(name = "PROCESS")
    private String process;
    @Column(name = "PREPTIME")
    private Short preptime;
    @Column(name = "SERVINGS")
    private Short servings;
    @Column(name = "CUISINE")
    private String cuisine;

    public Recipe() {
    }

    public Recipe(Integer id) {
        this.id = id;
    }

    public Recipe(Integer id, String name, String poster) {
        this.id = id;
        this.name = name;
        this.poster = poster;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public Short getPreptime() {
        return preptime;
    }

    public void setPreptime(Short preptime) {
        this.preptime = preptime;
    }

    public Short getServings() {
        return servings;
    }

    public void setServings(Short servings) {
        this.servings = servings;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Recipe)) {
            return false;
        }
        Recipe other = (Recipe) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.alexandjoe.recipesite.entity.Recipes[ id=" + id + " ]";
    }
    
}
