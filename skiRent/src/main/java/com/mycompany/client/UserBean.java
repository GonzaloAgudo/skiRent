package com.mycompany.client;

import com.mycompany.jaas.UserEJB;
import com.mycompany.entities.Users;
import com.mycompany.jaas.LoginView;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

@ManagedBean
@RequestScoped
public class UserBean {

    @EJB
    private UserEJB userEJB;
    
    @Inject
    LoginView login;

    private String email;

    // Otros métodos y atributos del bean

    public String eliminarCuenta() {
        try {
            Users usuario = obtenerUsuarioActual();
            if (usuario != null) {
                System.out.println("El email es: " + usuario.getEmail());
                userEJB.eliminarUsuario(usuario);
                // Invalidar la sesión después de eliminar la cuenta
                HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
                session.invalidate();
                return "index.xhtml?faces-redirect=true"; // Redirigir a la página de inicio
            } else {
                System.out.println("Usuario no encontrado.");
                return null;
            }
        } catch (Exception e) {
            // Manejo de errores
            e.printStackTrace();
            return null;
        }
    }

    private Users obtenerUsuarioActual() {
        String email = login.getAuthenticatedUser().getEmail();
        System.out.println("El email es: " + email);
        return userEJB.findByEmail(email);
    }
    
    // Getters y setters para email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}


