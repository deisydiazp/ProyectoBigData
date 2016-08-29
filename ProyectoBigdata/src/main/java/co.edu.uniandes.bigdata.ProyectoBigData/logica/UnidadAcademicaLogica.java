package co.edu.uniandes.bigdata.ProyectoBigData.logica;

import co.edu.uniandes.bigdata.ProyectoBigData.dto.*;
import co.edu.uniandes.bigdata.ProyectoBigData.persistencia.*;
import co.edu.uniandes.bigdata.ProyectoBigData.persistencia.entity.*;
import java.io.IOException;
import java.util.AbstractList;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import javax.persistence.*;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
  *  @generated
  */
@Stateless
public class UnidadAcademicaLogica {
	@EJB
    private UnidadAcademicaDAO persistencia;


	
	/**
	* @generated
	*/
	public List<UnidadAcademicaDTO> obtenerTodos(){
		return convertirEntidad(persistencia.obtenerTodos());
	}
	
	/**
	* @generated
	*/
	public UnidadAcademicaDTO obtener(Long id){
		return convertirEntidad(persistencia.obtener(id));
	}
	
	
	/**
	* @generated
	*/
	public UnidadAcademicaDTO guardar(UnidadAcademicaDTO dto){
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
	public void actualizar(UnidadAcademicaDTO dto){
		persistencia.actualizar(convertirDTO(dto));
	}
	
	
	/**
	* @generated
	*/
	private UnidadAcademica convertirDTO(UnidadAcademicaDTO dto){
		if(dto==null)return null;
		UnidadAcademica entidad=new UnidadAcademica();
		entidad.setId(dto.getId());
			entidad.setNombre(dto.getNombre());
			entidad.setUrl(dto.getUrl());
		
		
		return entidad;
	}
	
	
	/**
	* @generated
	*/
	private List<UnidadAcademica> convertirDTO(List<UnidadAcademicaDTO> dtos){
		List<UnidadAcademica> entidades=new ArrayList<UnidadAcademica>();
		for(UnidadAcademicaDTO dto:dtos){
			entidades.add(convertirDTO(dto));
		}
		return entidades;
	}
	
	
	/**
	* @generated
	*/
	private UnidadAcademicaDTO convertirEntidad(UnidadAcademica entidad){
		UnidadAcademicaDTO dto=new UnidadAcademicaDTO();
		dto.setId(entidad.getId());
			dto.setNombre(entidad.getNombre());
			dto.setUrl(entidad.getUrl());
		
		
		return dto;
	}
	
	
	/**
	* @generated
	*/
	private List<UnidadAcademicaDTO> convertirEntidad(List<UnidadAcademica> entidades){
		List<UnidadAcademicaDTO> dtos=new ArrayList<UnidadAcademicaDTO>();
		for(UnidadAcademica entidad:entidades){
			dtos.add(convertirEntidad(entidad));
		}
		return dtos;
	}
	
	public List<UnidadAcademicaDTO> crawlerUnidadAcademica (){
            
        try {
            persistencia.eliminarUnidadAcademicaTodo();
            Document doc = Jsoup.connect("http://www.uniandes.edu.co/institucional/facultades/facultades").get();
            Elements as = doc.select("ul[class=\"menu_ifactum\"]>li>a");
            
            List<UnidadAcademicaDTO> lstUnidades = new ArrayList<UnidadAcademicaDTO>();
            for(Element a:as){
                
                UnidadAcademicaDTO dto=new UnidadAcademicaDTO();
		dto.setNombre(a.text());
		dto.setUrl(a.attr("href"));
                dto = convertirEntidad(persistencia.guardar(convertirDTO(dto)));
                lstUnidades.add(dto);
            }
            
            return lstUnidades;
            
        } catch (Exception e) {
            System.out.println("Error UnidadAcademicaLogica.crawlerUnidadAcademica " + e.toString());
        }
        return null;
        }
}