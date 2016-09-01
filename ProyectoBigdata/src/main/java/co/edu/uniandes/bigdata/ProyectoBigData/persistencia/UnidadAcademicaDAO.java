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
public class UnidadAcademicaDAO {
	@PersistenceContext
    private EntityManager em;

	
	/**
	* @generated
	*/
	public List<UnidadAcademica> obtenerTodos(){
		return em.createNamedQuery("UnidadAcademica.obtenerTodos").getResultList();
	}
	
	/**
	* @generated
	*/
	public UnidadAcademica obtener(Long id){
		return em.find(UnidadAcademica.class, id);
	}
	
	
	/**
	* @generated
	*/
	public UnidadAcademica guardar(UnidadAcademica entidad){
		em.persist(entidad);
		return entidad;
	}
	
	
	/**
	* @generated
	*/
	public void borrar(Long id){
		em.remove(em.find(UnidadAcademica.class, id));
	}
	
	
	/**
	* @generated
	*/
	public void actualizar(UnidadAcademica entidad){
		em.merge(entidad);
	}
	
	public void eliminarUnidadAcademicaTodos(){
		em.createNativeQuery("delete from UnidadAcademica");
	}
}
