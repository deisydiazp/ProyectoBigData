package co.edu.uniandes.bigdata.ProyectoBigData.servicio;


import co.edu.uniandes.bigdata.ProyectoBigData.logica.*;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

/**
 * @generated
 */
@Stateless
@Path("/NodoWiki")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NodoWikiServicio {

    @EJB
    private NodoWikiLogica logica;
     
    @GET
    @Path("/filtro/{filtroFechaInicial}/{filtroFechaFinal}/{filtroPais}/{filtroNombre}")
    public String obtenerEventosFiltro(@PathParam("filtroFechaInicial") String fechaInicial, @PathParam("filtroFechaFinal") String fechaFinal, @PathParam("filtroPais") String filtroPais, @PathParam("filtroNombre") String filtroNombre) {
        //return logica.obtenerTodos();
        String cadenaJson = logica.filtarArchivoWiki(fechaInicial,fechaFinal,filtroPais,filtroNombre);
        if(cadenaJson.equals("{\"nodes\":],\"links\":[]}"))
            cadenaJson = "";
        return cadenaJson;
    }
}
