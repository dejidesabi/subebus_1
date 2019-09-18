package com.tikal.subebus.modelo.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class ContadorServicio {

	@Id private Long id;
	private Long folio;
	private Long guia;
	private Long factura;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getFolio() {
		
		if (folio==0){
			folio=Long.parseLong("2000");
		return folio;
		}else
			return folio;
		
		
	}
	public void incrementar() {
		this.folio++;
	}
	public void incrementarGuia() {
		this.guia++;
	}


	public void incrementarFactura() {
		this.factura++;
	}
	public void setFolio(Long folio){
		this.folio= folio;
	}
	public Long getGuia() {
		if (guia==null){
			guia=Long.parseLong("500000");
		return guia;
		}else
			return guia;
	}
	public void setGuia(Long guia) {
		this.guia = guia;
	}
	public void setFactura(Long factyra) {
		this.factura = factura;
	}
	
	public Long getFactura() {
		if (factura==null){
			factura=Long.parseLong("1000");
		return factura;
		}else
			return factura;
	}
}
