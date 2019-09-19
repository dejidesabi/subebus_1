package com.tikal.subebus.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.stereotype.Component;

import com.googlecode.objectify.ObjectifyService;
import com.tikal.subebus.modelo.entity.Contador;
import com.tikal.subebus.modelo.entity.ContadorServicio;
import com.tikal.subebus.modelo.entity.Lote;
import com.tikal.subebus.modelo.entity.Membresia;
import com.tikal.subebus.modelo.login.Perfil;
import com.tikal.subebus.modelo.login.SessionEntity;
import com.tikal.subebus.modelo.login.Sucursal;
import com.tikal.subebus.modelo.login.Usuario;





@Component
public class StartupEntities implements ServletContextListener {
	
	
	
		public void contextInitialized(ServletContextEvent event) {
		
			
			ObjectifyService.register(Perfil.class);
			ObjectifyService.register(SessionEntity.class);
			ObjectifyService.register(Sucursal.class);
			ObjectifyService.register(Usuario.class);
			ObjectifyService.register(Contador.class);
			ObjectifyService.register(ContadorServicio.class);
			ObjectifyService.register(Lote.class);
			ObjectifyService.register(Membresia.class);
	
		}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
    
    
}
