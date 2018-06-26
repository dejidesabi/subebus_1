package com.tikal.mensajeria.controller;

import java.io.File;
import java.io.IOException;
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

import com.tikal.mensajeria.dao.EnvioDao;
import com.tikal.mensajeria.dao.GuiaDao;
import com.tikal.mensajeria.dao.PaqueteDao;
import com.tikal.mensajeria.dao.PersonaDao;
import com.tikal.mensajeria.dao.SucursalDao;
import com.tikal.mensajeria.dao.UsuarioDao;
import com.tikal.mensajeria.dao.VentaDao;
import com.tikal.mensajeria.formatos.pdf.GeneraTicket;
import com.tikal.mensajeria.modelo.entity.Contador;
import com.tikal.mensajeria.modelo.entity.Envio;
import com.tikal.mensajeria.modelo.entity.Guia;
import com.tikal.mensajeria.modelo.entity.Paquete;
import com.tikal.mensajeria.modelo.entity.Persona;
import com.tikal.mensajeria.modelo.entity.Venta;
import com.tikal.mensajeria.modelo.login.Sucursal;
import com.tikal.mensajeria.modelo.login.Usuario;
import com.tikal.mensajeria.modelo.vo.EnvioVo;
import com.tikal.mensajeria.util.JsonConvertidor;
import com.tikal.util.AsignadorDeCharset;

@Controller
@RequestMapping("/venta")
public class VentaController {
	
	@Autowired
	@Qualifier("usuarioDao")
	UsuarioDao usuarioDao;
	

	@Autowired
	@Qualifier("ventaDao")
	VentaDao ventaDao;

	
	@Autowired
	@Qualifier("sucursalDao")
	SucursalDao sucursalDao;
	
	@Autowired
	@Qualifier("paqueteDao")
	PaqueteDao paqueteDao;
	
	@Autowired
	
	@Qualifier("personaDao")
	PersonaDao personaDao;
	
	@Autowired
	@Qualifier("envioDao")
	EnvioDao envioDao;
	
	@Autowired
	@Qualifier("guiaDao")
	GuiaDao guiaDao;
	
	@RequestMapping(value={"/prueba"},method = RequestMethod.GET)
	   
	   public void prueba(HttpServletResponse response, HttpServletRequest request) throws IOException {
		   response.getWriter().println("Prueba del mètodo Venta prueba");

	    }
	
	@RequestMapping(value = { "/add_/{username}" },  method = RequestMethod.GET)
	public void ventasadd(HttpServletRequest request, HttpServletResponse response, 
			@PathVariable String username)throws IOException {
		System.out.println("si entra aqui en alta venta:");
	//				if(SesionController.verificarPermiso2(request, usuarioDao, perfilDAO, 45, sessionDao,userName)){
		AsignadorDeCharset.asignar(request, response);
		//Venta v = (Venta) JsonConvertidor.fromJson(json, Venta.class);
		Venta v= new Venta();
		Usuario u= usuarioDao.consultarUsuario(username);
		List<Long> envios= new ArrayList<Long>();
		Envio e1 = new Envio();
		Persona c=new Persona();
		Persona d =new Persona();
		Paquete pq = new Paquete();
		
		c.setNombre("Angelica Pacheco");
		c.setCalle("ejercito del trabajo");
		c.setNoExterior("130");
		c.setNoInterior("0");
		c.setColonia("gpe");
		c.setLocalidad("san buena");
		c.setMunicipio("toluca");
		c.setEstado("mexico");
		c.setCodigoPostal(50110);
		c.setReferencias("frente a minisuper");
		c.setTelefono("4890104");
		personaDao.save(c);
		e1.setCliente(c);
		
		
		d.setNombre("Ma. del Carmen PAntoja");
		d.setCalle("melero y piña");
		d.setNoExterior("870");
		d.setNoInterior("0");
		d.setColonia("san sebastian");
		d.setLocalidad("toluca");
		d.setMunicipio("toluca");
		d.setEstado("mexico");
		d.setCodigoPostal(50050);
		d.setReferencias("junto a gasolinerar");
		d.setTelefono("4875567");
		personaDao.save(d);
		e1.setDestinatario(d);
		
		pq.setAlto(10.00);pq.setAncho(10.00); pq.setLargo(10.00);
		pq.setDescripcion("paquete de joyeria");
		pq.setPeso(3.0);
		pq.setTipoPaquete("paquete");
		paqueteDao.save(pq);
		e1.setPaquete(pq);
		
		e1.setEmpresa("ESTAFETA");
		Guia g=guiaDao.getByEstSuc("ASIGNADA", usuarioDao.consultarUsuario(username).getIdSucursal());
		System.out.println("guia:"+g.getNumero());
		e1.setGuia(g.getNumero());
		e1.setRastreo(111111);
		e1.setTipoEnvio("PAQUETE");
		e1.setTipoServicio("NACIONAL");
		e1.setPrecio(312.50);
		e1.setId(Long.parseLong("1111111"));
		
		envioDao.save(e1);
		envios.add(e1.getId());
		
		v.setEnvios(envios);
		v.setCantidad(1);
		//Envio e2 = new Envio();
		Contador folio= new Contador();
		v.setFolio(folio.getFolio());
		v.setFecha("25/06/2018");
		
		v.setEstatus("ABIERTA");
		
		v.setUsuario(u);
		
		
		
		//System.out.println(" yisus manda:"+json);
//		int ini=Integer.parseInt(inicio);
//		int f=Integer.parseInt(fin);
			
		
			ventaDao.save(v); 
			g.setEstatus("EN ENVIO");
			guiaDao.update(g);
	
	
//			} else {
//				response.sendError(403);
//			}
		}
	
