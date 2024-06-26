package com.mycompany.json;

import com.mycompany.entities.Reservas;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class ReservaWriter implements MessageBodyWriter<Reservas> {

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return Reservas.class.isAssignableFrom(type);
    }

    @Override
    public long getSize(Reservas reserva, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    @Override
    public void writeTo(Reservas reserva, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
        JsonGenerator gen = Json.createGenerator(entityStream);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        
        System.out.println("Tarjeta write-to " + reserva.getNumTarjeta());
        System.out.println("Fecha write-to " + dateFormat.format(reserva.getFechaReserva()));
        
        gen.writeStartObject()
            .write("emailCliente", reserva.getEmailCliente())
            .write("tipoEquipo", reserva.getTipoEquipo())
            .write("ciudad", reserva.getCiudad())
            .write("direccion", reserva.getDireccion())
            .write("horarioEstablecimiento", reserva.getHorarioEstablecimiento())
            .write("precioDia", reserva.getPrecio())
            .write("numTarjeta", reserva.getNumTarjeta())
            .write("fechaReserva", dateFormat.format(reserva.getFechaReserva()))
            .writeEnd();
        gen.flush();
    }
}

