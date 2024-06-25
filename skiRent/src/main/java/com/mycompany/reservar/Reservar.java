/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.reservar;

import com.mycompany.entities.Equipos;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.flow.FlowScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author gonza
 */
@Named
@FlowScoped("reservar")
public class Reservar implements Serializable{
    
    @PersistenceContext
    EntityManager em;
    
    int idEquipo;
    String tipoEquipo;
    String ciudadEquipo;
    String ciudadEquipoSeleccionada;
    String direccionEquipo;
    String horarioEstablecimiento;
    float precioDia;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getTipoEquipo() {
        return tipoEquipo;
    }

    public void setTipoEquipo(String tipoEquipo) {
        this.tipoEquipo = tipoEquipo;
    }    

    public String getCiudadEquipo() {
        return ciudadEquipo;
    }

    public void setCiudadEquipo(String ciudadEquipo) {
        this.ciudadEquipo = ciudadEquipo;
    }

    public String getCiudadEquipoSeleccionada() {
        return ciudadEquipoSeleccionada;
    }

    public void setCiudadEquipoSeleccionada(String ciudadEquipoSeleccionada) {
        this.ciudadEquipoSeleccionada = ciudadEquipoSeleccionada;
    } 

    public String getDireccionEquipo() {
        return direccionEquipo;
    }

    public void setDireccionEquipo(String direccionEquipo) {
        this.direccionEquipo = direccionEquipo;
    }

    public String getHorarioEstablecimiento() {
        return horarioEstablecimiento;
    }

    public void setHorarioEstablecimiento(String horarioEstablecimiento) {
        this.horarioEstablecimiento = horarioEstablecimiento;
    }

    public float getPrecioDia() {
        return precioDia;
    }

    public void setPrecioDia(float precioDia) {
        this.precioDia = precioDia;
    }   

    public List<Equipos> getEquiposFiltrados() {
        if (ciudadEquipoSeleccionada == null || ciudadEquipoSeleccionada.isEmpty()) {
            return new ArrayList<>();
        }
        return em.createQuery("SELECT e FROM Equipos e WHERE e.ciudad = :ciudad", Equipos.class)
                 .setParameter("ciudad", ciudadEquipoSeleccionada)
                 .getResultList();
    }

    public String seleccionarEquipo() {
        try {
            Equipos equipoSeleccionado = em.createNamedQuery("Equipos.findById", Equipos.class)
                                           .setParameter("id", idEquipo)
                                           .getSingleResult();
            this.tipoEquipo = equipoSeleccionado.getTipoEquipo();
        } catch (NoResultException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Equipo no encontrado", null));
        }
        return "confirmar";
    }

}
