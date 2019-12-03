package com.tikal.subebus.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.stereotype.Component;

import com.googlecode.objectify.ObjectifyService;
import com.tikal.subebus.modelo.entity.Alerta;
import com.tikal.subebus.modelo.entity.Folio;
import com.tikal.subebus.modelo.entity.Lote;
import com.tikal.subebus.modelo.entity.Membresia;
import com.tikal.subebus.modelo.entity.RutaBus;
import com.tikal.subebus.modelo.entity.RutaMem;
import com.tikal.subebus.modelo.entity.Serial;
import com.tikal.subebus.modelo.entity.Venta;
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
			ObjectifyService.register(Folio.class);
			ObjectifyService.register(Lote.class);
			ObjectifyService.register(Membresia.class);
			ObjectifyService.register(Serial.class);
			ObjectifyService.register(RutaBus.class);
			ObjectifyService.register(RutaMem.class);
			ObjectifyService.register(Venta.class);
			ObjectifyService.register(Alerta.class);
		}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
    
    
}
