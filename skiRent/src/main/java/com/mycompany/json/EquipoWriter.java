/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.json;

import com.mycompany.entities.Equipos;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author gonza
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class EquipoWriter implements MessageBodyWriter<Equipos> {

    @Override
    public boolean isWriteable(Class<?> type, Type type1, Annotation[] antns, MediaType mt) {
        return Equipos.class.isAssignableFrom(type);
    }

    @Override
    public long getSize(Equipos t, Class<?> type, Type type1, Annotation[] antns, MediaType mt) {
        return -1;
    }

    @Override
    public void writeTo(Equipos t, Class<?> type, Type type1, Annotation[] antns, MediaType mt, MultivaluedMap<String, Object> mm, OutputStream out) throws IOException, WebApplicationException {
        JsonGenerator gen = Json.createGenerator(out);
        gen.writeStartObject()
        .write("tipoEquipo", t.getTipoEquipo())
        .write("ciudad", t.getCiudad())
        .write("direccion", t.getDireccion())
        .write("horarioEstablecimiento", t.getHorarioEstablecimiento())
        .write("precioDia", t.getPrecioDia())
        .writeEnd();
        gen.flush();        
    }    
}
