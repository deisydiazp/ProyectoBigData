package co.edu.uniandes.bigdata.ProyectoBigData.persistencia.entity;


import javax.persistence.*;

/**
  *  @generated
  */
@Entity
@Table(name="Noticia")//, schema="${schema}")
@NamedQueries({
	@NamedQuery(name="Noticia.obtenerTodos", query="select e from Noticia e")
})
public class Noticia {

	@Id
    //@Column(name = "Noticia_id")
    @GeneratedValue(generator = "NoticiaGen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "NoticiaGen", sequenceName = "Noticia_SEQ",allocationSize = 1)
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
    
    //@Column(name = "contenido")
    private String contenido;
    
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
    private Feeds feeds;
    
    
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
	public Feeds getFeeds() {
	    return this.feeds;
	}
	
	/**
	* @generated
	*/
	public void setFeeds(Feeds feeds) {
	    this.feeds = feeds;
	}
	
}
