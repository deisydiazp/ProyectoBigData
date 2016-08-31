package co.edu.uniandes.bigdata.ProyectoBigData.dto;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rodrigo B
 */
public class FiltroFeedsDTO {

    private String categoria;
    private String filtroFeed;
    private String texto;
    private boolean excluyente;
    private String metodo;

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }
    
    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getFiltroFeed() {
        return filtroFeed;
    }

    public void setFiltroFeed(String filtroFeed) {
        this.filtroFeed = filtroFeed;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public boolean isExcluyente() {
        return excluyente;
    }

    public void setExcluyente(boolean excluyente) {
        this.excluyente = excluyente;
    }
    
    
}
