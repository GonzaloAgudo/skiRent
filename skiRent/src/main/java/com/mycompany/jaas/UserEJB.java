package com.mycompany.jaas;

import com.mycompany.entities.UserGroups;
import com.mycompany.entities.Users;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class UserEJB {
    @PersistenceContext
    private EntityManager em;

    public Users createUser(Users user) {
        try {
            user.setPassword(AuthenticationUtils.encodeSHA256(user.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        UserGroups group = new UserGroups();
        group.setEmail(user.getEmail());
        group.setGroupName("users");
        em.persist(user);
        em.persist(group);
        return user;
    }    
    
    public Users createOwner(Users user) {
        try {
            user.setPassword(AuthenticationUtils.encodeSHA256(user.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        UserGroups group = new UserGroups();
        group.setEmail(user.getEmail());
        group.setGroupName("owners");
        em.persist(user);
        em.persist(group);
        return user;
    }

    public Users findByEmail(String email) {
        TypedQuery<Users> query = em.createNamedQuery("Users.findByEmail", Users.class);
        query.setParameter("email", email);
        Users user = null;
        try {
            user = query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }    
    
    public void eliminarUsuario(Users user) {
        if (user != null) {
            UserGroups group = em.find(UserGroups.class, user.getEmail());
            if (group != null) {
                em.remove(group);
            }
            if (!em.contains(user)) {
                user = em.merge(user);
            }
            em.remove(user);
        }
    }
    
    public void eliminarUsuarioPorEmail(String email) {
        Users usuario = em.find(Users.class, email);
        if (usuario != null) {
            // Eliminar el grupo de usuarios asociado
            UserGroups group = em.find(UserGroups.class, email);
            if (group != null) {
                em.remove(group);
            }
            // Eliminar el usuario
            em.remove(usuario);
        }
    }


    public List<Users> obtenerPropietariosNoVerificados() {
        TypedQuery<Users> query = em.createQuery("SELECT u FROM Users u WHERE u.esVerificado = false", Users.class);
        return query.getResultList();
    }

    public void actualizarVerificacionUsuario(String email, boolean esVerificado) {
        Users usuario = em.find(Users.class, email);
        if (usuario != null) {
            usuario.setEsVerificado(esVerificado);
            em.merge(usuario);
        }
    }
    
    public List<Users> obtenerTodosPropietarios() {
        TypedQuery<Users> query = em.createQuery("SELECT u FROM Users u WHERE u.email IN (SELECT ug.email FROM UserGroups ug WHERE ug.groupName = 'owners')", Users.class);
        return query.getResultList();
    }
}

