package co.edu.uniandes.bigdata.ProyectoBigData.servicio;


import co.edu.uniandes.bigdata.ProyectoBigData.dto.SentimientosTwitter;
import co.edu.uniandes.bigdata.ProyectoBigData.logica.*;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

/**
 * @generated
 */
@Stateless
@Path("/Twitter")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TwitterServicio {

    @EJB
    private TwitterLogica logica;
     
    @GET
    @Path("/DatasetsAnotados/{numDataset}/{type}")
    public List<SentimientosTwitter> obtenerDatasetsAnotados(@PathParam("numDataset") int numDataset, @PathParam("type") String type) {
        List<SentimientosTwitter> result = logica.obtenerDatasetsAnotados(numDataset, type);
        return result;
    }
    
    @GET
    @Path("/TemasDatasetsAnotados/{numDataset}/{type}")
    public List<SentimientosTwitter> obtenerCantidadTemas(@PathParam("numDataset") int numDataset, @PathParam("type") String type) {
        List<SentimientosTwitter> result = logica.obtenerCantidadTemas(numDataset, type);
        return result;
    }
    
    @GET
    @Path("/UsuariosDatasetsAnotados/{numDataset}/{type}")
    public List<SentimientosTwitter> obtenerCantidadUsuarios(@PathParam("numDataset") int numDataset, @PathParam("type") String type) {
        List<SentimientosTwitter> result = logica.obtenerCantidadUsuarios(numDataset, type);
        return result;
    }
}
