package co.edu.uniandes.bigdata.ProyectoBigData.persistencia.entity;


import java.util.Date;
import javax.persistence.*;

/**
  *  @generated
  */
@Entity
@Table(name="Evento")//, schema="${schema}")
@NamedQueries({
	@NamedQuery(name="Evento.obtenerTodos", query="select e from Evento e")
})
public class Evento {

	@Id
    //@Column(name = "Evento_id")
    @GeneratedValue(generator = "EventoGen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "EventoGen", sequenceName = "Evento_SEQ",allocationSize = 1)
	private Long id;

	public Long getId(){
		return this.id;
	}

	public void setId(Long id){
		this.id=id;
	}
    
    /**
    * @generated
    * 1-1-false
    */
    
    //@Column(name = "nombre")
    private String nombre;
    
    /**
    * @generated
    * 1-1-false
    */
    @Temporal(TemporalType.DATE)
    //@Column(name = "fecha")
    private Date fecha;
    
    /**
    * @generated
    * 1-1-false
    */
    
    //@Column(name = "hora")
    private String hora;
    
    /**
    * @generated
    * 1-1-false
    */
    
    //@Column(name = "lugar")
    private String lugar;
    
    /**
    * @generated
    * 1-1-false
    */
    
    //@Column(name = "nombreContacto")
    private String nombreContacto;
    
    /**
    * @generated
    * 1-1-false
    */
    
    //@Column(name = "correoContacto")
    private String correoContacto;
    
    /**
    * @generated
    * 1-1-false
    */
    
    //@Column(name = "palabrasClave")
    private String palabrasClave;
    
    
    /**
    * @generated
    * 0-1-false
    */
    @ManyToOne(cascade={},fetch=FetchType.EAGER)
    private UnidadAcademica unidadAcademica;
    
    
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
    public Date getFecha() {
        return this.fecha;
    }
    
    /**
    * @generated
    */
    public void setFecha(Date fecha) {
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
	public UnidadAcademica getUnidadAcademica() {
	    return this.unidadAcademica;
	}
	
	/**
	* @generated
	*/
	public void setUnidadAcademica(UnidadAcademica unidadAcademica) {
	    this.unidadAcademica = unidadAcademica;
	}
	
}