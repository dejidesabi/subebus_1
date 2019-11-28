package com.tikal.subebus.modelo.entity;

import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFRow;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class RutaMem {
	//@Id Long id;
	@Id Long idRutaBus;  //mismo id de ruta
	private String ruta;
	private String chofer;
	@Index Long membresia;
	private String duracion;
	private String nombre;
	@Index private Date fecha;
	@Index private Long sucursal;
	private Long venta;
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

	public Long getMembresia() {
		return membresia;
	}
	public void setMembresia(Long membresia) {
		this.membresia = membresia;
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
	public Long getVenta() {
		return venta;
	}
	public void setVenta(Long venta) {
		this.venta = venta;
	}
	
	
	public String getRuta() {
		return ruta;
	}
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	public String getChofer() {
		return chofer;
	}
	public void setChofer(String chofer) {
		this.chofer = chofer;
	}
	
	
	public String getDuracion() {
		return duracion;
	}
	public void setDuracion(String duracion) {
		this.duracion = duracion;
	}
	public void llenarRenglon(HSSFRow r){
		for(int i=0;i<6;i++){
			r.createCell(i);
		}
		
		r.getCell(0).setCellValue(this.getRuta());
		r.getCell(1).setCellValue(this.getChofer());
		//r.getCell(2).setCellValue(this.isTieneGabinete());
//		if(this.isTieneGabinete()){
			r.getCell(2).setCellValue(this.getMembresia());
//		}else{
			r.getCell(3).setCellValue(this.getDuracion());
//		}
		
		
		r.getCell(4).setCellValue(this.getNombre());
		r.getCell(5).setCellValue(this.getFecha().toGMTString());
//		r.getCell(5).setCellValue(this.getCircuito());
//		r.getCell(6).setCellValue(this.getLongitud());
//		r.getCell(7).setCellValue(this.getLatitud());
//		r.getCell(8).setCellValue(this.fechaInstalacion);
//		r.getCell(9).setCellValue(this.getCuadrilla());
//		r.getCell(10).setCellValue(this.getPotencia());
		
	
		
	}
}
