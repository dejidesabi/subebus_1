package com.tikal.subebus.backProcess.servlet;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.tikal.subebus.dao.MembresiaDao;
import com.tikal.subebus.modelo.entity.Membresia;

/**
 * Servlet implementation class checkCaducidad
 */
public class checkCaducidadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
	
	 @Autowired
	 MembresiaDao memDao;
	 
    public checkCaducidadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Calendar cal=Calendar.getInstance(TimeZone.getTimeZone("America/Mexico_City"));
		List<Membresia> lista= memDao.consultarTodos();
		for(Membresia m:lista){
			if (cal.getTime().after(m.getFechaCaducidad())){
				m.setEstatus("INACTIVA");
				memDao.actualizar(m);
			}
		}
		
		response.getWriter().print("OK");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
