/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.json;

import com.mycompany.entities.Equipos;
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
public class EquipoReader implements MessageBodyReader<Equipos> {

    @Override
    public boolean isReadable(Class<?> type, Type type1, Annotation[] antns, MediaType mt) {
        return Equipos.class.isAssignableFrom(type);
    }

    @Override
    public Equipos readFrom(Class<Equipos> type, Type type1, Annotation[] antns, MediaType mt, MultivaluedMap<String, String> mm, InputStream in) throws IOException, WebApplicationException {
        Equipos equipo = new Equipos();
        JsonParser parser = Json.createParser(in);
        
        while (parser.hasNext()) {
            switch (parser.next()) {
                case KEY_NAME:
                String key = parser.getString();
                parser.next();
                
                switch (key) {
                    
                case "id":
                    equipo.setId(parser.getInt());
                break;
                    
                case "tipoEquipo":
                    equipo.setTipoEquipo(parser.getString());
                break;
                
                case "ciudad":
                    equipo.setCiudad(parser.getString());
                break;
                
                case "direccion":
                    equipo.setDireccion(parser.getString());
                break;
                
                case "horarioEstablecimiento":
                    equipo.setHorarioEstablecimiento(parser.getString());
                break;
                
                case "precioDia":
                    equipo.setPrecioDia(parser.getInt());
                break;
                
                default:
                break;
                
                }
                break;
                default:
                break;
            }
        }
        return equipo;
    } 
}
