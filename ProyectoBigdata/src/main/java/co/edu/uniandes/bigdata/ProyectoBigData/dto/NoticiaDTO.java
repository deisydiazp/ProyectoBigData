package co.edu.uniandes.bigdata.ProyectoBigData.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import java.util.ArrayList;

/**
* @generated
*/
@JsonIgnoreProperties(ignoreUnknown = true)
public class NoticiaDTO {

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
    private String contenido;
    
    /**
    * @generated
    */
    private String palabrasClave;
    
    
    /**
    * @generated
    */
    private FeedsDTO feeds;
    
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
    public String getContenido() {
        return this.contenido;
    }
    
    /**
    * @generated
    */
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
    /**
    * @generated
    */
    public String getPalabrasClave() {
        return this.palabrasClave;
    }
    
    /**
    * @generated
    */
    public void setPalabrasClave(String palabrasClave) {
        this.palabrasClave = palabrasClave;
    }
    
	/**
	* @generated
	*/
	public FeedsDTO getFeeds() {
	    return this.feeds;
	}
	
	/**
	* @generated
	*/
	public void setFeeds(FeedsDTO feeds) {
	    this.feeds = feeds;
	}
	
}
