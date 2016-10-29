
package org.netbeans.rest.application.config;

import java.util.Set;
import javax.ws.rs.core.Application;
import co.edu.uniandes.bigdata.ProyectoBigData.servicio.*;

/**
 *
 * @author daniel
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<Class<?>>();
        addRestResourceClasses(resources);
        return resources;
    }
    
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(co.edu.uniandes.bigdata.ProyectoBigData.servicio.EventoServicio.class);
        resources.add(co.edu.uniandes.bigdata.ProyectoBigData.servicio.FeedsServicio.class);
        resources.add(co.edu.uniandes.bigdata.ProyectoBigData.servicio.NodoWikiServicio.class);
        resources.add(co.edu.uniandes.bigdata.ProyectoBigData.servicio.NoticiaServicio.class);
        resources.add(co.edu.uniandes.bigdata.ProyectoBigData.servicio.TwitterColombiaServicio.class);
        resources.add(co.edu.uniandes.bigdata.ProyectoBigData.servicio.TwitterServicio.class);
        resources.add(co.edu.uniandes.bigdata.ProyectoBigData.servicio.UnidadAcademicaServicio.class);
        //resources.add(org.netbeans.rest.application.config.GsonMessageBodyHandler.class);
    }
    
}

