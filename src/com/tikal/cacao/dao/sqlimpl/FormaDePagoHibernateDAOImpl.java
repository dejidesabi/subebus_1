package com.tikal.cacao.dao.sqlimpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tikal.cacao.dao.SimpleHibernateDAO;
import com.tikal.cacao.model.orm.FormaDePago;
import com.tikal.cacao.util.Util;

@Service("formaDePagoDAOH")
public class FormaDePagoHibernateDAOImpl extends AbstractDAOHibernate implements SimpleHibernateDAO<FormaDePago> {

	
	public FormaDePagoHibernateDAOImpl(){
		System.out.println("FormaDePagoHibernateDAOImpl instancia");
	}
	
	@Override
	public void guardar(FormaDePago entity) {
		this.initSessionTx();
		sesion.persist(entity);
		this.cleanSessionTx();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FormaDePago> consultarTodos() {
		this.sesion = this.sessionFactory.openSession();
		String hql = "from FormaDePago fp";
		List<FormaDePago> listaResultado= this.sesion.createQuery(hql).list();
		this.sesion.close();
		//List<FormaDePago> listaFormasPago = Util.convertirArrayObjectToHBEntity(listaResultado, FormaDePago.class);
		return listaResultado;
	}

	@Override
	public synchronized FormaDePago consultar(String id) {
		super.sesion = super.sessionFactory.openSession();
		FormaDePago formaDePago = (FormaDePago) super.sesion.get(FormaDePago.class, id);
		if (super.sesion.isConnected()) {
			super.sesion.close();
		}
		return formaDePago;
	}

}