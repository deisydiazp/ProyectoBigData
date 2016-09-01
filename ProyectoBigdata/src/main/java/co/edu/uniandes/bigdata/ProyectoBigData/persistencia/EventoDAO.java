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
public class EventoDAO {
	@PersistenceContext
    private EntityManager em;

	
	/**
	* @generated
	*/
	public List<Evento> obtenerTodos(){
		return em.createNamedQuery("Evento.obtenerTodos").getResultList();
	}
	
	/**
	* @generated
	*/
	public Evento obtener(Long id){
		return em.find(Evento.class, id);
	}
	
	
	/**
	* @generated
	*/
	public Evento guardar(Evento entidad){
		em.persist(entidad);
		return entidad;
	}
	
	
	/**
	* @generated
	*/
	public void borrar(Long id){
		em.remove(em.find(Evento.class, id));
	}
	
	
	/**
	* @generated
	*/
	public void actualizar(Evento entidad){
		em.merge(entidad);
	}
	
	public void eliminarEventosDeUnidadAcademica(Long id_unidadAcademica){
                em.createNativeQuery("delete from Evento where unidadacademica_id = " + id_unidadAcademica);
        }
        
        public List<Evento> obtenerEventosDeUnidadAcademica(Long id_unidadAcademica){
            return em.createNamedQuery("Evento.obtenerUnidadAcademica").setParameter("ua", id_unidadAcademica).getResultList();
	}
        
        public List<Evento> obtenerEventosPorFiltro(String fecha, String hora, Long id_unidadAcademica){
            return em.createNamedQuery("Evento.obtenerEventosPorFiltro").
                setParameter("ua", id_unidadAcademica).
                setParameter("fecha", fecha).
                setParameter("hora", hora).getResultList();
            
	}
        
        public void eliminarEventoTodos(){
		em.createNamedQuery("Evento.eliminarEventoTodos");
                //em.remove(Evento.class);
	}
}
