package com.tikal.subebus.modelo.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Venta {
	
	@Id Long id;
	@Index String nombre;
	private int edad;
	@Index String sexo;
	@Index String telefono;
	@Index String mail;
	@Index int idMembresia;
	private double precio;
	@Index Long idSucursal;
	

}
