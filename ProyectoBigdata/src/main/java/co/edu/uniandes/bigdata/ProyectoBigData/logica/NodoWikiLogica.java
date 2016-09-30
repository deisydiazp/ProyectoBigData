package co.edu.uniandes.bigdata.ProyectoBigData.logica;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DateFormat;
import javax.ejb.Stateless;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import co.edu.uniandes.bigdata.ProyectoBigData.dto.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Random;


/**
  *  @generated
  */
@Stateless
public class NodoWikiLogica {
    
    public static final String RUTA_ARCHIVO = "C:\\prueba\\part-r-00000";
    private static Map<String, PersonajeDTO> mapa = new HashMap<>();
    
    public String filtarArchivoWiki(String fechaInicial, String fechaFinal, String filtroPais, String filtroNombre){
        
        try{
        
            System.out.println("1. " + System.currentTimeMillis());
            //Test2 t = new Test2();
            procesarArchivo();
            System.out.println("2. " + System.currentTimeMillis());
            System.out.println("cant: " + mapa.size());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaInicio = sdf.parse(fechaInicial);
            Date fechaFin = sdf.parse(fechaFinal);

            Map<String, PersonajeDTO> filtro = new HashMap<>();
            Map<String, Integer> indices = new HashMap<>();
            Map<String, String> coloresPais = new HashMap<>();
            int i = 0;
            
            for (String key : mapa.keySet()) {
                PersonajeDTO p = mapa.get(key);
                
                if(!"seleccionar".equals(filtroPais)){
                    if(!filtroNombre.equals("ninguno") && !"".equals(filtroNombre.trim())){
                        if ((p.getFecha_nacimiento().after(fechaInicio) && p.getFecha_nacimiento().before(fechaFin)) && p.getPais_nacimiento().toLowerCase().equals(filtroPais) && p.getNombre().toLowerCase().contains(filtroNombre.toLowerCase())) {  
                            p.setPeso(9);
                            filtro.put(key, p);
                            indices.put(key, i++);
                            for (String rel : p.getNombresRelaciones()) {
                                if (!filtro.containsKey(rel)) {
                                    PersonajeDTO p1 = mapa.get(rel);
                                    if (p1 != null) {
                                        filtro.put(rel, p1);
                                        indices.put(rel, i++);
                                    }
                                }
                            }
                        } 
                    }else{
                        if ((p.getFecha_nacimiento().after(fechaInicio) && p.getFecha_nacimiento().before(fechaFin)) && p.getPais_nacimiento().toLowerCase().equals(filtroPais)) {  
                            p.setPeso(9);
                            filtro.put(key, p);
                            indices.put(key, i++);
                            for (String rel : p.getNombresRelaciones()) {
                                if (!filtro.containsKey(rel)) {
                                    PersonajeDTO p1 = mapa.get(rel);
                                    if (p1 != null) {
                                        filtro.put(rel, p1);
                                        indices.put(rel, i++);
                                    }
                                }
                            }
                        }
                    }
                }else{
                    if(!filtroNombre.equals("ninguno") && !"".equals(filtroNombre.trim())){
                        if ((p.getFecha_nacimiento().after(fechaInicio) && p.getFecha_nacimiento().before(fechaFin)) && p.getNombre().toLowerCase().contains(filtroNombre.toLowerCase())) {  
                            p.setPeso(9);
                            filtro.put(key, p);
                            indices.put(key, i++);
                            for (String rel : p.getNombresRelaciones()) {
                                if (!filtro.containsKey(rel)) {
                                    PersonajeDTO p1 = mapa.get(rel);
                                    if (p1 != null) {
                                        filtro.put(rel, p1);
                                        indices.put(rel, i++);
                                    }
                                }
                            }
                        }
                    }else{
                       if (p.getFecha_nacimiento().after(fechaInicio) && p.getFecha_nacimiento().before(fechaFin)) {  
                            p.setPeso(9);
                            filtro.put(key, p);
                            indices.put(key, i++);
                            for (String rel : p.getNombresRelaciones()) {
                                if (!filtro.containsKey(rel)) {
                                    PersonajeDTO p1 = mapa.get(rel);
                                    if (p1 != null) {
                                        filtro.put(rel, p1);
                                        indices.put(rel, i++);
                                    }
                                }
                            }
                        } 
                    }
                }
            }
            
            boolean exist = false;
            System.out.println("3. " + System.currentTimeMillis());
            System.out.println("cant: " + filtro.size());
            StringBuilder sbNodes = new StringBuilder();
            StringBuilder sbLinks = new StringBuilder();
            sbNodes.append("{\"nodes\":[");
            sbLinks.append("\"links\":[");
            
            
            coloresPais.put("Unknown","#000000");
            for (String key : sortByComparator(indices, true).keySet()) {
                List<Integer> rels = new ArrayList<>();
                PersonajeDTO p = filtro.get(key);
                Integer indexA=indices.get(key);
                for (String rel : p.getNombresRelaciones()) {
                    Integer indexB = indices.get(rel);
                    if (indexB != null) {
                        rels.add(indexB);
                        sbLinks.append(toGraphStringLinks(indexA, indexB));
                        exist = true;
                    }
                }
                String color = "";
                if(!coloresPais.containsKey(p.getPais_nacimiento()))
                {
                    color = obtenerColor();
                    coloresPais.put(p.getPais_nacimiento(), color);
                }
                else
                    color = coloresPais.get(p.getPais_nacimiento());
                
                sbNodes.append(toGraphStringNodes(p, indexA, rels, color));
            }
            
            String texto = sbNodes.toString();
            texto = texto.substring(0, texto.length() - 1);
            texto = texto + "]";
            texto = texto + ",";
            texto = texto + sbLinks.toString();
            if(exist)
                texto = texto.substring(0, texto.length() - 1);
            
            texto = texto + "]}";

            System.out.println("4. " + System.currentTimeMillis());
            System.out.println("datos: " + texto);
            System.out.println("5. " + System.currentTimeMillis());

            return texto;
            
        }catch(Exception ex){
            System.out.println("co.edu.uniandes.bigdata.ProyectoBigData.logica.NodoWikiLogica.filtarArchivoWiki() --> " + ex.getMessage());
        }
        
        return null;
    }
    
