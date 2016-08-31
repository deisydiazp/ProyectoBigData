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
 * @generated
 */
@Stateless
@Path("/Evento")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EventoServicio {

    @EJB
    private EventoLogica logica;

    /**
     * @generated
     */
    @GET
    public List<EventoDTO> obtenerTodosEventos() {
        return logica.obtenerTodos();
    }

    /**
     * @generated
     */
    @GET
    @Path("/{id}")
    public EventoDTO obtenerEvento(@PathParam("id") Long id) {
        return logica.obtener(id);
    }

    /**
     * @generated
     */
    @POST
    public EventoDTO guardarEvento(EventoDTO dto) {
        if (dto.getId() != null) {
            logica.actualizar(dto);
            return dto;
        } else {
            return logica.guardar(dto);
        }
    }

    /**
     * @generated
     */
    @DELETE 
    @Path("/{id}")
    public void borrarEvento(@PathParam("id") Long id) {
        logica.borrar(id);
    }

    @GET
    @Path("/unidadAcademica/{id}")
    public List<EventoDTO> obtenerEventosUnidad(@PathParam("id") Long id) {
        //return logica.obtenerTodos();
        return logica.crawlerEventos(id);
    }
}
