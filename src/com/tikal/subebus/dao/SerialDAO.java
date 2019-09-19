package com.tikal.subebus.dao;

import java.util.List;

import com.tikal.subebus.modelo.entity.Serial;

public interface SerialDAO {
	public void guardar(Serial s);
	//public Serial consultar(String rfc, String serie);
	//public List<Serial> consultar(String rfc);
	public void eliminar(String id);
}