    private static String obtenerColor(){
        String[] letters = new String[15];
        letters = "0123456789ABCDEF".split("");
        String code ="#";
        for(int i=0;i<6;i++)
        {
            double ind = Math.random() * 15;
            int index = (int)Math.round(ind);
            code += letters[index]; 
        }
        return code;
    }
    
    private static Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap, final boolean order)
    {

        List<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Entry<String, Integer>>()
        {
            public int compare(Entry<String, Integer> o1,
                    Entry<String, Integer> o2)
            {
                if (order)
                {
                    return o1.getValue().compareTo(o2.getValue());
                }
                else
                {
                    return o2.getValue().compareTo(o1.getValue());

                }
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Entry<String, Integer> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }
    
     private void procesarArchivo() {
        if(mapa.size()!=0){
            return ;
        }
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
            archivo = new File(RUTA_ARCHIVO);
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;
            while ((linea = br.readLine()) != null) {
                PersonajeDTO p = new PersonajeDTO(linea);
                mapa.put(p.getNombre(), p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si todo va bien como si salta 
            // una excepcion.
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private String toGraphStringNodes(PersonajeDTO p, Integer index, List<Integer> relaciones, String color) {
        StringBuilder sb = new StringBuilder(" ");
        for (Integer i : relaciones) {
            sb.append(i).append(",");
        }
        String rels = sb.toString();

        return "{\n"
                + "   \"index\": " + index + ", \n"
                + "   \"links\": [" + rels.substring(0, rels.length() - 1) + "], \n"
                + "   \"score\": " + p.getPeso() + ", \n"
                + "   \"level\": 0, \n"
                + "   \"name\": \"" + p.getNombre() + "\", \n"
                + "   \"label\": \"" + p.getNombre() + "\", \n"
                + "   \"cover\": \"" + (p.getCoverURL() == null ? "-" : p.getCoverURL()) + "\", \n"
                + "   \"country\": \"" + p.getPais_nacimiento() + "\", \n"
                + "   \"birth_date\": \"" + p.getFecha_nacimientoString() + "\", \n"
                + "   \"description\": \"xxxxx\", \n"
                + "   \"url\": \"" + (p.getUrl() == null ? "-" : p.getUrl()) + "\", \n"
                + "   \"color\": \"" + color + "\", \n"
                + "   \"id\": " + p.getId()
                + "\n},";
    }

    private String toGraphStringLinks(Integer indexA, Integer indexB) {

        String stringLinks =  "{"
                + "\n   \"source\": " + indexB + ","
                + "\n   \"target\": " + indexA + ","
                + "\n   \"weight\": 0.1"
                + "\n},";

        return stringLinks;
    }
    
}
    