package com.tikal.subebus.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import com.tikal.subebus.dao.AlertaDAO;
import com.tikal.subebus.dao.FolioDao;
import com.tikal.subebus.dao.LoteDao;
import com.tikal.subebus.dao.MembresiaDao;
import com.tikal.subebus.dao.PerfilDAO;
import com.tikal.subebus.dao.RutaBusDao;
import com.tikal.subebus.dao.RutaMemDao;
import com.tikal.subebus.dao.SessionDao;
import com.tikal.subebus.dao.UsuarioDao;
import com.tikal.subebus.dao.VentaDao;
import com.tikal.subebus.modelo.entity.Alerta;
import com.tikal.subebus.modelo.entity.Lote;
import com.tikal.subebus.modelo.entity.Membresia;
import com.tikal.subebus.modelo.entity.RutaBus;
import com.tikal.subebus.modelo.entity.RutaMem;
import com.tikal.subebus.modelo.entity.Venta;
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
	 
	 @Autowired
	 FolioDao folioDao;
	 
	 @Autowired
	 RutaMemDao rmDao;
	 
	 @Autowired
	 RutaBusDao rbDao;
	 
	 @Autowired
	 VentaDao ventaDao;
		
	 @Qualifier("usuarioDao")
	 UsuarioDao usuarioDao;
		
	@Autowired
	@Qualifier("perfilDAO")
	PerfilDAO perfilDAO;
		
	 @Autowired
	 @Qualifier("sessionDao")
	 SessionDao sessionDao; 
	 
	 @Autowired
	 AlertaDAO alertaDao;
	 
	 @RequestMapping(value = {"/add" }, method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	 public void add(HttpServletRequest re, HttpServletResponse rs, @RequestBody String json) throws IOException, SQLException {
		//if(Util.verificarPermiso(re, usuariodao, perfildao, 2)){
			Membresia m = (Membresia) JsonConvertidor.fromJson(json, Membresia.class);
			
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
	  ////asigna membresias x duracion y sucursal
	  @RequestMapping(value = "/byDTS/{duracion}/{idSucursal}", method = RequestMethod.GET)
		public void byds(HttpServletRequest req, HttpServletResponse res, @PathVariable String duracion, @PathVariable Long idSucursal) throws IOException {
		
		  Membresia m = memDao.byDTS(duracion, "Electronico", idSucursal).get(0); // no activas solamente (para venta)
		 // System.out.println("lista:"+lista);
		  if(m.equals(null)){
			  res.getWriter().println("no hay membresias disponibles para esa sucursal...");
		  }else{
			  res.getWriter().println(JsonConvertidor.toJson(m));
		  }
		}
	  
	  @RequestMapping(value = "/asignar/{duracion}/{tipo}/{idSucursal}", method = RequestMethod.GET)
			public void bydts(HttpServletRequest req, HttpServletResponse res, @PathVariable String duracion, @PathVariable String tipo, @PathVariable Long idSucursal) throws IOException {
			
			  Membresia m = memDao.byDTS(duracion,tipo, idSucursal).get(0); // no activas solamente (para venta)
			 // System.out.println("lista:"+lista);
			  if(m.equals(null)){
				  res.getWriter().println("no hay membresias "+tipo+" disponibles para esa sucursal...");
			  }else{
				  res.getWriter().println(JsonConvertidor.toJson(m));
			  }
			}
	  
	  @RequestMapping(value = "/byFolio/{idMembresia}", method = RequestMethod.GET)
		public void byfolio(HttpServletRequest req, HttpServletResponse res, @PathVariable Long idMembresia) throws IOException {
		 Membresia m = memDao.consultar(idMembresia);
			res.getWriter().println(JsonConvertidor.toJson(m));
	  }
	  
	 /////////  checa si esta activa o no la membresia  ANDROID
	  @RequestMapping(value = "/byQR/{qr}/{idRutaBus}", method = RequestMethod.GET)
			public void checkqr(HttpServletRequest req, HttpServletResponse res, @PathVariable String qr, @PathVariable Long idRutaBus) throws IOException {
		  System.out.println("checando al abordar....");
			 Membresia m = memDao.byQr(qr);
			 if (m.getEstatus().equals("INACTIVA") && m.getDuracion().equals("Dia")){
				 System.out.println("Se activará la membresia conveniente por este dia...");
				 m.setEstatus("ACTIVA");
				 memDao.actualizar(m);
			 }
			 RutaBus rb= rbDao.cargar(idRutaBus);
			 crearRutaMem(m,rb);
	
		   if(m.getEstatus().equals("EN USO")){
			   Alerta a= new Alerta();
			   a.idproducto=m.getId();
			   a.nombre="--";
			   a.alerta="LA MEMBRESIA "+m.getId()+" ESTA INTENTANDO ACCESAR EN VARIAS RUTAS...";
			   alertaDao.add(a);
			   System.out.println("Intento de fraude.... Esta membresia ya esta en uso...");
		   }
		   res.getWriter().println(JsonConvertidor.toJson(m.getEstatus()));
			/////// pasar a estatus= "EN USO"
			m.setEstatus("EN USO");
			Calendar cal=Calendar.getInstance(TimeZone.getTimeZone("America/Mexico_City"));
		//	cal.add(Calendar.HOUR_OF_DAY, -6);
			System.out.println("fechaActivacion:"+cal.getTime());
			m.setIniUso(cal.getTime());
			m.setFinUso(sumarDias(m.getIniUso(),"Uso"));
			memDao.actualizar(m);
				
			
	  }
	  
	  @RequestMapping(value = "/byLote/{idLote}", method = RequestMethod.GET)
		public void bylote(HttpServletRequest req, HttpServletResponse res, @PathVariable Long idLote) throws IOException {
		  List<Membresia> lista = memDao.byLote(idLote);
			res.getWriter().println(JsonConvertidor.toJson(lista));
	  }
	   
	  @RequestMapping(value = "/desactivar/{folio}", method = RequestMethod.GET)
		public void desactivar(HttpServletRequest req, HttpServletResponse res, @PathVariable Long folio) throws IOException {
			Membresia m=memDao.consultar(folio);
			m.setEstatus("CADUCADA");
			memDao.actualizar(m);
			res.getWriter().print("membresia desactivada....");
		}
	  
	  @RequestMapping(value = {"/renovar/{folio}" }, method = RequestMethod.GET, produces = "application/json")
		 public void renovar(HttpServletRequest re, HttpServletResponse rs, @PathVariable Long folio) throws IOException, SQLException {
			//if(Util.verificarPermiso(re, usuariodao, perfildao, 2)){
				Venta v = ventaDao.byMembresia(folio);
				
//				System.out.println("yisus trae:"+json);
			Calendar cal=Calendar.getInstance(TimeZone.getTimeZone("America/Mexico_City"));
		//	cal.add(Calendar.HOUR_OF_DAY, -6);
				System.out.println("fechaActivacion:"+cal.getTime());
			
				Membresia m= memDao.consultar(v.getIdMembresia());
				m.setFechaActivacion(cal.getTime());
				m.setFechaCaducidad(sumarDias(m.getFechaActivacion(),m.getDuracion()));
				System.out.println("fechaCaducidad:"+m.getFechaCaducidad());
				m.setEstatus("ACTIVA");
				//ventaDao.guardar(v);
				Venta nueva=crearVenta(v,m.getFechaCaducidad());
				 m.setIdVenta(nueva.getId());
				 memDao.actualizar(m);
				 
				 
//			}else{
//				rs.sendError(403);
//			}
		 
		 } 
	  
	  
	  public void crearRutaMem(Membresia m , RutaBus rb){
		  
		  Calendar cal=Calendar.getInstance(TimeZone.getTimeZone("America/Mexico_City"));
			//cal.add(Calendar.HOUR_OF_DAY, -6);
			System.out.println(" creando ruta mem ...hora:"+cal.getTime());
		  
		Venta v= ventaDao.byMembresia(m.getId());
		
			
		  RutaMem rm= new RutaMem();
		  rm.setIdRutaBus(rb.getId());
		  rm.setRuta(rb.getRuta());
		  rm.setChofer(rb.getChofer());
		  rm.setMembresia(m.getId());
		  rm.setDuracion(m.getDuracion());
		  rm.setFecha(cal.getTime());//		 
		  rm.setSucursal(m.getIdSucursal());
		  rm.setVenta(v.getId());
		  rm.setNombre(v.getNombre());
		  rmDao.guardar(rm);
		  System.out.println("guardada rm");

	  }		
	  
	  public Date sumarDias(Date fecha, String duracion){
			 int dias=0;
			 Calendar calendar = Calendar.getInstance();		 	
			 calendar.setTime(fecha); // Configuramos la fecha que se recibe		 
			 switch(duracion){
			 	case "Dia": //dias=1;
								 	// Calendar calendar = Calendar.getInstance();		 	
									// calendar.setTime(fecha); // Configuramos la fecha que se recibe	
								//	 System.out.println("fecha hoy:"+calendar.getTime());
									// calendar.set(Calendar.DAY_OF_MONTH, fecha.getDay());
									 calendar.set(Calendar.HOUR,11);  // numero de días a añadir, o restar en caso de días<0
									
									 calendar.set(Calendar.MINUTE,59);
									 calendar.set(Calendar.SECOND,59);
									 //calendar.add(Calendar.DAY_OF_MONTH, -1);
									 System.out.println("fecha  hoy fin:"+calendar.getTime()); 
									 return calendar.getTime();
				    //break;
			 	case "Semanal":dias=7;
			 		break;
			 	case "Mensual":dias=30;
			 		break;
			 	case "Semestral":dias=180;
		 			break;
			 	case "Uso" :
			 		//Calendar calendar = Calendar.getInstance();		 	
					// calendar.setTime(fecha); // Configuramos la fecha que se recibe	
				//	 System.out.println("fecha hoy:"+calendar.getTime());
					// calendar.set(Calendar.DAY_OF_MONTH, fecha.getDay());
					// calendar.set(Calendar.HOUR,11);  // numero de días a añadir, o restar en caso de días<0
					
					// calendar.set(Calendar.MINUTE,10);
					// calendar.set(Calendar.SECOND,59);
					 //calendar.add(Calendar.DAY_OF_MONTH, -1);
					 calendar.add(Calendar.MINUTE, 10); 
					 System.out.println("fecha  hoy fin:"+calendar.getTime()); 
					 return calendar.getTime();
			 		
				 
			 }
			 
			// Calendar calendar = Calendar.getInstance();		 	
			// calendar.setTime(fecha); // Configuramos la fecha que se recibe		 	
			 calendar.add(Calendar.DAY_OF_YEAR, dias);  // numero de días a añadir, o restar en caso de días<0		 	
			 return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos
		}
	  
	  public Venta crearVenta(Venta v, Date cad){
			Calendar cal=Calendar.getInstance(TimeZone.getTimeZone("America/Mexico_City"));
		  Venta vn= new Venta();
		  vn.setDuracion(v.getDuracion());
		  vn.setEdad(v.getEdad());
		  vn.setIdMembresia(v.getIdMembresia());
		  vn.setIdSucursal(v.getIdSucursal());
		  vn.setMail(v.getMail());
		  vn.setNombre(v.getNombre());
		  vn.setPrecio(v.getPrecio());
		  vn.setSector(v.getSector());
		  vn.setSexo(v.getSexo());
		  vn.setTelefono(v.getTelefono());
		  vn.setTipo(v.getTipo());
		  vn.setUser(v.getUser());
		  vn.setCaducidadVenta(cad);
		  vn.setFecha(cal.getTime());
		  ventaDao.guardar(vn);
		  return vn;
	  }
}
