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
import com.tikal.subebus.dao.SerialDAO;
import com.tikal.subebus.dao.SessionDao;
import com.tikal.subebus.dao.UsuarioDao;
import com.tikal.subebus.formatos.pdf.GeneraMembresias;
import com.tikal.subebus.modelo.entity.Contador;
import com.tikal.subebus.modelo.entity.Lote;
import com.tikal.subebus.modelo.entity.Membresia;
import com.tikal.subebus.modelo.entity.Serial;
import com.tikal.subebus.modelo.login.Sucursal;
import com.tikal.subebus.util.JsonConvertidor;
import com.tikal.util.AsignadorDeCharset;

@Controller
@RequestMapping(value = { "/lote"})

public class LoteController {

	
	
	@Qualifier("usuarioDao")
	UsuarioDao usuarioDao;
	
	@Autowired
	@Qualifier("perfilDAO")
	PerfilDAO perfilDAO;
	
	 @Autowired
	 @Qualifier("sessionDao")
	 SessionDao sessionDao;

	 @Autowired
	 LoteDao loteDao;
	 
	 @Autowired
	 MembresiaDao memDao;
	 
	 @Autowired
	 ContadorDao contadorDao;
	 
	 
	 @RequestMapping(value = {"/add" }, method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	 public void add(HttpServletRequest re, HttpServletResponse rs, @RequestBody String json) throws IOException, SQLException {
		//if(Util.verificarPermiso(re, usuariodao, perfildao, 2)){
			Lote l = (Lote) JsonConvertidor.fromJson(json, Lote.class);
			Calendar cal=Calendar.getInstance(TimeZone.getTimeZone("America/Mexico_City"));
			loteDao.guardar(l);
			for (int i=1; i<=l.getCantidad(); i++){
				Membresia m = new Membresia();
				m.setDuracion(l.getDuracion());
				m.setEstatus("INACTIVO");
				m.setIdLote(l.getId());
				Contador c = new Contador();
				m.setId(c.getFolio());
				c.incremeta();
				contadorDao.guardar(c);
				System.out.println("serial:"+c.getFolio());
				memDao.crear(m);
				System.out.println("num mem:"+i);
			}
			System.out.println("se crearon "+l.getCantidad()+" membresias...");
//		}else{
//			rs.sendError(403);
//		}
//	 
	 }
	 
	 
	 @RequestMapping(value = {"/add_" }, method = RequestMethod.GET, produces = "application/json")
	 public void add_(HttpServletRequest re, HttpServletResponse rs) throws IOException, SQLException {
		//if(Util.verificarPermiso(re, usuariodao, perfildao, 2)){
			Lote l =new Lote();
			l.setDuracion("mensual");
			l.setIdSucursal(Long.parseLong("6333186975989760"));
			l.setTipo("Electrónico");
			l.setCantidad(3);
			
			loteDao.guardar(l);
			Calendar cal=Calendar.getInstance(TimeZone.getTimeZone("America/Mexico_City"));
			System.out.println("cantidad memb:"+l.getCantidad());
			for (int i=1; i<=l.getCantidad(); i++){
				Membresia m = new Membresia();
				m.setDuracion(l.getDuracion());
				m.setEstatus("INACTIVO");
				m.setIdLote(l.getId());
				Contador c = new Contador();
				m.setId(c.getFolio());
				c.incremeta();
				contadorDao.guardar(c);
				System.out.println("serial:"+c.getFolio());
				memDao.crear(m);
				System.out.println("num mem:"+i);
			}
			System.out.println("se crearon "+l.getCantidad()+" membresias...");
//		}else{
//			rs.sendError(403);
//		}
//	 
	 }
	 
	 
	 @RequestMapping(value = { "/findAll" }, method = RequestMethod.GET, produces = "application/json")
		public void findAllSuc(HttpServletResponse response, HttpServletRequest request) throws IOException {
			AsignadorDeCharset.asignar(request, response);
			List<Lote> lista = loteDao.todos();
			if (lista == null) {
				lista = new ArrayList<Lote>();
			}
			response.getWriter().println(JsonConvertidor.toJson(lista));

		}
	 
	 
	
	 @RequestMapping(value = "/numPaginas", method = RequestMethod.GET)
		public void numpags(HttpServletRequest req, HttpServletResponse res) throws IOException {
			int paginas =loteDao.pags();
			res.getWriter().print(paginas);
		}
	  
	  @RequestMapping(value = { "/findAllP/{page}" }, method = RequestMethod.GET, produces = "application/json")
		public void findAllByPage(HttpServletResponse response, HttpServletRequest request, @PathVariable int page) throws IOException {
			AsignadorDeCharset.asignar(request, response);
			List<Lote> lista = loteDao.findAllPage(page);
			if (lista == null) {
				lista = new ArrayList<Lote>();
			}
			response.getWriter().println(JsonConvertidor.toJson(lista));
		}
	 
	  
	  @RequestMapping(value = { "/print/{idLote}" },  method = RequestMethod.GET, produces = "application/pdf")
		public void generaVale(HttpServletResponse response, HttpServletRequest request, @PathVariable Long idLote) throws IOException {
	   
//	   if(SesionController.verificarPermiso2(request, usuarioDao, perfilDAO, 20, sessionDao,userName)){
		  AsignadorDeCharset.asignar(request, response);
		   response.setContentType("Application/Pdf");   
		   Lote l= loteDao.cargar(idLote);
		   
   
	        //Sucursal s= sucursalDao.consult(usuarioDao.consultarUsuario(userName).getIdSucursal());
	      //  Sucursal s= sucursalDao.consult(v.getIdSucursal());
//	        System.out.println("Empiezo a generar pdf..envios.."+objE );
//	        System.out.println("Empiezo a generar pdf...suc."+s );
//	        System.out.println("Empiezo a generar pdf...venta."+v );
	    	GeneraMembresias gm = new GeneraMembresias(l, response.getOutputStream());
	 
	    	  response.getOutputStream().flush();
		        response.getOutputStream().close();
	    	
//	   }else{
//			response.sendError(403);
//		}
	}

}


