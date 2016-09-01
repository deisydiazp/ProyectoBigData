package co.edu.uniandes.bigdata.ProyectoBigData.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

/**
* @generated
*/
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventoDTO {

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
    private String fecha;
    
    /**
    * @generated
    */
    private String hora;
    
    /**
    * @generated
    */
    private String lugar;
    
    /**
    * @generated
    */
    private String nombreContacto;
    
    /**
    * @generated
    */
    private String correoContacto;
    
    /**
    * @generated
    */
    private String palabrasClave;
    
    /**
    * @generated
    */
    private UnidadAcademicaDTO unidadAcademica;
    
    /**
    * @generated
    */
    private String descripcion;
    
    /**
    * @generated
    */
    private String enlace;
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
    public String getFecha() {
        return this.fecha;
    }
    
    /**
    * @generated
    */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    /**
    * @generated
    */
    public String getHora() {
        return this.hora;
    }
    
    /**
    * @generated
    */
    public void setHora(String hora) {
        this.hora = hora;
    }
    /**
    * @generated
    */
    public String getLugar() {
        return this.lugar;
    }
    
    /**
    * @generated
    */
    public void setLugar(String lugar) {
        this.lugar = lugar;
    }
    /**
    * @generated
    */
    public String getNombreContacto() {
        return this.nombreContacto;
    }
    
    /**
    * @generated
    */
    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }
    /**
    * @generated
    */
    public String getCorreoContacto() {
        return this.correoContacto;
    }
    
    /**
    * @generated
    */
    public void setCorreoContacto(String correoContacto) {
        this.correoContacto = correoContacto;
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
    public UnidadAcademicaDTO getUnidadAcademica() {
        return this.unidadAcademica;
    }

    /**
    * @generated
    */
    public void setUnidadAcademica(UnidadAcademicaDTO unidadAcademica) {
        this.unidadAcademica = unidadAcademica;
    }

    /**
    * @generated
    */
    public String getDescripcion() {
        return this.descripcion;
    }

    /**
    * @generated
    */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    /**
    * @generated
    */
    public String getEnlace() {
        return this.enlace;
    }

    /**
    * @generated
    */
    public void setEnlace(String enlace) {
        this.enlace = enlace;
    }
}
