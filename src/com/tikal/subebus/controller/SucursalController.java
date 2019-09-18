package com.tikal.subebus.controller;

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

import com.tikal.subebus.dao.PerfilDAO;
import com.tikal.subebus.dao.SessionDao;
import com.tikal.subebus.dao.SucursalDao;
import com.tikal.subebus.dao.UsuarioDao;
import com.tikal.subebus.modelo.login.Perfil;
import com.tikal.subebus.modelo.login.Sucursal;
import com.tikal.util.AsignadorDeCharset;
import com.tikal.util.JsonConvertidor;

@Controller
@RequestMapping("/sucursal")
public class SucursalController {
	
	 
		@Autowired
		@Qualifier("usuarioDao")
		UsuarioDao usuarioDao;
		
		@Autowired
		@Qualifier("perfilDAO")
		PerfilDAO perfilDAO;
		
		 @Autowired
		 @Qualifier("sessionDao")
		 SessionDao sessionDao;

		 @Autowired
		 @Qualifier("sucursalDao")
		 SucursalDao sucursalDao;

		 
		 @RequestMapping(value={"/prueba"},method = RequestMethod.GET)
		   
		   public void prueba(HttpServletResponse response, HttpServletRequest request) throws IOException {
			   response.getWriter().println("Prueba del mètodo Sucursal prueba");

		    }
		 
			//@RequestMapping(value = { "/add/{userName}" }, method = RequestMethod.GET)
		 //public void crearPerfil(HttpServletRequest request, HttpServletResponse response, 
//					@RequestBody String json, @PathVariable String userName)throws IOException {
			@RequestMapping(value = { "/add__" }, method = RequestMethod.GET)
			public void crearPerfil(HttpServletRequest request, HttpServletResponse response)throws IOException {
//				if(SesionController.verificarPermiso2(request, usuarioDao, perfilDAO, 45, sessionDao,userName)){
				//	AsignadorDeCharset.asignar(request, response);
				//	System.out.println(" edgar manda:"+json);
					Sucursal sucursal = new Sucursal();
					sucursal.setNombre("prueba");
					sucursal.setRfc("prueba");
				//	sucursal.setCurp("prueba11111");
					sucursal.setDomicilio("pruebapruebapruebaprueba");
					sucursal.setTelefono("--------");
					sucursal.setTitular("prueba prueba");
					sucursal.setUbicacion("-------");
					sucursalDao.save(sucursal);
//				} else {
//					response.sendError(403);
//				}
			}
			
//			@RequestMapping(value = { "/add/{userName}" }, method = RequestMethod.GET)
//			public void crearPerfil(HttpServletRequest request, HttpServletResponse response, 
//						@RequestBody String json, @PathVariable String userName)throws IOException {
				
				@RequestMapping(value = { "/add" }, method = RequestMethod.POST, consumes = "Application/Json")
				public void crearPerfil(HttpServletRequest request, HttpServletResponse response, 
							@RequestBody String json)throws IOException {
			
//					if(SesionController.verificarPermiso2(request, usuarioDao, perfilDAO, 45, sessionDao,userName)){
				AsignadorDeCharset.asignar(request, response);
				System.out.println(" edgar manda:"+json);
				Sucursal sucursal = (Sucursal) JsonConvertidor.fromJson(json, Sucursal.class);
				//System.out.println("lista de permisos:"+perfil.getPermisos());
				sucursalDao.save(sucursal);
//					} else {
//						response.sendError(403);
//					}
				}
			
			
			 @RequestMapping(value = { "/find/{id}" }, method = RequestMethod.GET, produces = "application/json")
				public void findFolio(HttpServletResponse response, HttpServletRequest request,
						@PathVariable Long id) throws IOException {
				   System.out.println("xxxxxxxxx");
					AsignadorDeCharset.asignar(request, response);
					//DetalleDiscrepanciaVo dd = getDetalleDiscrepancia(id);
				//	System.out.println("aaaaaaaaaa");
					System.out.println("sucursal"+id);
					Sucursal s=sucursalDao.consult(id);
					
					response.getWriter().println(JsonConvertidor.toJson(s));
				
				}
			 
			 @RequestMapping(value = { "/findAll" }, method = RequestMethod.GET, produces = "application/json")
				public void findAllSuc(HttpServletResponse response, HttpServletRequest request) throws IOException {
					AsignadorDeCharset.asignar(request, response);
					List<Sucursal> lista = sucursalDao.findAll();
					if (lista == null) {
						lista = new ArrayList<Sucursal>();
					}
					response.getWriter().println(JsonConvertidor.toJson(lista));

				}
}
