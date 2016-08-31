package co.edu.uniandes.bigdata.ProyectoBigData.persistencia.entity;


import javax.persistence.*;

/**
  *  @generated
  */
@Entity
@Table(name="UnidadAcademica")//, schema="${schema}")
@NamedQueries({
	@NamedQuery(name="UnidadAcademica.obtenerTodos", query="select e from UnidadAcademica e")
})
public class UnidadAcademica {

	@Id
    //@Column(name = "UnidadAcademica_id")
    @GeneratedValue(generator = "UnidadAcademicaGen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "UnidadAcademicaGen", sequenceName = "UnidadAcademica_SEQ",allocationSize = 1)
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
    
    //@Column(name = "url")
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
