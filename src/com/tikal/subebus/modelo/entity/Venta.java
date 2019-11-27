package com.tikal.subebus.modelo.entity;

import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFRow;

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
	@Index Long idMembresia;
	@Index Date fecha;
	private Date caducidadVenta;
	private double precio;
	@Index Long idSucursal;
	@Index String tipo;
	private String duracion;
	private String sector;
	@Index String user;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getEdad() {
		return edad;
	}
	public void setEdad(int edad) {
		this.edad = edad;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public Long getIdMembresia() {
		return idMembresia;
	}
	public void setIdMembresia(Long idMembresia) {
		this.idMembresia = idMembresia;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public Long getIdSucursal() {
		return idSucursal;
	}
	public void setIdSucursal(Long idSucursal) {
		this.idSucursal = idSucursal;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	
	
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getDuracion() {
		return duracion;
	}
	public void setDuracion(String duracion) {
		this.duracion = duracion;
	}
	
	
	public String getSector() {
		return sector;
	}
	public void setSector(String sector) {
		this.sector = sector;
	}
	
	
	public Date getCaducidadVenta() {
		return caducidadVenta;
	}
	public void setCaducidadVenta(Date caducidadVenta) {
		this.caducidadVenta = caducidadVenta;
	}
	public void llenarRenglon(HSSFRow r){
		for(int i=0;i<11;i++){
			r.createCell(i);
		}
		r.getCell(0).setCellValue(this.getFecha().toLocaleString());
		r.getCell(1).setCellValue(this.getIdMembresia());
		r.getCell(2).setCellValue(this.getTipo());
		r.getCell(3).setCellValue(this.getDuracion());
		r.getCell(4).setCellValue(this.getCaducidadVenta().toLocaleString());
		//r.getCell(2).setCellValue(this.isTieneGabinete());
		//if(this.isTieneGabinete()){
		//	r.getCell(2).setCellValue(this.getCaducidad());
//		}else{
//			r.getCell(2).setCellValue("no");
//		}
		
		
		r.getCell(5).setCellValue(this.getNombre());
		r.getCell(6).setCellValue(this.getEdad());
		r.getCell(7).setCellValue(this.getSexo());
		r.getCell(8).setCellValue(this.getTelefono());
		r.getCell(9).setCellValue(this.getMail());
		if(this.getPrecio()==0){
					r.getCell(10).setCellValue("0");
		}else{
					r.getCell(10).setCellValue(this.getPrecio());
		}
	
		
	
		
	}
	
	
}
