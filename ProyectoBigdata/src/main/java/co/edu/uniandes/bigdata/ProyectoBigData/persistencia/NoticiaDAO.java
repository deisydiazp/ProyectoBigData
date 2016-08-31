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
public class NoticiaDAO {
	@PersistenceContext
    private EntityManager em;

	
	/**
	* @generated
	*/
	public List<Noticia> obtenerTodos(){
		return em.createNamedQuery("Noticia.obtenerTodos").getResultList();
	}
	
	/**
	* @generated
	*/
	public Noticia obtener(Long id){
		return em.find(Noticia.class, id);
	}
	
	
	/**
	* @generated
	*/
	public Noticia guardar(Noticia entidad){
		em.persist(entidad);
		return entidad;
	}
	
	
	/**
	* @generated
	*/
	public void borrar(Long id){
		em.remove(em.find(Noticia.class, id));
	}
	
	
	/**
	* @generated
	*/
	public void actualizar(Noticia entidad){
		em.merge(entidad);
	}
	
	
}
