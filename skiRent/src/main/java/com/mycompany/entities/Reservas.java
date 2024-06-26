/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author gonza
 */
@Entity
@Table(name = "reservas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reservas.findAll", query = "SELECT r FROM Reservas r"),
    @NamedQuery(name = "Reservas.findById", query = "SELECT r FROM Reservas r WHERE r.id = :id"),
    @NamedQuery(name = "Reservas.findByEmailCliente", query = "SELECT r FROM Reservas r WHERE r.emailCliente = :emailCliente"),
    @NamedQuery(name = "Reservas.findByTipoEquipo", query = "SELECT r FROM Reservas r WHERE r.tipoEquipo = :tipoEquipo"),
    @NamedQuery(name = "Reservas.findByCiudad", query = "SELECT r FROM Reservas r WHERE r.ciudad = :ciudad"),
    @NamedQuery(name = "Reservas.findByDireccion", query = "SELECT r FROM Reservas r WHERE r.direccion = :direccion"),
    @NamedQuery(name = "Reservas.findByHorarioEstablecimiento", query = "SELECT r FROM Reservas r WHERE r.horarioEstablecimiento = :horarioEstablecimiento"),
    @NamedQuery(name = "Reservas.findByPrecio", query = "SELECT r FROM Reservas r WHERE r.precio = :precio"),
    @NamedQuery(name = "Reservas.findByFechaReserva", query = "SELECT r FROM Reservas r WHERE r.fechaReserva = :fechaReserva"),
    @NamedQuery(name = "Reservas.findByNumTarjeta", query = "SELECT r FROM Reservas r WHERE r.numTarjeta = :numTarjeta"),
    @NamedQuery(name = "Reservas.findAllDetailsById", query = "SELECT r FROM Reservas r WHERE r.id = :id")})
public class Reservas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 255)
    @Column(name = "email_cliente")
    private String emailCliente;
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
    @Column(name = "precio")
    private int precio;
    @Column(name = "fecha_reserva")
    @Temporal(TemporalType.DATE)
    private Date fechaReserva;
    @Size(max = 32)
    @Column(name = "num_tarjeta")
    private String numTarjeta;

    public Reservas() {
    }

    public Reservas(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
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

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public Date getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(Date fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public String getNumTarjeta() {
        return numTarjeta;
    }

    public void setNumTarjeta(String numTarjeta) {
        this.numTarjeta = numTarjeta;
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
        if (!(object instanceof Reservas)) {
            return false;
        }
        Reservas other = (Reservas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.entities.Reservas[ id=" + id + " ]";
    }
    
}
