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
		m.setEstatus("INACTIVO");
		actualizar(m);
	}

	@Override
	public List<Membresia> consultarTodos() {
		return ofy().load().type(Membresia.class).order("- fechaCaducidad").list();
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

}
