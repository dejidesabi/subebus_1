package com.tikal.subebus.modelo.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Contador {
	 @Id private Long id;
	 @Index
	 static Long folio;
	
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public  Long getFolio() {
		if (folio == null || folio==0)
			folio=Long.parseLong("1000");
		return folio;
	}
	public static void setFolio(Long folio) {
		Contador.folio = folio;
	}
	
	
	
	public Long incremeta(){
		
		Contador.folio=getFolio()+1;
		return folio;
	}
	
	public void  reinicia(){
		Contador.setFolio(Long.parseLong("1000"));
	}
	
	
}
