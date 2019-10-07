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
import com.tikal.subebus.util.Util;
import com.tikal.subebus.util.AsignadorDeCharset;

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
		 System.out.println("json yisus"+json);
			Lote l = (Lote) JsonConvertidor.fromJson(json, Lote.class);
			Calendar cal=Calendar.getInstance(TimeZone.getTimeZone("America/Mexico_City"));
			loteDao.guardar(l);
			for (int i=1; i<=l.getCantidad(); i++){
				Membresia m = new Membresia();
				m.setDuracion(l.getDuracion());
				m.setTipo(l.getTipo());
				m.setEstatus("INACTIVA");
				m.setIdLote(l.getId());
				Contador c = new Contador();
				m.setId(c.getFolio());
				c.incremeta();
				contadorDao.guardar(c);
				System.out.println("serial:"+c.getFolio());
				String cad= "folio:"+m.getId()+"lote:"+m.getIdLote()+"duracion:"+m.getDuracion()+"estatus:"+m.getEstatus()+"tipo:"+m.getTipo();
				System.out.println("cadena:"+cad);
				cad= Util.encripta(cad);
				System.out.println("cadena:"+cad);
				m.setQr(cad);
				byte[] Qr=Util.generate(cad);
				m.setCodigoQR(Qr);
				System.out.println("cadena:"+cad);
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
				m.setEstatus("INACTIVA");
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
		public void pdf(HttpServletResponse response, HttpServletRequest request, @PathVariable Long idLote) throws IOException {
	   
//	   if(SesionController.verificarPermiso2(request, usuarioDao, perfilDAO, 20, sessionDao,userName)){
		  AsignadorDeCharset.asignar(request, response);
		   response.setContentType("Application/Pdf");   
		   Lote l= loteDao.cargar(idLote);
		   List<Membresia> mems= memDao.byLote(idLote);
   
	        //Sucursal s= sucursalDao.consult(usuarioDao.consultarUsuario(userName).getIdSucursal());
	      //  Sucursal s= sucursalDao.consult(v.getIdSucursal());
//	        System.out.println("Empiezo a generar pdf..envios.."+objE );
//	        System.out.println("Empiezo a generar pdf...suc."+s );
//	        System.out.println("Empiezo a generar pdf...venta."+v );
	    	GeneraMembresias gm = new GeneraMembresias(l, mems, response.getOutputStream());
	 
	    	  response.getOutputStream().flush();
		        response.getOutputStream().close();
	    	
//	   }else{
//			response.sendError(403);
//		}
	}
	  
	  @RequestMapping(value = { "/delete/{idLote}" },  method = RequestMethod.GET, produces = "application/json")
		public void delete(HttpServletResponse response, HttpServletRequest request, @PathVariable Long idLote) throws IOException {
	   
//	   if(SesionController.verificarPermiso2(request, usuarioDao, perfilDAO, 20, sessionDao,userName)){
		  AsignadorDeCharset.asignar(request, response);
		   
		   Lote l= loteDao.cargar(idLote);
		   List<Membresia> mems= memDao.byLoteA(idLote);
		   if (mems.size()>0){
			   System.out.println("El lote ya tiene membresias activas, no se puede eliminar...");
			   response.getWriter().println("El lote ya tiene membresias activas, no se puede eliminar...");
		   }else{
			   loteDao.eliminar(l);
		   		List<Membresia> memI= memDao.byLoteI(idLote);
		   		System.out.println("mems inactivas"+memI);
		   	   for (Membresia m: memI){
		   		   memDao.eliminar(m);
		   	   }
		   	   System.out.println("Lote y membresias  eliminados...");
		   	   response.getWriter().println("lote eliminado");
	  		}
		    
	  }
}


