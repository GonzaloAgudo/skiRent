/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.client;

import com.mycompany.json.EquipoReader;
import com.mycompany.json.EquipoWriter;
import com.mycompany.entities.Equipos;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author gonza
 */
@Named
@RequestScoped
public class EquipoClientBean {
    @Inject
    EquipoBackingBean bean;
    
    Client client;
    WebTarget target;
    
    @PostConstruct
    public void init() {
    client = ClientBuilder.newClient();
    target = client.target("http://localhost:8080/skiRent/webresources/com.mycompany.entities.equipos");
    }
    
    @PreDestroy
    public void destroy() {
    client.close();
    }
    
    public Equipos[] getEquipos() {
        return target
        .request()
        .get(Equipos[].class);
    }
    
    public Equipos getEquipo() {
        return target
            .register(EquipoReader.class)
            .path("{equipoId}")
            .resolveTemplate("equipoId", bean.getEquipoId())
            .request(MediaType.APPLICATION_JSON)
            .get(Equipos.class);
    }
    
    public void deleteEquipo() {
        target.path("{equipoId}")
         .resolveTemplate("equipoId", bean.getEquipoId())
         .request()
         .delete();
    }
    
    public void deleteEquipoById(int equipoId) {
    target.path("{equipoId}")
        .resolveTemplate("equipoId", equipoId)
        .request()
        .delete();
}
    
    public void addEquipo() {
        Equipos m = new Equipos();
        
        m.setTipoEquipo(bean.getTipoEquipo());
        m.setCiudad(bean.getCiudad());
        m.setDireccion(bean.getDireccion());
        m.setHorarioEstablecimiento(bean.getHorarioEstablecimiento());
        m.setPrecioDia(bean.getPrecioDia());
        
        System.out.println("El precio por dia es: ");
        
        target.register(EquipoWriter.class).request().post(Entity.entity(m, MediaType.APPLICATION_JSON));
    }
    
    
   public void updateEquipo() {
        // Carga el equipo existente
        Equipos equipo = getEquipo();

        if (equipo == null) {
            // Si el equipo no existe, manejar el error apropiadamente
            System.out.println("Equipo no encontrado con ID: " + bean.getEquipoId());
            return;
        }

        // Actualiza los campos necesarios
        equipo.setTipoEquipo(bean.getTipoEquipo());
        equipo.setCiudad(bean.getCiudad());
        equipo.setDireccion(bean.getDireccion());
        equipo.setHorarioEstablecimiento(bean.getHorarioEstablecimiento());
        equipo.setPrecioDia(bean.getPrecioDia());

        // Realiza la actualización del equipo existente
        target.path("{equipoId}")
            .register(EquipoWriter.class)
            .resolveTemplate("equipoId", bean.getEquipoId())
            .request()
            .put(Entity.entity(equipo, MediaType.APPLICATION_JSON));
    }


    public void loadEquipo() {
        
        Equipos equipo = getEquipo();
        
        bean.setEquipoId(equipo.getId());
        bean.setTipoEquipo(equipo.getTipoEquipo());
        bean.setCiudad(equipo.getCiudad());
        bean.setDireccion(equipo.getDireccion());
        bean.setHorarioEstablecimiento(equipo.getHorarioEstablecimiento());
        bean.setPrecioDia(equipo.getPrecioDia());
        
    }
}
