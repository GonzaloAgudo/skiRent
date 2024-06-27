package com.mycompany.jaas;

import com.mycompany.entities.UserGroups;
import com.mycompany.entities.Users;
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
}

