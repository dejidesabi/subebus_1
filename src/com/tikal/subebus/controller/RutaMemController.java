package com.tikal.subebus.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import com.tikal.subebus.modelo.entity.Venta;
import com.tikal.subebus.reportes.ReporteRutaMem;
import com.tikal.subebus.reportes.ReporteVentas;
import com.tikal.subebus.util.JsonConvertidor;
import com.tikal.subebus.util.AsignadorDeCharset;

@Controller
@RequestMapping(value={"/rutaMem"})
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
	 
		@RequestMapping(value = { "/xlsRM/{idSucursal}" }, method = RequestMethod.GET, produces = "application/vnd.ms-excel")
		public void xlsRM(HttpServletRequest re, HttpServletResponse rs, @PathVariable Long idSucursal) throws IOException{
			AsignadorDeCharset.asignar(re, rs);
						
		//	if(Util.verificarPermiso(re, usuariodao, perfildao, 2,5,6)){
			List<RutaMem> rms= new ArrayList<RutaMem>();
			if (idSucursal==9999){
				rms= rmDao.todos();
			}else{
				rms= rmDao.bySuc(idSucursal);
			}
			//List<RutaMem> rms= rmDao.bySuc(idSucursal);
			System.out.println("lista de rms:"+rms);
			//int hojas=lumiDao.hojasRep();
			//for (int i=1; i<=hojas; i++){
				ReporteRutaMem reporte= new ReporteRutaMem();
				HSSFWorkbook rep=reporte.getReporte(rms);
				rep.write(rs.getOutputStream());
				rs.getOutputStream().flush();
				rs.getOutputStream().close();
			//}
		}
		
		  @RequestMapping(value = { "/xlsRutaP/{idSucursal}/{inicio}/{fin}" }, method = RequestMethod.GET, produces = "aplication/vnd.ms-excel")
			public void xlsRutasP(HttpServletRequest re, HttpServletResponse rs, @PathVariable Long idSucursal,
					@PathVariable String inicio, @PathVariable String  fin) throws IOException, ParseException{
				AsignadorDeCharset.asignar(re, rs);
				
			//	if(Util.verificarPermiso(re, usuariodao, perfildao, 2,5,6)){
			//	 String perfil=usuarioDao.consultarUsuario(userName).getPerfil();
			    System.out.println("inicio"+inicio); System.out.println("fin"+fin); System.out.println("suc:"+idSucursal);
			      SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy"); //HH:mm:ss");
					//	try {
			      Date datei = formatter.parse(inicio);
					System.out.println("formatter inicio"+datei);
					Date datef = formatter.parse(fin);
					System.out.println("formatter fin"+datef);
					Calendar c = Calendar.getInstance();
					c.setTime(datef);
					c.add(Calendar.DATE, 1);
					datef = c.getTime();	
					List<RutaMem> lista= new ArrayList<RutaMem>();
					
				  if (idSucursal==9999){
					  System.out.println("ififififififif");
					  System.out.println("datei:"+datei);System.out.println("datef:"+datef);
					  lista= rmDao.periodoTodas(datei, datef);
					  System.out.println("rutas array:"+lista.size());
					  
				  }else{
					  System.out.println("eeeeeeee");
					  lista= rmDao.periodoSuc(datei, datef, idSucursal);
					  System.out.println("rutas array:"+lista.size());
				  }
				
			//	List<Venta> ventas= ventaDao.bySucursal(idSucursal);
				System.out.println("lista de rms:"+lista);
				//int hojas=lumiDao.hojasRep();
				//for (int i=1; i<=hojas; i++){
					ReporteRutaMem reporte= new ReporteRutaMem();
					HSSFWorkbook rep=reporte.getReporte(lista);
					rep.write(rs.getOutputStream());
					rs.getOutputStream().flush();
					rs.getOutputStream().close();
				//}
			}
		
	  
}
