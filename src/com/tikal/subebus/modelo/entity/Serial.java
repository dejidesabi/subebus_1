package com.tikal.subebus.modelo.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Serial {

	@Id
	private Long id;
	
	private int folio;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public long getFolio() {
		return folio;
	}

	public void setFolio(int folio) {
		this.folio = folio;
	}
	
	public void incrementa(){
		this.folio++;
	}
}
