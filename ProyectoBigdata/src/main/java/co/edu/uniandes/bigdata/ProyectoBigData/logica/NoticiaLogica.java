package co.edu.uniandes.bigdata.ProyectoBigData.logica;

import co.edu.uniandes.bigdata.ProyectoBigData.dto.*;
import co.edu.uniandes.bigdata.ProyectoBigData.persistencia.*;
import co.edu.uniandes.bigdata.ProyectoBigData.persistencia.entity.*;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.*;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
  *  @generated
  */
@Stateless
public class NoticiaLogica {
	@EJB
    private NoticiaDAO persistencia;


	
	/**
	* @generated
	*/
	public List<NoticiaDTO> obtenerTodos(){
		return convertirEntidad(persistencia.obtenerTodos());
	}
	
	/**
	* @generated
	*/
	public NoticiaDTO obtener(Long id){
		return convertirEntidad(persistencia.obtener(id));
	}
	
	
	/**
	* @generated
	*/
	public NoticiaDTO guardar(NoticiaDTO dto){
		return convertirEntidad(persistencia.guardar(convertirDTO(dto)));
	}
	
	
	/**
	* @generated
	*/
	public void borrar(Long id){
		persistencia.borrar(id);
	}
	
	
	/**
	* @generated
	*/
	public void actualizar(NoticiaDTO dto){
		persistencia.actualizar(convertirDTO(dto));
	}
	
	
	/**
	* @generated
	*/
	private Noticia convertirDTO(NoticiaDTO dto){
		if(dto==null)return null;
		Noticia entidad=new Noticia();
		entidad.setId(dto.getId());
			entidad.setNombre(dto.getNombre());
			entidad.setContenido(dto.getContenido());
			entidad.setPalabrasClave(dto.getPalabrasClave());
		
			if(dto.getFeeds()!=null){
				entidad.setFeeds(new Feeds());
				entidad.getFeeds().setId(dto.getFeeds().getId());
			}
		
		return entidad;
	}
	
	
	/**
	* @generated
	*/
	private List<Noticia> convertirDTO(List<NoticiaDTO> dtos){
		List<Noticia> entidades=new ArrayList<Noticia>();
		for(NoticiaDTO dto:dtos){
			entidades.add(convertirDTO(dto));
		}
		return entidades;
	}
	
	
	/**
	* @generated
	*/
	private NoticiaDTO convertirEntidad(Noticia entidad){
		NoticiaDTO dto=new NoticiaDTO();
		dto.setId(entidad.getId());
			dto.setNombre(entidad.getNombre());
			dto.setContenido(entidad.getContenido());
			dto.setPalabrasClave(entidad.getPalabrasClave());
		
			if(entidad.getFeeds()!=null){
				dto.setFeeds(
					new FeedsDTO(
						entidad.getFeeds().getId()));
			}
		
		return dto;
	}
	
	
	/**
	* @generated
	*/
	private List<NoticiaDTO> convertirEntidad(List<Noticia> entidades){
		List<NoticiaDTO> dtos=new ArrayList<NoticiaDTO>();
		for(Noticia entidad:entidades){
			dtos.add(convertirEntidad(entidad));
		}
		return dtos;
	}
	
	
}
