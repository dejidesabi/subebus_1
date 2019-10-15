package com.tikal.subebus.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tikal.subebus.dao.ContadorDao;
import com.tikal.subebus.dao.LoteDao;
import com.tikal.subebus.dao.MembresiaDao;
import com.tikal.subebus.dao.PerfilDAO;
import com.tikal.subebus.dao.RutaBusDao;
import com.tikal.subebus.dao.SessionDao;
import com.tikal.subebus.dao.UsuarioDao;

import com.tikal.subebus.modelo.entity.Lote;
import com.tikal.subebus.modelo.entity.RutaBus;
import com.tikal.subebus.util.JsonConvertidor;
import com.tikal.subebus.util.AsignadorDeCharset;

@Controller
@RequestMapping(value={"/ruta"})
public class RutaBusController {

	
	@Qualifier("usuarioDao")
	UsuarioDao usuarioDao;
	
	@Autowired
	@Qualifier("perfilDAO")
	PerfilDAO perfilDAO;
	
	 @Autowired
	 @Qualifier("sessionDao")
	 SessionDao sessionDao;

	 @Autowired
	 RutaBusDao rbDao;
	 
	 
	 @RequestMapping(value = {"/add" }, method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	 public void add(HttpServletRequest re, HttpServletResponse rs, @RequestBody String json) throws IOException, SQLException {
		//if(Util.verificarPermiso(re, usuariodao, perfildao, 2)){
			RutaBus r = (RutaBus) JsonConvertidor.fromJson(json, RutaBus.class);
			rbDao.guardar(r);
			System.out.println("se creó la ruta...");
//		}else{
//			rs.sendError(403);
//		}
//	 
	 }
	 
	 
	 @RequestMapping(value = { "/findAll" }, method = RequestMethod.GET, produces = "application/json")
		public void findAllSuc(HttpServletResponse response, HttpServletRequest request) throws IOException {
			AsignadorDeCharset.asignar(request, response);
			List<RutaBus> lista = rbDao.todos();
			if (lista == null) {
				lista = new ArrayList<RutaBus>();
			}
			response.getWriter().println(JsonConvertidor.toJson(lista));

		}
	 
	 @RequestMapping(value = { "/byChofer/{chofer}" }, method = RequestMethod.GET, produces = "application/json")
		public void byChofer(HttpServletResponse response, HttpServletRequest request, @PathVariable String chofer) throws IOException {
			AsignadorDeCharset.asignar(request, response);
			RutaBus rb= rbDao.byChofer(chofer);
		
			response.getWriter().println(JsonConvertidor.toJson(rb));

		}
	 
	 
}
