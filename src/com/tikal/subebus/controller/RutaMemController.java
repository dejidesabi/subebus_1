package com.tikal.subebus.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tikal.subebus.dao.PerfilDAO;
import com.tikal.subebus.dao.RutaBusDao;
import com.tikal.subebus.dao.RutaMemDao;
import com.tikal.subebus.dao.SessionDao;
import com.tikal.subebus.dao.UsuarioDao;
import com.tikal.subebus.modelo.entity.Lote;
import com.tikal.subebus.modelo.entity.RutaBus;
import com.tikal.subebus.modelo.entity.RutaMem;
import com.tikal.subebus.util.JsonConvertidor;
import com.tikal.subebus.util.AsignadorDeCharset;

@Controller
@RequestMapping(value={"/rutaQr"})
public class RutaMemController {
	
	@Qualifier("usuarioDao")
	UsuarioDao usuarioDao;
	
	@Autowired
	@Qualifier("perfilDAO")
	PerfilDAO perfilDAO;
	
	 @Autowired
	 @Qualifier("sessionDao")
	 SessionDao sessionDao;

	 @Autowired
	 RutaMemDao rmDao;
	
	
	
	 @RequestMapping(value = {"/add" }, method = RequestMethod.POST, consumes = "application/json",produces = "application/json")
	 public void add(HttpServletRequest re, HttpServletResponse rs, @RequestBody String json) throws IOException, SQLException {
		//if(Util.verificarPermiso(re, usuariodao, perfildao, 2)){
			RutaMem r = (RutaMem) JsonConvertidor.fromJson(json, RutaMem.class);
			rmDao.guardar(r);
			System.out.println("se registro un uso de membresia en la ruta designada.....");
//		}else{
//			rs.sendError(403);
//		}
//	 
	 }
	 
	 
	 @RequestMapping(value = { "/findAll" }, method = RequestMethod.GET, produces = "application/json")
		public void findAll(HttpServletResponse response, HttpServletRequest request) throws IOException {
			AsignadorDeCharset.asignar(request, response);
			List<RutaMem> lista = rmDao.todos();
			if (lista == null) {
				lista = new ArrayList<RutaMem>();
			}
			response.getWriter().println(JsonConvertidor.toJson(lista));

		}
	
	 @RequestMapping(value = { "/findAllS" }, method = RequestMethod.POST, consumes = "application/json",produces = "application/json")
		public void findAllSuc(HttpServletResponse response, HttpServletRequest request) throws IOException {
			AsignadorDeCharset.asignar(request, response);
			List<RutaMem> lista = rmDao.todos();
			if (lista == null) {
				lista = new ArrayList<RutaMem>();
			}
			response.getWriter().println(JsonConvertidor.toJson(lista));

		}
	
	
	
	 @RequestMapping(value = "/numPaginas", method = RequestMethod.GET)
		public void numpags(HttpServletRequest req, HttpServletResponse res) throws IOException {
			int paginas =rmDao.pags();
			res.getWriter().print(paginas);
		}
	  
	  @RequestMapping(value = { "/findAllP/{page}" }, method = RequestMethod.GET, produces = "application/json")
		public void findAllByPage(HttpServletResponse response, HttpServletRequest request, @PathVariable int page) throws IOException {
			AsignadorDeCharset.asignar(request, response);
			List<RutaMem> lista = rmDao.findAllPage(page);
			if (lista == null) {
				lista = new ArrayList<RutaMem>();
			}
			response.getWriter().println(JsonConvertidor.toJson(lista));
		}
	 

	  
}
