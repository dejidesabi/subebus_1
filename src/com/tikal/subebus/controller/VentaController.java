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

import com.tikal.subebus.dao.LoteDao;
import com.tikal.subebus.dao.MembresiaDao;
import com.tikal.subebus.dao.PerfilDAO;
import com.tikal.subebus.dao.SessionDao;
import com.tikal.subebus.dao.SucursalDao;
import com.tikal.subebus.dao.UsuarioDao;
import com.tikal.subebus.dao.VentaDao;
import com.tikal.subebus.formatos.pdf.GeneraTicket;
import com.tikal.subebus.modelo.entity.Lote;
import com.tikal.subebus.modelo.entity.Membresia;
import com.tikal.subebus.modelo.entity.Venta;
import com.tikal.subebus.modelo.login.Sucursal;
import com.tikal.subebus.modelo.login.Usuario;
import com.tikal.subebus.util.AsignadorDeCharset;
import com.tikal.subebus.util.JsonConvertidor;

@Controller
@RequestMapping (value={"/venta"})
public class VentaController {
	
	 @Autowired
	 LoteDao loteDao;
	 
	 @Autowired
	 MembresiaDao memDao;
	 
	 @Autowired
	 SucursalDao sucDao;
		
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
			System.out.println("yisus trae:"+json);
			Calendar cal=Calendar.getInstance(TimeZone.getTimeZone("America/Mexico_City"));
			System.out.println("fechaActivacion:"+cal.getTime());
			if (v.getTipo().equals("Electronico")){
				v.setTipo("Electronico");
				
			} 
			if(v.getTipo().equals("Fisico")){
				v.setTipo("Fisico");
			}
			if(v.getTipo().equals("Conveniente")){
				v.setTipo("Conveniente");
			}
			Membresia m= memDao.consultar(v.getIdMembresia());
			m.setFechaActivacion(cal.getTime());
			m.setFechaCaducidad(sumarDias(m.getFechaActivacion(),m.getDuracion()));
			System.out.println("fechaCaducidad:"+m.getFechaCaducidad());
			m.setEstatus("ACTIVA");
			ventaDao.guardar(v);
			 m.setIdVenta(v.getId());
			 memDao.actualizar(m);
//		}else{
//			rs.sendError(403);
//		}
	 
	 } 
	  
	 
	 
	 @RequestMapping(value = { "/bySucursal" }, method = RequestMethod.POST,consumes = "application/json", produces = "application/json")
		public void bysuc(HttpServletResponse response, HttpServletRequest request, @RequestBody String json) throws IOException {
			AsignadorDeCharset.asignar(request, response);
			Usuario u = (Usuario) JsonConvertidor.fromJson(json, Usuario.class);
			System.out.println("usuariooo:"+u);
		//	Usuario u = usuarioDao.byId(idUsuario);
		//	Usuario u = usuarioDao.consult(idUsuario);
			System.out.println("suc usuario:"+u.getIdSucursal());
			List<Venta> lista= new ArrayList<Venta>();
			if(u.getIdSucursal()==9999){
				lista = ventaDao.todas();
			}else{
				lista = ventaDao.bySucursal(u.getIdSucursal());
			}
			
			response.getWriter().println(JsonConvertidor.toJson(lista));

		} 
	 
	 
	  //@RequestMapping(value = { "/generaTicket/{idVenta}/{userName}" },  method = RequestMethod.GET, produces = "application/pdf")
	  @RequestMapping(value = { "/generaTicket/{idVenta}" },  method = RequestMethod.GET, produces = "application/pdf")
			public void generaTicket(HttpServletResponse response, HttpServletRequest request, @PathVariable Long idVenta) throws IOException {
		   
//		   if(SesionController.verificarPermiso2(request, usuarioDao, perfilDAO, 20, sessionDao,userName)){
			   response.setContentType("Application/Pdf");
			   
			   Venta v = ventaDao.cargar( idVenta);
		//	   System.out.println("genera ticket con precio"+v.getTotal());
			 // Envio e = envioDao.consult(idEnvio) ; 
//			   List<Envio> objE= new ArrayList<Envio>();
//			    List<Long> envios = ventaDao.consult(idVenta).getEnvios();
//				for (Long envio: envios){
//					Envio e=envioDao.consult(envio);
//					objE.add(e);
//				}
//					   
//		        File newPdfFile = new File(idVenta+".pdf");		 
//		        if (!newPdfFile.exists()){ 
//		            try {
//		            	newPdfFile.createNewFile();
//		            } catch (IOException ioe) {
//		                System.out.println("(Error al crear el fichero nuevo ......)" + ioe);
//		            }
//		        }
//		        
	     
		        //Sucursal s= sucursalDao.consult(usuarioDao.consultarUsuario(userName).getIdSucursal());
		        Sucursal s= sucDao.consult(v.getIdSucursal());
		      //  System.out.println("Empiezo a generar pdf..envios.."+objE );
		        System.out.println("Empiezo a generar pdf...suc."+s );
		        System.out.println("Empiezo a generar pdf...venta."+v );
		    	GeneraTicket gt = new GeneraTicket( v ,  response.getOutputStream());
		 
		    	  response.getOutputStream().flush();
			        response.getOutputStream().close();
		    	
//		   }else{
//				response.sendError(403);
//			}
		}

	 
	 
	 
	 
	 public Date sumarDias(Date fecha, String duracion){
		 int dias=0;
		 switch(duracion){
		 	case "Conveniente": dias=1;
							 	 Calendar calendar = Calendar.getInstance();		 	
								 calendar.setTime(fecha); // Configuramos la fecha que se recibe		 	
								 calendar.set(Calendar.HOUR,23);  // numero de días a añadir, o restar en caso de días<0
								 calendar.set(Calendar.MINUTE,59);
								 calendar.set(Calendar.SECOND,59);
								 return calendar.getTime();
			    //break;
		 	case "Semanal":dias=7;
		 		break;
		 	case "Mensual":dias=30;
		 		break;
			 
		 }
		 
		 Calendar calendar = Calendar.getInstance();		 	
		 calendar.setTime(fecha); // Configuramos la fecha que se recibe		 	
		 calendar.add(Calendar.DAY_OF_YEAR, dias);  // numero de días a añadir, o restar en caso de días<0		 	
		 return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos
	}
	 
}
