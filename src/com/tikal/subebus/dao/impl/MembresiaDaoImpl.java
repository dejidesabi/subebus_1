package com.tikal.subebus.dao.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tikal.subebus.dao.MembresiaDao;
import com.tikal.subebus.modelo.entity.Membresia;
import static com.googlecode.objectify.ObjectifyService.ofy;

@Service("memDao")
public class MembresiaDaoImpl implements MembresiaDao {

	@Override
	public void crear(Membresia m) {
		// TODO Auto-generated method stub
		ofy().save().entity(m);
	}

	@Override
	public void actualizar(Membresia m) {
		Membresia old = this.consultar(m.getId());
		if (old != null) {
			old.setDuracion(m.getDuracion());
			old.setEstatus(m.getEstatus());
			old.setFechaActivacion(m.getFechaActivacion());
			old.setFechaCaducidad(m.getFechaCaducidad());
			old.setQr(m.getQr());
			ofy().save().entity(old);
		}
	}

	@Override
	public Membresia consultar(Long id) {
		return ofy().load().type(Membresia.class).id(id).now();
	}

	@Override
	public void eliminar(Membresia m) {
		//m.setEstatus("INACTIVA");
		//actualizar(m);
		ofy().delete().entity(m).now();
	}

	@Override
	public List<Membresia> consultarTodos() {
		return ofy().load().type(Membresia.class).list();
	}
	
	@Override     /// activas y en uso para el check
	public List<Membresia> ActivasEnUso() {
		List<Membresia> act = ofy().load().type(Membresia.class).filter("estatus","ACTIVA").list();
		List<Membresia> uso = ofy().load().type(Membresia.class).filter("estatus","EN USO").list();
		List<Membresia> lista= act;
		lista.addAll(uso);
		return lista;
	}

	
	@Override
	public int pags() {
		return ((ofy().load().type(Membresia.class).count()-1)/15)+1;
	}

	@Override
	public List<Membresia> findAllPage(int page) {
		return ofy().load().type(Membresia.class).order("- fechaCaducidad").offset((page-1)*15).limit(10).list();
	}

	@Override
	public List<Membresia> byLote(Long idLote) {
		return ofy().load().type(Membresia.class).filter("idLote", idLote).list();
	}

	@Override
	public List<Membresia> byDTS(String duracion, String tipo, Long idSucursal) {
		return ofy().load().type(Membresia.class).filter("duracion", duracion).filter("tipo", tipo).filter("estatus", "INACTIVA").filter("idSucursal", idSucursal).list();
	}

	@Override
	public List<Membresia> byLoteA(Long idLote) {
		return ofy().load().type(Membresia.class).filter("idLote", idLote).filter("estatus","ACTIVA").list();
	}

	@Override
	public List<Membresia> byLoteI(Long idLote) {
		return ofy().load().type(Membresia.class).filter("idLote", idLote).filter("estatus","INACTIVA").list();
	}

	@Override
	public Membresia byQr(String qr) {
		 List<Membresia> l= ofy().load().type(Membresia.class).filter("qr", qr).list();
		 if (l.size()>0){
			 return l.get(0);
		 }else 
			 return null;
		 
		
	}

	@Override
	public List<Membresia> consultarElec() {
		return ofy().load().type(Membresia.class).filter("tipo", "Electronico").list();
		
		
	}

	
	
	

}
