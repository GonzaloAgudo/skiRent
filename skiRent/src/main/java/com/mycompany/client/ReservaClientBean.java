package com.mycompany.client;

import com.mycompany.entities.Reservas;
import com.mycompany.reservar.Reservar;
import com.mycompany.reservar.TarjetaResponse;
import com.mycompany.jaas.LoginView;
import com.mycompany.json.ReservaWriter;
import com.mycompany.json.ReservaReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Named
@RequestScoped
public class ReservaClientBean {
    
    @Inject
    Reservar bean;
    @Inject    
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
        if (!isTarjetaAutorizada(bean.getTarjeta())) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "La tarjeta introducida no está disponible para autorizar pagos.", ""));
            return null;
        }
        
        if (!isReservaDisponible(bean.getTipoEquipo(), bean.getFechaReserva())) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "El equipo ya está reservado para la fecha seleccionada.", ""));
            return null;
        }

        Reservas reserva = new Reservas();

        if (login == null || login.getAuthenticatedUser() == null) {
            throw new IllegalStateException("Usuario no autenticado");
        }

        reserva.setEmailCliente(login.getAuthenticatedUser().getEmail());
        reserva.setTipoEquipo(bean.getTipoEquipo());
        reserva.setCiudad(bean.getCiudadEquipoSeleccionada());
        reserva.setDireccion(bean.obtenerEquipoPorId(bean.getIdEquipo()).getDireccion());
        reserva.setHorarioEstablecimiento(bean.obtenerEquipoPorId(bean.getIdEquipo()).getHorarioEstablecimiento());
        reserva.setPrecio(bean.obtenerEquipoPorId(bean.getIdEquipo()).getPrecioDia());
        reserva.setFechaReserva(bean.getFechaReserva());
        reserva.setNumTarjeta(bean.getTarjeta());

        reservaTarget.register(ReservaWriter.class).request().post(Entity.entity(reserva, MediaType.APPLICATION_JSON));

        return "resumen";
    }
    
    public boolean isTarjetaAutorizada(String numTarjeta) {
        WebTarget tarjetaTarget = client.target("http://serpis.infor.uva.es:80/pf2024_rest_cards/webresources/tarjetas/")
                                        .path(numTarjeta);
        try {
            Response response = tarjetaTarget.request(MediaType.APPLICATION_JSON).get();
            if (response.getStatus() == 200) {
                TarjetaResponse tarjetaResponse = response.readEntity(TarjetaResponse.class);
                return "si".equalsIgnoreCase(tarjetaResponse.getAutorizada());
            } else if (response.getStatus() == 404) {
                // La tarjeta no está registrada en la plataforma de pagos
                return false;
            } else {
                // Manejar otros códigos de estado si es necesario
                throw new WebApplicationException("Error en el servicio de validación de tarjetas: " + response.getStatus());
            }
        } catch (WebApplicationException e) {
            // Capturar y gestionar excepciones del tipo WebApplicationException
            e.printStackTrace();
            return false;
        }
    }
    
    
    public boolean isReservaDisponible(String tipoEquipo, Date fechaReserva) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"); // Asegúrate de usar el mismo formato de fecha
        Reservas[] reservasExistentes = getReservas();
        for (Reservas reserva : reservasExistentes) {
            if (reserva.getTipoEquipo().equals(tipoEquipo) &&
                sdf.format(reserva.getFechaReserva()).equals(sdf.format(fechaReserva))) {
                return false; // Ya existe una reserva para el mismo equipo en la misma fecha
            }
        }
        return true; // No existe ninguna reserva conflictiva
    }


}
