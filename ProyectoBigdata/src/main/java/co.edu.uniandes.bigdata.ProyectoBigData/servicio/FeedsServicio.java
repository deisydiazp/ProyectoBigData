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
@Path("/Feeds")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FeedsServicio {
	@EJB
    private FeedsLogica logica;

	
	/**
	* @generated
	*/
	@GET
	public List<FeedsDTO> obtenerTodosFeeds(){
		return logica.obtenerPorFiltro();
	}
	
	/**
	* @generated
	*/
	@GET
	@Path("/{id}")
	public FeedsDTO obtenerFeeds(@PathParam("id") Long id){
		return logica.obtener(id);
	}
	
	
	/**
	* @generated
	*/
	@POST
	public FeedsDTO guardarFeeds(FeedsDTO dto){
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
	public void borrarFeeds(@PathParam("id") Long id){
		logica.borrar(id);
	}
	
	
}
