package com.tikal.subebus.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tikal.subebus.dao.LoteDao;
import com.tikal.subebus.dao.MembresiaDao;
import com.tikal.subebus.dao.PerfilDAO;
import com.tikal.subebus.dao.SessionDao;
import com.tikal.subebus.dao.UsuarioDao;
import com.tikal.subebus.dao.VentaDao;
import com.tikal.subebus.modelo.entity.Membresia;
import com.tikal.subebus.modelo.entity.Venta;
import com.tikal.subebus.util.JsonConvertidor;

@Controller
@RequestMapping (value={"/venta"})
public class VentaController {
	
	 @Autowired
	 LoteDao loteDao;
	 
	 @Autowired
	 MembresiaDao memDao;
		
	 @Qualifier("usuarioDao")
	 UsuarioDao usuarioDao;
		
	@Autowired
	@Qualifier("perfilDAO")
	PerfilDAO perfilDAO;
		
	 @Autowired
	 @Qualifier("sessionDao")
	 SessionDao sessionDao; 
	 
	 @Autowired
	 VentaDao ventaDao;
	
	 
	 @RequestMapping(value = {"/add" }, method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	 public void add(HttpServletRequest re, HttpServletResponse rs, @RequestBody String json) throws IOException, SQLException {
		//if(Util.verificarPermiso(re, usuariodao, perfildao, 2)){
			Venta v = (Venta) JsonConvertidor.fromJson(json, Venta.class);
			Calendar cal=Calendar.getInstance(TimeZone.getTimeZone("America/Mexico_City"));
			Membresia m= memDao.consultar(v.getIdMembresia());
			m.setEstatus("ACTIVA");
		ventaDao.guardar(v);
//		}else{
//			rs.sendError(403);
//		}
	 
	 }
	 
	 
	 
	 
}
