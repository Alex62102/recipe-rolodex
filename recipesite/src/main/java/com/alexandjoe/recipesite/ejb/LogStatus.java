/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatefulEjbClass.java to edit this template
 */
package com.alexandjoe.recipesite.ejb;

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
    
    private boolean loggedIn;
    private String username;
    
    private String emailIn;
    private String passIn;
    
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
    
    public void setEmailIn(String s) {
        emailIn = s;
    }
    
    public String getEmailIn() {
        return emailIn;
    }
    
    public void setPassIn(String s) {
        passIn = s;
    }
    
    public String getPassIn() {
        return passIn;
    }
    
    public String logIn() {
        //Check email against database
        username = emailIn.split("@")[0]; //replace with name of user based on email
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
