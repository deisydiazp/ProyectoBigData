
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
        resources.add(NoticiaServicio.class);resources.add(UnidadAcademicaServicio.class);resources.add(FeedsServicio.class);resources.add(EventoServicio.class);
    }
    
}

