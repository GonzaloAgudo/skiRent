/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.client;

import com.mycompany.json.EquipoReader;
import com.mycompany.json.EquipoWriter;
import com.mycompany.entities.Equipos;
import com.mycompany.entities.Reservas;
import com.mycompany.json.ReservaWriter;
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
        deleteEquipo();
        
        Equipos m = new Equipos();

        m.setId(bean.getEquipoId());
        m.setTipoEquipo(bean.getTipoEquipo());
        m.setCiudad(bean.getCiudad());
        m.setDireccion(bean.getDireccion());
        m.setHorarioEstablecimiento(bean.getHorarioEstablecimiento());
        m.setPrecioDia(bean.getPrecioDia());

        target.path("{equipoId}")
            .register(EquipoWriter.class)
            .resolveTemplate("equipoId", bean.getEquipoId())
            .request()
            .put(Entity.entity(m, MediaType.APPLICATION_JSON));
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
