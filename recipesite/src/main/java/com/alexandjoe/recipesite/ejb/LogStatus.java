/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatefulEjbClass.java to edit this template
 */
package com.alexandjoe.recipesite.ejb;

import com.alexandjoe.recipesite.web.util.JsfUtil;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateful;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 *
 * @author jmanno
 */
@Named
@SessionScoped
@Stateful
public class LogStatus implements Serializable {

    private static final long serialVersionUID = 4866453L;
    @EJB private UsersFacade usersFacade;
    
    private boolean loggedIn;
    private String username;
    
    private String userIn;
    private String passIn;
    
    private String oldPass;
    private String newPass;
    
    
    
    public LogStatus() {
        loggedIn = false;
    }
    
    public void setLoggedIn(boolean b) {
        loggedIn = b;
    }
    
    public boolean getLoggedIn() {
        return loggedIn;
    }
    
    public void setUsername(String s) {
        username = s;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUserIn(String s) {
        userIn = s;
    }
    
    public String getUserIn() {
        return userIn;
    }
    
    public void setPassIn(String s) {
        passIn = s;
    }
    
    public String getPassIn() {
        return passIn;
    }
    
    public String logIn() {
        if(!usersFacade.userExists(userIn)) {
            JsfUtil.addErrorMessage("No user exists with that username");
            return null;
        }
        if(!usersFacade.matchPassword(userIn, passIn)) {
            JsfUtil.addErrorMessage("Username and password do not match");
            return null;
        }
        username = userIn.split("@")[0]; //replace with name of user based on email
        loggedIn = true;
        passIn = null;
        return "profile";
    }
    
    public String logOut() {
        username = null;
        loggedIn = false;
        return "logout";
    }
}
