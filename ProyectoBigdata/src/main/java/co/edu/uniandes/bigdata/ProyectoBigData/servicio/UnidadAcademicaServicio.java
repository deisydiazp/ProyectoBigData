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
@Path("/UnidadAcademica")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UnidadAcademicaServicio {
	@EJB
    private UnidadAcademicaLogica logica;

	
	/**
	* @generated
	*/
	@GET
	public List<UnidadAcademicaDTO> obtenerTodosUnidadAcademicas(){
		return logica.crawlerUnidadAcademica();
	}
	
	/**
	* @generated
	*/
	@GET
	@Path("/{id}")
	public UnidadAcademicaDTO obtenerUnidadAcademica(@PathParam("id") Long id){
		return logica.obtener(id);
	}
	
	
	/**
	* @generated
	*/
	@POST
	public UnidadAcademicaDTO guardarUnidadAcademica(UnidadAcademicaDTO dto){
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
	public void borrarUnidadAcademica(@PathParam("id") Long id){
		logica.borrar(id);
	}
        
        /**
	* @generated
	*/
	@GET
        @Path("/borrarInformacion")
        public List<UnidadAcademicaDTO> borrarInformacionBD(){
                logica.borrarInformacionBD();
		return null;
	}

}
