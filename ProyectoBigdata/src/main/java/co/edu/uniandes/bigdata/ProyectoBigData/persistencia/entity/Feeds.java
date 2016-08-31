package co.edu.uniandes.bigdata.ProyectoBigData.persistencia.entity;


import javax.persistence.*;

/**
  *  @generated
  */
@Entity
@Table(name="Feeds")//, schema="${schema}")
@NamedQueries({
	@NamedQuery(name="Feeds.obtenerTodos", query="select e from Feeds e")
})
public class Feeds {

	@Id
    //@Column(name = "Feeds_id")
    @GeneratedValue(generator = "FeedsGen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "FeedsGen", sequenceName = "Feeds_SEQ",allocationSize = 1)
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
    
    //@Column(name = "categoria")
    private String categoria;
    
    /**
    * @generated
    * 1-1-false
    */
    
    //@Column(name = "fuente")
    private String fuente;
    
    /**
    * @generated
    * 1-1-false
    */
    
    //@Column(name = "titulo")
    private String titulo;
    
    /**
    * @generated
    * 1-1-false
    */
    
    //@Column(name = "url")
    private String url;
    
    /**
    * @generated
    * 1-1-false
    */
    
    //@Column(name = "publicado")
    private String publicado;
    
    
    
    /**
    * @generated
    */
    public String getCategoria() {
        return this.categoria;
    }
    
    /**
    * @generated
    */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    /**
    * @generated
    */
    public String getFuente() {
        return this.fuente;
    }
    
    /**
    * @generated
    */
    public void setFuente(String fuente) {
        this.fuente = fuente;
    }
    
    /**
    * @generated
    */
    public String getTitulo() {
        return this.titulo;
    }
    
    /**
    * @generated
    */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
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
    
    /**
    * @generated
    */
    public String getPublicado() {
        return this.publicado;
    }
    
    /**
    * @generated
    */
    public void setPublicado(String publicado) {
        this.publicado = publicado;
    }
    
	
}
