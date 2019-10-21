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

import com.tikal.subebus.dao.LoteDao;
import com.tikal.subebus.dao.MembresiaDao;
import com.tikal.subebus.dao.PerfilDAO;
import com.tikal.subebus.dao.SessionDao;
import com.tikal.subebus.dao.UsuarioDao;
import com.tikal.subebus.modelo.entity.Lote;
import com.tikal.subebus.modelo.entity.Membresia;
import com.tikal.subebus.modelo.login.Sucursal;
import com.tikal.subebus.util.JsonConvertidor;
import com.tikal.subebus.util.AsignadorDeCharset;

@Controller
@RequestMapping (value= {"/membresia"})
public class MembresiaController {
	
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
	 
	 @RequestMapping(value = {"/add" }, method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	 public void add(HttpServletRequest re, HttpServletResponse rs, @RequestBody String json) throws IOException, SQLException {
		//if(Util.verificarPermiso(re, usuariodao, perfildao, 2)){
			Membresia m = (Membresia) JsonConvertidor.fromJson(json, Membresia.class);
			Calendar cal=Calendar.getInstance(TimeZone.getTimeZone("America/Mexico_City"));
			memDao.crear(m);
//		}else{
//			rs.sendError(403);
//		}
//	 
	 }

	 
	 @RequestMapping(value = { "/findAll" }, method = RequestMethod.GET, produces = "application/json")
		public void findAllSuc(HttpServletResponse response, HttpServletRequest request) throws IOException {
			AsignadorDeCharset.asignar(request, response);
			List<Membresia> lista = memDao.consultarTodos();
			if (lista == null) {
				lista = new ArrayList<Membresia>();
			}
			response.getWriter().println(JsonConvertidor.toJson(lista));

		}
	 
	 @RequestMapping(value = "/numPaginas", method = RequestMethod.GET)
		public void numpags(HttpServletRequest req, HttpServletResponse res) throws IOException {
			int paginas = memDao.pags();
			res.getWriter().print(paginas);
		}
	  
	  @RequestMapping(value = { "/findAllP/{page}" }, method = RequestMethod.GET, produces = "application/json")
		public void findAllByPage(HttpServletResponse response, HttpServletRequest request, @PathVariable int page) throws IOException {
			AsignadorDeCharset.asignar(request, response);
			List<Membresia> lista = memDao.findAllPage(page);
			if (lista == null) {
				lista = new ArrayList<Membresia>();
			}
			response.getWriter().println(JsonConvertidor.toJson(lista));
		}
	  
	  @RequestMapping(value = "/byDTS/{duracion}/{idSucursal}", method = RequestMethod.GET)
		public void bydp(HttpServletRequest req, HttpServletResponse res, @PathVariable String duracion, @PathVariable Long idSucursal) throws IOException {
		
		  Membresia m = memDao.byDTS(duracion, "Electronico", idSucursal).get(0); // no activas solamente (para venta)
		 // System.out.println("lista:"+lista);
		  if(m.equals(null)){
			  res.getWriter().println("no hay membresias disponibles para esa sucursal...");
		  }else{
			  res.getWriter().println(JsonConvertidor.toJson(m));
		  }
		}
	  
	  @RequestMapping(value = "/byQR/{qr}", method = RequestMethod.GET)
			public void byqr(HttpServletRequest req, HttpServletResponse res, @PathVariable String qr) throws IOException {
			 Membresia m = memDao.byQr(qr);
				res.getWriter().println(JsonConvertidor.toJson(m));
	  }
	  
	  @RequestMapping(value = "/byLote/{idLote}", method = RequestMethod.GET)
		public void bylote(HttpServletRequest req, HttpServletResponse res, @PathVariable Long idLote) throws IOException {
		  List<Membresia> lista = memDao.byLote(idLote);
			res.getWriter().println(JsonConvertidor.toJson(lista));
	  }
	  
	  
	  
}
