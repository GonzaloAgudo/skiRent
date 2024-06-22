/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author gonza
 */
@Entity
@Table(name = "equipos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Equipos.findAll", query = "SELECT e FROM Equipos e"),
    @NamedQuery(name = "Equipos.findById", query = "SELECT e FROM Equipos e WHERE e.id = :id"),
    @NamedQuery(name = "Equipos.findByTipoEquipo", query = "SELECT e FROM Equipos e WHERE e.tipoEquipo = :tipoEquipo"),
    @NamedQuery(name = "Equipos.findByCiudad", query = "SELECT e FROM Equipos e WHERE e.ciudad = :ciudad"),
    @NamedQuery(name = "Equipos.findByDireccion", query = "SELECT e FROM Equipos e WHERE e.direccion = :direccion"),
    @NamedQuery(name = "Equipos.findByHorarioEstablecimiento", query = "SELECT e FROM Equipos e WHERE e.horarioEstablecimiento = :horarioEstablecimiento"),
    @NamedQuery(name = "Equipos.findByPrecioDia", query = "SELECT e FROM Equipos e WHERE e.precioDia = :precioDia")})
public class Equipos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 64)
    @Column(name = "tipo_equipo")
    private String tipoEquipo;
    @Size(max = 64)
    @Column(name = "ciudad")
    private String ciudad;
    @Size(max = 64)
    @Column(name = "direccion")
    private String direccion;
    @Size(max = 20)
    @Column(name = "horario_establecimiento")
    private String horarioEstablecimiento;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "precio_dia")
    private Double precioDia;

    public Equipos() {
    }

    public Equipos(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipoEquipo() {
        return tipoEquipo;
    }

    public void setTipoEquipo(String tipoEquipo) {
        this.tipoEquipo = tipoEquipo;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getHorarioEstablecimiento() {
        return horarioEstablecimiento;
    }

    public void setHorarioEstablecimiento(String horarioEstablecimiento) {
        this.horarioEstablecimiento = horarioEstablecimiento;
    }

    public Double getPrecioDia() {
        return precioDia;
    }

    public void setPrecioDia(Double precioDia) {
        this.precioDia = precioDia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Equipos)) {
            return false;
        }
        Equipos other = (Equipos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.entities.Equipos[ id=" + id + " ]";
    }
    
}
