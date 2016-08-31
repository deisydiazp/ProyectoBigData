package co.edu.uniandes.bigdata.ProyectoBigData.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @generated
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeedsDTO {

    public FeedsDTO() {
    }

    public FeedsDTO(Long id) {
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
    private String categoria;

    /**
     * @generated
     */
    private String fuente;

    /**
     * @generated
     */
    private String titulo;

    /**
     * @generated
     */
    private String url;

    /**
     * @generated
     */
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
