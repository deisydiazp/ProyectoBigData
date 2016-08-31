package co.edu.uniandes.bigdata.ProyectoBigData.servicio;

import co.edu.uniandes.bigdata.ProyectoBigData.dto.*;
import co.edu.uniandes.bigdata.ProyectoBigData.logica.*;
import java.util.List;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

/**
  *  @generated
  */
@Stateless
@Path("/Noticia")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NoticiaServicio {
	@EJB
    private NoticiaLogica logica;

	
	/**
	* @generated
	*/
	@GET
	public List<NoticiaDTO> obtenerTodosNoticias(){
		return logica.obtenerTodos();
	}
	
	/**
	* @generated
	*/
	@GET
	@Path("/{id}")
	public NoticiaDTO obtenerNoticia(@PathParam("id") Long id){
		return logica.obtener(id);
	}
	
	
	/**
	* @generated
	*/
	@POST
	public NoticiaDTO guardarNoticia(NoticiaDTO dto){
	    if(dto.getId()!=null){
	        logica.actualizar(dto);
	        return dto;
	    }else{
	        return logica.guardar(dto);
	    }
	}
	
	
	/**
	* @generated
	*/
	@DELETE
	@Path("/{id}")
	public void borrarNoticia(@PathParam("id") Long id){
		logica.borrar(id);
	}
	
	
}
