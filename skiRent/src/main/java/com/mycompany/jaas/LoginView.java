/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jaas;

import com.mycompany.entities.Users;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author gonza
 */
@Named
@SessionScoped
public class LoginView implements Serializable{
    
    @Inject
    private UserEJB userEJB;

    
    private String email;
    private String password;
    private Users user;
    
    
    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        
        try {
            System.out.println("Login intentado con e-mail: " + email + " y password: " + password);
            request.login(email, password);
        } catch (ServletException e) {
            System.out.println("Login fallado con e-mail: " + email + " y password: " + password);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Login incorrecto!", null));
            return "login";
        }
        
        this.user = userEJB.findByEmail(request.getUserPrincipal().getName());       
        if (request.isUserInRole("users")) {
            return "/users/privatepage?faces-redirect=true";
        } else if (request.isUserInRole("admin")) {
            return "/admin/privatepage?faces-redirect=true";
        }else if (request.isUserInRole("owners")){
            return "/owners/privatepage?faces-redirect=true";
        } else {
            return "login";
        }
    }
    
    
    public String logout() {
    FacesContext context = FacesContext.getCurrentInstance();
    HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
    
    try {
    this.user = null;
    request.logout();
    ((HttpSession) context.getExternalContext().getSession(false)).invalidate();
    } catch (ServletException e) {
        System.out.println("Fallo durante el proceso de logout!");
    }
    
    return "/index?faces-redirect=true";
}
    
    
    public Users getAuthenticatedUser() {
        return user;
    }

    public UserEJB getUserEJB() {
        return userEJB;
    }

    public void setUserEJB(UserEJB userEJB) {
        this.userEJB = userEJB;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    } 
    
}
