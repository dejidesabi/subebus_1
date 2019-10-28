package com.tikal.subebus.modelo.entity;

import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class RutaMem {
	//@Id Long id;
	@Id Long idRutaBus;  //mismo id de ruta
	@Index Long venta;
	private String nombre;
	private Date fecha;
	@Index private Long sucursal;
//	public Long getId() {
//		return id;
//	}
//	public void setId(Long id) {
//		this.id = id;
//	}
	public Long getIdRutaBus() {
		return idRutaBus;
	}
	public void setIdRutaBus(Long idRutaBus) {
		this.idRutaBus = idRutaBus;
	}
	public Long getVenta() {
		return venta;
	}
	public void setVenta(Long venta) {
		this.venta = venta;
	}
	public String getNombre() {
		return nombre;
	} 
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Long getSucursal() {
		return sucursal;
	}
	public void setSucursal(Long sucursal) {
		this.sucursal = sucursal;
	}
	
	
	
}
