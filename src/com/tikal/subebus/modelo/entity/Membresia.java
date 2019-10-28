package com.tikal.subebus.modelo.entity;

import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity

public class Membresia {

		@Id Long id;
		@Index String duracion;
		@Index String tipo;
		private Date fechaActivacion;
		@Index private Date fechaCaducidad;
		@Index String estatus;
		@Index private String qr;
		@Index Long idLote;
		@Index Long idSucursal;
		@Index Long idVenta;
		private byte[] codigoQR;
		
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
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
		public String getTipo() {
			return tipo;
		}
		public void setTipo(String tipo) {
			this.tipo = tipo;
		}
		public byte[] getCodogoQR() {
			return codigoQR;
		}
		public void setCodigoQR(byte[] codigoQR) {
			this.codigoQR = codigoQR;
		}
		public Long getIdSucursal() {
			return idSucursal;
		}
		public void setIdSucursal(Long idSucursal) {
			this.idSucursal = idSucursal;
		}
		public byte[] getCodigoQR() {
			return codigoQR;
		}
		public Long getIdVenta() {
			return idVenta;
		}
		public void setIdVenta(Long idVenta) {
			this.idVenta = idVenta;
		}
		
		
		
}
