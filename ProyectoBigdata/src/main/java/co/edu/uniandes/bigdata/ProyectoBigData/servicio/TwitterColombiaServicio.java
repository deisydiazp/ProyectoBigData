package co.edu.uniandes.bigdata.ProyectoBigData.servicio;


import co.edu.uniandes.bigdata.ProyectoBigData.logica.*;
import co.edu.uniandes.bigdata.ProyectoBigData.util.DataGrapher;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

/**
 * @generated
 */
@Stateless
@Path("/TwitterColombia")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TwitterColombiaServicio {

    @EJB
    private TwitterColombiaLogica logica;
     
    @GET
    @Path("/GetTwitterDashboard")
    public List<DataGrapher> getTwitterDashboard() {
        return logica.getTwitterDashboard("ReformaTributaria","");
    }
    
    
}
