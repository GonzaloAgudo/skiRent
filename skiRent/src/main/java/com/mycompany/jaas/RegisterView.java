/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jaas;

import com.mycompany.entities.Users;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author gonza
 */
@Named
@SessionScoped
public class RegisterView implements Serializable {

    @Inject
    private UserEJB userEJB;

    private String name;
    private String email;
    private String password;
    private String confirmPassword;
    private Date fechaNacimiento;
    private String telefono;
    private String nif;
    private String domicilio;
    private String horarioEstablecimiento;
    private Boolean esVerificado;
    

    public void validatePassword(ComponentSystemEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIComponent components = event.getComponent();

        UIInput uiInputPassword = (UIInput) components.findComponent("password");
        String password = uiInputPassword.getLocalValue() == null ? "" : uiInputPassword.getLocalValue().toString();

        UIInput uiInputConfirmPassword = (UIInput) components.findComponent("confirmpassword");
        String confirmPassword = uiInputConfirmPassword.getLocalValue() == null ? "" : uiInputConfirmPassword.getLocalValue().toString();

        if (password.isEmpty() || confirmPassword.isEmpty()) {
            return;
        }

        if (!password.equals(confirmPassword)) {
            FacesMessage msg = new FacesMessage("Las contraseñas no coinciden");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            facesContext.addMessage(uiInputPassword.getClientId(), msg);
            facesContext.renderResponse();
        }

        UIInput uiInputEmail = (UIInput) components.findComponent("email");
        String email = uiInputEmail.getLocalValue() == null ? "" : uiInputEmail.getLocalValue().toString();

        if (userEJB.findByEmail(email) != null) {
            FacesMessage msg = new FacesMessage("Ya existe un usuario con ese email");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            facesContext.addMessage(uiInputPassword.getClientId(), msg);
            facesContext.renderResponse();
        }        
        if (fechaNacimiento != null){
            UIInput uiInputFechaNacimiento = (UIInput) components.findComponent("fechaNacimiento");
            Date fechaNacimiento = (Date) uiInputFechaNacimiento.getLocalValue();
            if (fechaNacimiento == null || !esAdulto(fechaNacimiento)) {
                FacesMessage msg = new FacesMessage("Debe ser mayor de 18 años");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                facesContext.addMessage(uiInputFechaNacimiento.getClientId(), msg);
                facesContext.renderResponse();
            }
        }
        
    }
    
    private boolean esAdulto(Date fechaNacimiento) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -18);
        Date today18YearsAgo = cal.getTime();
        return fechaNacimiento.before(today18YearsAgo);
    }
    

    public String registerUser() {
        Users user = new Users(email, password, name, telefono, nif, fechaNacimiento, esVerificado);
        user.setEsVerificado(true);
        userEJB.createUser(user);
        System.out.println("Nuevo cliente creado con e-mail: " + email + " y password: " + password);
        return "regok";
    }
    
    public String registerOwner() {
        Users user = new Users(email, password, name, telefono, nif, domicilio, horarioEstablecimiento, esVerificado);
        user.setEsVerificado(false);
        userEJB.createOwner(user);
        System.out.println("Nuevo propietario creado con e-mail: " + email + " y password: " + password);
        return "regok";
    }

    public UserEJB getUserEJB() {
        return userEJB;
    }

    public void setUserEJB(UserEJB userEJB) {
        this.userEJB = userEJB;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getHorarioEstablecimiento() {
        return horarioEstablecimiento;
    }

    public void setHorarioEstablecimiento(String horarioEstablecimiento) {
        this.horarioEstablecimiento = horarioEstablecimiento;
    }

    public Boolean getEsVerificado() {
        return esVerificado;
    }

    public void setEsVerificado(Boolean esVerificado) {
        this.esVerificado = esVerificado;
    }   
    
}
