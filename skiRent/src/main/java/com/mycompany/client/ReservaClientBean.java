package com.mycompany.client;

import com.mycompany.entities.Reservas;
import com.mycompany.reservar.Reservar;
import com.mycompany.jaas.LoginView;
import com.mycompany.json.ReservaWriter;
import com.mycompany.json.ReservaReader;
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

@Named
@RequestScoped
public class ReservaClientBean {
    
    @Inject
    Reservar bean;
    LoginView login;
    
    private Client client;
    private WebTarget reservaTarget;
    
    @PostConstruct
    public void init() {
        client = ClientBuilder.newClient();
        reservaTarget = client.target("http://localhost:8080/skiRent/webresources/com.mycompany.entities.reservas");
    }
    
    @PreDestroy
    public void destroy() {
        client.close();
    }
    
    public Reservas[] getReservas() {
        return reservaTarget
        .request()
        .get(Reservas[].class);
    }
    
    public Reservas getReserva() {
        return reservaTarget
            .register(ReservaReader.class)
            .path("{reservaId}")
            .resolveTemplate("reservaId", bean.getIdReserva())
            .request(MediaType.APPLICATION_JSON)
            .get(Reservas.class);
    }
    
    public void deleteReserva() {
        reservaTarget.path("{reservaId}")
         .resolveTemplate("reservaId", bean.getIdReserva())
         .request()
         .delete();
    }
    
    public String guardarReserva() {
        Reservas reserva = new Reservas();
        //reserva.setEmailCliente(login.getAuthenticatedUser().getEmail());
        reserva.setTipoEquipo(bean.getTipoEquipo());
        reserva.setCiudad(bean.getCiudadEquipoSeleccionada());
        reserva.setDireccion(bean.obtenerEquipoPorId(bean.getIdEquipo()).getDireccion());
        reserva.setHorarioEstablecimiento(bean.obtenerEquipoPorId(bean.getIdEquipo()).getHorarioEstablecimiento());
        reserva.setPrecio(bean.obtenerEquipoPorId(bean.getIdEquipo()).getPrecioDia());
        reserva.setFechaReserva(bean.getFechaReserva());
        reserva.setNumTarjeta(bean.getTarjeta());
        
        
        //System.out.println("Usuario Reserva " + login.getAuthenticatedUser().getEmail());
        System.out.println("TipoEquipo " + bean.getTipoEquipo());
        System.out.println("Ciudad " + bean.getCiudadEquipoSeleccionada());
        System.out.println("Direccion " + bean.obtenerEquipoPorId(bean.getIdEquipo()).getDireccion());
        System.out.println("Horario Establecimiento " + bean.obtenerEquipoPorId(bean.getIdEquipo()).getHorarioEstablecimiento());
        System.out.println("Precio " + bean.obtenerEquipoPorId(bean.getIdEquipo()).getPrecioDia());
        System.out.println("Fecha Reserva " + bean.getFechaReserva());
        System.out.println("Numero Tarjeta " + bean.getTarjeta());

        reservaTarget.register(ReservaWriter.class).request().post(Entity.entity(reserva, MediaType.APPLICATION_JSON));
        
        return "resumen";
    }
}