	@RequestMapping(value = { "/add/{username}" },  method = RequestMethod.GET ,consumes = "Application/Json")
	public void altas(HttpServletRequest request, HttpServletResponse response, 
			@PathVariable String username,@RequestBody String json)throws IOException {
	
	//				if(SesionController.verificarPermiso2(request, usuarioDao, perfilDAO, 45, sessionDao,userName)){
		AsignadorDeCharset.asignar(request, response);
		Venta v = (Venta) JsonConvertidor.fromJson(json, Venta.class);
		Contador folio= new Contador();
		v.setFolio(folio.getFolio());
		v.setEstatus("ABIERTA");
		//System.out.println(" yisus manda:"+json);
//		int ini=Integer.parseInt(inicio);
//		int f=Integer.parseInt(fin);
			
		
			ventaDao.save(v); 
			folio.incremeta();
	
//			} else {
//				response.sendError(403);
//			}
		}
	
	
	
	
	
	@RequestMapping(value = { "/cancelar/{idVenta}" },  method = RequestMethod.GET)
	public void asignaM(HttpServletRequest request, HttpServletResponse response, 
			@PathVariable Long idVenta )throws IOException {
	
	//				if(SesionController.verificarPermiso2(request, usuarioDao, perfilDAO, 45, sessionDao,userName)){
		AsignadorDeCharset.asignar(request, response);
		
			Venta v = ventaDao.consult(idVenta);
			v.setEstatus("CANCELADA");
			ventaDao.update(v);
			//System.out.println(" yisus manda:"+json);
		
		
	}
	
	 @RequestMapping(value = { "/findAll" }, method = RequestMethod.GET, produces = "application/json")
		public void findAll(HttpServletResponse response, HttpServletRequest request) throws IOException {
			AsignadorDeCharset.asignar(request, response);
			List<Venta> lista = ventaDao.findAll();
			if (lista == null) {
				lista = new ArrayList<Venta>();
			}
			response.getWriter().println(JsonConvertidor.toJson(lista));

		}
	 
	  @RequestMapping(value = { "/getFolio" },  method = RequestMethod.GET, produces = "application/json")
			public void getFolio(HttpServletResponse response, HttpServletRequest request) throws IOException {
		   //System.out.println("dame guia");
			   Contador folio= new Contador();
			   //folio.getFolio();
		  //response.getWriter().println(g.getNumero());
		  response.getWriter().println(JsonConvertidor.toJson(folio.getFolio()));
		

//		   if(SesionController.verificarPermiso2(request, usuarioDao, perfilDAO, 20, sessionDao,userName)){
			  
	  }

	  @RequestMapping(value = { "/generaTicket/{idVenta}/{userName}" },  method = RequestMethod.GET, produces = "application/pdf")
		public void generaVale(HttpServletResponse response, HttpServletRequest request, @PathVariable Long idVenta, @PathVariable String userName) throws IOException {
	   System.out.println("genera ticket");
//	   if(SesionController.verificarPermiso2(request, usuarioDao, perfilDAO, 20, sessionDao,userName)){
		   response.setContentType("Application/Pdf");
		 //  Venta v = ventaDao.consult(idVenta);
		 // Envio e = envioDao.consult(idEnvio) ; 
		   List<Envio> objE= new ArrayList<Envio>();
		    List<Long> envios = ventaDao.consult(idVenta).getEnvios();
			for (Long envio: envios){
				Envio e=envioDao.consult(envio);
				objE.add(e);
			}
				   
	        File newPdfFile = new File(idVenta+".pdf");		 
	        if (!newPdfFile.exists()){
	            try {
	            	newPdfFile.createNewFile();
	            } catch (IOException ioe) {
	                System.out.println("(Error al crear el fichero nuevo ......)" + ioe);
	            }
	        }
     
	        Sucursal s= sucursalDao.consult(usuarioDao.consultarUsuario(userName).getIdSucursal());
	      //  List<Envio> es= new ArrayList<Envio>();
	        
	        
	       // Paquete p = v.getPaquete();  
	     //   String des = e.paquete.paqueteDao.consult(e.getPaquete().getDescripcion());
	        System.out.println("Empiezo a generar pdf...." );
	    	GeneraTicket gt = new GeneraTicket(objE, s,  response.getOutputStream());
	    //ystem.out.println("nombre de archivo para edgar:"+tik.getNombreArchivo().substring(10) );
	    	//response.getWriter().println((vpdf.getNombreArchivo().substring(10)));
	    	  response.getOutputStream().flush();
		        response.getOutputStream().close();
	    	//generaOrdenPdf.GeneraOrdenPdf(new File(ox.getNombreArchivo()));
	    	//generaOrdenPdf.GeneraOrdenPdf(ox));
//	   }else{
//			response.sendError(403);
//		}
	}
	 

}
