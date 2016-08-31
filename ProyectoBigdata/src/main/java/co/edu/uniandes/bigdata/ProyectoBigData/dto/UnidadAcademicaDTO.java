package co.edu.uniandes.bigdata.ProyectoBigData.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import java.util.ArrayList;

/**
* @generated
*/
@JsonIgnoreProperties(ignoreUnknown = true)
public class UnidadAcademicaDTO {

    public UnidadAcademicaDTO() {
    }

    public UnidadAcademicaDTO(Long id) {
        this.id = id;
    }

    
    
	private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
	
    
    /**
    * @generated
    */
    private String nombre;
    
    /**
    * @generated
    */
    private String url;
    
    
    /**
    * @generated
    */
    public String getNombre() {
        return this.nombre;
    }
    
    /**
    * @generated
    */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    /**
    * @generated
    */
    public String getUrl() {
        return this.url;
    }
    
    /**
    * @generated
    */
    public void setUrl(String url) {
        this.url = url;
    }
    
	
}
