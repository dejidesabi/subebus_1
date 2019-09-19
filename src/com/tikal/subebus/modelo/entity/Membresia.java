package com.tikal.subebus.modelo.entity;

import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity

public class Membresia {

		@Id long id;
		@Index String duracion;
		private Date fechaActivacion;
		private Date fechaCaducidad;
		@Index String estatus;
		private String qr;
		@Index Long idLote;
		
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public String getDuracion() {
			return duracion;
		}
		public void setDuracion(String duracion) {
			this.duracion = duracion;
		}
		public Date getFechaActivacion() {
			return fechaActivacion;
		}
		public void setFechaActivacion(Date fechaActivacion) {
			this.fechaActivacion = fechaActivacion;
		}
		public Date getFechaCaducidad() {
			return fechaCaducidad;
		}
		public void setFechaCaducidad(Date fechaCaducidad) {
			this.fechaCaducidad = fechaCaducidad;
		}
		public String getEstatus() {
			return estatus;
		}
		public void setEstatus(String estatus) {
			this.estatus = estatus;
		}
		public String getQr() {
			return qr;
		}
		public void setQr(String qr) {
			this.qr = qr;
		}
		public Long getIdLote() {
			return idLote;
		}
		public void setIdLote(Long idLote) {
			this.idLote = idLote;
		}
		
		
		
}
