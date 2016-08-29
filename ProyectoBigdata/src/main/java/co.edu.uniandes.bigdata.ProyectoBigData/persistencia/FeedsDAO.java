package co.edu.uniandes.bigdata.ProyectoBigData.persistencia;

import co.edu.uniandes.bigdata.ProyectoBigData.persistencia.entity.*;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.*;
import javax.ejb.Stateless;

/**
  *  @generated
  */
@Stateless
public class FeedsDAO {
	@PersistenceContext
    private EntityManager em;

	
	/**
	* @generated
	*/
	public List<Feeds> obtenerTodos(){
		return em.createNamedQuery("Feeds.obtenerTodos").getResultList();
	}
	
	/**
	* @generated
	*/
	public Feeds obtener(Long id){
		return em.find(Feeds.class, id);
	}
	
	
	/**
	* @generated
	*/
	public Feeds guardar(Feeds entidad){
		em.persist(entidad);
		return entidad;
	}
	
	
	/**
	* @generated
	*/
	public void borrar(Long id){
		em.remove(em.find(Feeds.class, id));
	}
	
	
	/**
	* @generated
	*/
	public void actualizar(Feeds entidad){
		em.merge(entidad);
	}
	
	
}
