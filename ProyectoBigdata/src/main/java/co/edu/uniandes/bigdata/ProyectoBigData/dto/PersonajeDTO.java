package co.edu.uniandes.bigdata.ProyectoBigData.dto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rodrigo B
 */
public class PersonajeDTO {
    
    private String id;
    private int idJSON;
    private String nombre;
    private Date fecha_nacimiento;
    private String pais_nacimiento;
    private String coverURL;
    private String url;
    private double peso;
    private List<String> nombresRelaciones=new ArrayList<>();
    private static final SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
    
    public PersonajeDTO(String texto) throws Exception{
        //?tefan Luchian	|2475912|Unknown|1868/02/01|Edgar Degas
        //texto=texto.replaceAll(";\t1", "");
        String[] datos=texto.split("\\|");
        this.id=datos[1];
        this.nombre=datos[0].trim();
        this.pais_nacimiento=datos[2];
        this.fecha_nacimiento=sdf.parse(datos[3]);
        this.coverURL = "https://en.wikipedia.org/w/api.php?action=query&pageids=" + id + "&prop=pageimages&format=json&pithumbsize=100";
        this.url = "https://en.wikipedia.org/?curid=" + id;
        this.peso = 7.5;
        //Personaje|1002676|Jerry Sadowitz|Unknown|1961/11/04|Lenny Bruce;Peter Cook;	1
        if(datos.length>4 && datos[4]!=null && datos[4].compareTo("")!=0){
            String[] nombresRelaciones=datos[4].split(";");
            for(int i=0;i<nombresRelaciones.length;i++){
                this.nombresRelaciones.add(nombresRelaciones[i]);
            }
        }
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getPais_nacimiento() {
        return pais_nacimiento;
    }

    public void setPais_nacimiento(String pais_nacimiento) {
        this.pais_nacimiento = pais_nacimiento;
    }

    public String getCoverURL() {
        return coverURL;
    }

    public void setCoverURL(String coverURL) {
        this.coverURL = coverURL;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getIdJSON() {
        return idJSON;
    }

    public void setIdJSON(int idJSON) {
        this.idJSON = idJSON;
    }

    public String getFecha_nacimientoString() {
        return sdf.format(fecha_nacimiento);
    }
    
    public List<String> getNombresRelaciones() {
        return nombresRelaciones;
    }

    public void setNombresRelaciones(List<String> nombresRelaciones) {
        this.nombresRelaciones = nombresRelaciones;
    }
    
    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }
}
