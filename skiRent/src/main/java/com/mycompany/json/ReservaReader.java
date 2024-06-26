/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.json;

import com.mycompany.entities.Reservas;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author gonza
 */
@Provider
@Consumes(MediaType.APPLICATION_JSON)
public class ReservaReader implements MessageBodyReader<Reservas> {

    @Override
    public boolean isReadable(Class<?> type, Type type1, Annotation[] antns, MediaType mt) {
        return Reservas.class.isAssignableFrom(type);
    }

    @Override
    public Reservas readFrom(Class<Reservas> type, Type type1, Annotation[] antns, MediaType mt, MultivaluedMap<String, String> mm, InputStream in) throws IOException, WebApplicationException {
        Reservas reserva = new Reservas();
        JsonParser parser = Json.createParser(in);
        
        while (parser.hasNext()) {
            switch (parser.next()) {
                case KEY_NAME:
                String key = parser.getString();
                parser.next();
                
                switch (key) {
                    
                case "id":
                    reserva.setId(parser.getInt());
                break;
                
                case "emailCliente":
                    reserva.setEmailCliente(parser.getString());
                break;
                    
                case "tipoEquipo":
                    reserva.setTipoEquipo(parser.getString());
                break;
                
                case "ciudad":
                    reserva.setCiudad(parser.getString());
                break;
                
                case "direccion":
                    reserva.setDireccion(parser.getString());
                break;
                
                case "horarioEstablecimiento":
                    reserva.setHorarioEstablecimiento(parser.getString());
                break;
                
                case "precioDia":
                    reserva.setPrecio(parser.getInt());
                break;
                
                default:
                break;
                
                }
                break;
                default:
                break;
            }
        }
        return reserva;
    } 
}

