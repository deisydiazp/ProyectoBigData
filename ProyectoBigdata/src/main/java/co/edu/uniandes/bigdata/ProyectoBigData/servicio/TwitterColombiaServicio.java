package co.edu.uniandes.bigdata.ProyectoBigData.servicio;


import co.edu.uniandes.bigdata.ProyectoBigData.logica.*;
import co.edu.uniandes.bigdata.ProyectoBigData.util.DataGrapher;
import co.edu.uniandes.bigdata.ProyectoBigData.util.MongoDataRecord;
import com.google.gson.Gson;
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
    @Path("/GetTwitterDashboard/{topics}/{influencers}/{limit}")
    public List<DataGrapher> getTwitterDashboard(@PathParam("topics") String topics, @PathParam("influencers") String influencers, @PathParam("limit") int limit) {
        List<DataGrapher> lista = logica.getTwitterDashboard(topics,influencers, limit);
        return lista;
    }
    
    @GET
    @Path("/GetTopTopics")
    public String getTopTopics() {
        List<MongoDataRecord> lista = logica.getTopTopics("");
        return new Gson().toJson(lista);
    }
    
    @GET
    @Path("/GetInfluencers")
    public String getInfluencers() {
        List<MongoDataRecord> lista = logica.getInfluencers("");
        return new Gson().toJson(lista);
    }
}
