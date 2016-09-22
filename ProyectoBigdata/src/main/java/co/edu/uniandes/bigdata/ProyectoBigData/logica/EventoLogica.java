package co.edu.uniandes.bigdata.ProyectoBigData.logica;

import co.edu.uniandes.bigdata.ProyectoBigData.dto.*;
import co.edu.uniandes.bigdata.ProyectoBigData.persistencia.*;
import co.edu.uniandes.bigdata.ProyectoBigData.persistencia.entity.*;
import java.io.Console;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.*;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
  *  @generated
  */
@Stateless
public class EventoLogica {
    @EJB
    private EventoDAO persistencia;
    
    private static final Logger LOG=Logger.getLogger(EventoLogica.class.getName());
    
    @EJB
    private UnidadAcademicaDAO persistenciaUa;

	private final SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");

	
	/**
	* @generated
	*/
	public List<EventoDTO> obtenerTodos(){
		return convertirEntidad(persistencia.obtenerTodos());
	}
	
	/**
	* @generated
	*/
	public EventoDTO obtener(Long id){
		return convertirEntidad(persistencia.obtener(id));
	}
	
	
	/**
	* @generated
	*/
	public EventoDTO guardar(EventoDTO dto){
		return convertirEntidad(persistencia.guardar(convertirDTO(dto)));
	}
	
	
	/**
	* @generated
	*/
	public void borrar(Long id){
		persistencia.borrar(id);
	}
	
	
	/**
	* @generated
	*/
	public void actualizar(EventoDTO dto){
		persistencia.actualizar(convertirDTO(dto));
	}
	
	
	/**
	* @generated
	*/
	private Evento convertirDTO(EventoDTO dto){
		if(dto==null)return null;
		Evento entidad=new Evento();
		entidad.setId(dto.getId());
			entidad.setNombre(dto.getNombre());
                        entidad.setFecha(dto.getFecha());    
                        entidad.setHora(dto.getHora());
			entidad.setLugar(dto.getLugar());
			entidad.setNombreContacto(dto.getNombreContacto());
			entidad.setCorreoContacto(dto.getCorreoContacto());
			entidad.setPalabrasClave(dto.getPalabrasClave());
                        entidad.setDescripcion(dto.getDescripcion());
                        entidad.setEnlace(dto.getEnlace());
		
			if(dto.getUnidadAcademica()!=null){
				entidad.setUnidadAcademica(new UnidadAcademica());
				entidad.getUnidadAcademica().setId(dto.getUnidadAcademica().getId());
			}
		
		return entidad;
	}
	
	
	/**
	* @generated
	*/
	private List<Evento> convertirDTO(List<EventoDTO> dtos){
		List<Evento> entidades=new ArrayList<Evento>();
		for(EventoDTO dto:dtos){
			entidades.add(convertirDTO(dto));
		}
		return entidades;
	}
	
	
	/**
	* @generated
	*/
	private EventoDTO convertirEntidad(Evento entidad){
		EventoDTO dto=new EventoDTO();
		dto.setId(entidad.getId());
			dto.setNombre(entidad.getNombre());
			dto.setFecha(entidad.getFecha());
			dto.setHora(entidad.getHora());
			dto.setLugar(entidad.getLugar());
			dto.setNombreContacto(entidad.getNombreContacto());
			dto.setCorreoContacto(entidad.getCorreoContacto());
			dto.setPalabrasClave(entidad.getPalabrasClave());
                        dto.setDescripcion(entidad.getDescripcion());
                        dto.setEnlace(entidad.getEnlace());
                        
			if(entidad.getUnidadAcademica()!=null){
				dto.setUnidadAcademica(
					new UnidadAcademicaDTO(
						entidad.getUnidadAcademica().getId()));
			}
		
		return dto;
	}
	
	
	/**
	* @generated
	*/
	private List<EventoDTO> convertirEntidad(List<Evento> entidades){
		List<EventoDTO> dtos=new ArrayList<EventoDTO>();
		for(Evento entidad:entidades){
			dtos.add(convertirEntidad(entidad));
		}
		return dtos;
	}
	
        private String armarUrl(String urlFacultad, String urlEvento){
            
            String urlFinal = urlEvento;

            if(!urlEvento.contains(urlFacultad)){
                if(urlFacultad.substring(urlFacultad.length()-1).equals("/")){
                    urlFacultad = urlFacultad.substring(0,urlFacultad.length()-1);
                }

                if(urlEvento.substring(0,1).equals("/")){
                    urlEvento = urlEvento.substring(1,urlEvento.length());
                }
                urlFinal = urlFacultad + "/" + urlEvento;
            }
            return urlFinal;
        }
        
        public List<EventoDTO> crawlerEventos (Long id_unidadAcademica){
            try {
                 
                List<EventoDTO> lstEventosDto = new ArrayList<EventoDTO>();
                List<Evento> lstEventos = new ArrayList<Evento>();
                
                List<String> lstTitulos = new ArrayList<String>();
                
                UnidadAcademica ua = persistenciaUa.obtener(id_unidadAcademica);
                lstEventos = persistencia.obtenerEventosDeUnidadAcademica(id_unidadAcademica);
                
                if(lstEventos.size() > 0){
                    
                    lstEventosDto = convertirEntidad(lstEventos);
                    System.out.println("*****************" + lstEventosDto.size());
                    
                }else{
                
                    Document docEventos = Jsoup.connect(ua.getUrl()).timeout(30*1000).get();
                    if(docEventos.select("body").val().trim().compareTo("")==0){
                        Elements scripts=docEventos.select("head>script");
                        for(Element script:scripts){
                            if(script.data().startsWith("location.href=")){
                                docEventos=Jsoup.connect(script.data().substring(script.data().indexOf("=\"")+2, script.data().length() - 1)).get();
                                break;
                            }
                        }
                    }

                    Elements urlEventos = docEventos.select("a[href*=\"event\"], a[class*=\"event\"]");
                    List<String> lstEventosFiltrada = new ArrayList<String>();
                    for(Element url:urlEventos){

                        String urlFinal = armarUrl(ua.getUrl().trim(), url.attr("href").trim());
                        //System.out.println("*************" + urlFinal);    
                        if (lstEventosFiltrada.contains(urlFinal))
                            continue;
                        else if(urlFinal.contains("eventos.uniandes.edu.co")){
                            continue;
                        }
                        else{
                            lstEventosFiltrada.add(urlFinal);
                            List<EventoDTO> lstEventosPorUrl = crearEventos(urlFinal,lstTitulos,ua.getUrl().trim());

                            for(EventoDTO eventoDto :lstEventosPorUrl){
                                UnidadAcademicaDTO unidadAcademica = new UnidadAcademicaDTO();
                                unidadAcademica.setId(id_unidadAcademica);

                                eventoDto.setUnidadAcademica(unidadAcademica);
                                lstEventosDto.add(eventoDto);
                                eventoDto = convertirEntidad(persistencia.guardar(convertirDTO(eventoDto)));
                            }

                        }
                    }

                    if(lstEventosDto.size() == 0)
                        System.out.println("NO HAY EVENTOS DISPONIBLES");
                }
                
                return lstEventosDto;

            } catch (Exception e) {
                LOG.log(Level.SEVERE, "Error al .... ", e);
            }  
            return null;
        }
        
                
        
        private List<EventoDTO> crearEventos(String url, List<String> lstTitulos, String urlFacultad){
            
            List<EventoDTO> lstEventos = new ArrayList<EventoDTO>();
            Document docEventos = null;
            
            try {
                System.out.println("URLLLLLLLLLLLLLLLLLLLLLLL*********" + url);
                docEventos = Jsoup.connect(url).timeout(30*1000).get();
            }
            catch (SocketTimeoutException ex){
                System.out.println("Error conexion --> co.edu.uniandes.bigdata.ProyectoBigData.logica.EventoLogica.crearEventos()");
                return lstEventos;
            }
            catch (IOException e) {
                System.out.println("Error conexion --> co.edu.uniandes.bigdata.ProyectoBigData.logica.EventoLogica.crearEventos()");
                return lstEventos;
            } 
            
            try{
                
                if(docEventos.select("body").val().trim().compareTo("")==0){
                    
                    EventoDTO eventoDto = new EventoDTO();
                    String nombre = "";
                    String fecha = "";
                    String descripcion = "";
                    String enlace = "";
                    String lugar = "";
                    String hora = "";
                    String palabrasClave = "";
                    String nombreContacto="";
                    String correoContacto="";
                    
                    //https://administracion.uniandes.edu.co/index.php/es/facultad/sobre-la-facultad/eventos/cat.listevents/2016/08/27/-
                    if(docEventos.select("div[class=\"col-xs-12 col-sm-12 col-md-12 col-lg-12 list-destacado\"]").first() != null){
                        Element informacion = docEventos.select("div[class=\"col-xs-12 col-sm-12 col-md-12 col-lg-12 list-destacado\"]").first();
                        if(informacion.select("div[class=\"title\"]").first() != null){
                            nombre = informacion.select("a").first().text();
                            enlace = informacion.select("a").first().attr("href");
                            enlace = armarUrl(urlFacultad, enlace);
                            if(!lstTitulos.contains(nombre)){
                        
                                if(informacion.select("div[class*=\"month\"]").first() != null){
                                    Calendar calendario = Calendar.getInstance();
                                    fecha = calendario.get(Calendar.YEAR) + "-" + convertirMeses(informacion.select("div[class*=\"month\"]").first().text().trim());
                                }
                                if(informacion.select("div[class*=\"day\"]").first() != null){
                                    fecha += "-" + informacion.select("div[class*=\"day\"]").first().text();
                                }

                                if(informacion.select("div[class*=\"description\"]").first() != null){
                                    descripcion = informacion.select("div[class*=\"description\"]").first().text();
                                }

                                eventoDto = new EventoDTO();
                                eventoDto.setNombre(nombre);
                                eventoDto.setFecha(fecha);
                                eventoDto.setDescripcion(descripcion);
                                eventoDto.setEnlace(enlace);
                                eventoDto.setPalabrasClave(palabrasClave);
                                eventoDto.setLugar(lugar);
                                eventoDto.setHora(hora);
                                eventoDto.setNombreContacto(nombreContacto);
                                eventoDto.setCorreoContacto(correoContacto);

                                lstEventos.add(eventoDto);
                                lstTitulos.add(nombre);
                            }
                        }
                    }
                    
                    Elements eventos = docEventos.select("div[class=\"col-xs-12 col-sm-12 col-md-8 col-lg-8 list-event-info\"]");
                    for(Element informacion:eventos){
                        if(informacion.select("div[class*=\"title\"]").first() != null){
                            nombre = informacion.select("a").first().text();
                            enlace = informacion.select("a").first().attr("href");
                            enlace = armarUrl(urlFacultad, enlace);
                            if(lstTitulos.contains(nombre))
                                break;
                        }else
                        {
                            break;  //si no cumple con las 2 primeras etiquetas no siga
                        }
                        
                        if(informacion.select("div[class*=\"month\"]").first() != null){
                            Calendar calendario = Calendar.getInstance();
                            fecha = calendario.get(Calendar.YEAR) + "-" + convertirMeses(informacion.select("div[class*=\"month\"]").first().text().trim());
                        }
                        if(informacion.select("div[class*=\"day\"]").first() != null){
                            fecha += "-" + informacion.select("div[class*=\"day\"]").first().text();
                        }
                        
                        if(informacion.select("div[class*=\"description\"]").first() != null){
                            descripcion = informacion.select("div[class*=\"description\"]").first().text();
                        }
                            
                        eventoDto = new EventoDTO();
                        eventoDto.setNombre(nombre);
                        eventoDto.setFecha(fecha);
                        eventoDto.setDescripcion(descripcion);
                        eventoDto.setEnlace(enlace);
                        eventoDto.setPalabrasClave(palabrasClave);
                        eventoDto.setLugar(lugar);
                        eventoDto.setHora(hora);
                        eventoDto.setNombreContacto(nombreContacto);
                        eventoDto.setCorreoContacto(correoContacto);
                        
                        lstEventos.add(eventoDto);
                        lstTitulos.add(nombre);
                    }
                    
                    
                    //https://administracion.uniandes.edu.co/index.php/es/facultad/sobre-la-facultad/eventos/icalrepeat.detail/2016/08/30/110/28|14|15|17|18|19|20/la-importancia-de-hacer-networking
                    eventos = docEventos.select("div[class=\"row col-xs-12 col-sm-12 col-md-12 col-lg-12 event-detail\"]");
                    for(Element informacion:eventos){
                        if(informacion.select("h2").first() != null){
                            nombre = informacion.select("h2").first().text().trim();
                            if(lstTitulos.contains(nombre))
                                break;
                        }else
                        {
                            break;  //si no cumple con las 2 primeras etiquetas no siga
                        }
                        
                        
                        if(informacion.select("div[class*=\"month\"]").first() != null){
                            fecha = informacion.select("div[class*=\"month\"]").first().text();
                        }
                        if(informacion.select("div[class*=\"day\"]").first() != null){
                            fecha += " - " + informacion.select("div[class*=\"day\"]").first().text();
                        }
                        if(informacion.select("div[class*=\"description\"]").first() != null){
                            descripcion = informacion.select("div[class*=\"description\"]").first().text();
                        }    
                        enlace = url;    
                            
                        eventoDto = new EventoDTO();
                        eventoDto.setNombre(nombre);
                        eventoDto.setFecha(fecha);
                        eventoDto.setDescripcion(descripcion);
                        eventoDto.setEnlace(enlace);
                        eventoDto.setPalabrasClave(palabrasClave);
                        eventoDto.setLugar(lugar);
                        eventoDto.setHora(hora);
                        eventoDto.setNombreContacto(nombreContacto);
                        eventoDto.setCorreoContacto(correoContacto);
                        lstEventos.add(eventoDto);
                        lstTitulos.add(nombre);
                        
                    }
                    
                    //https://facartes.uniandes.edu.co/index.php/eventos/day.listevents/2016/08/01/62|64    
                    eventos = docEventos.select("li[class=\"ev_td_li\"]");
                    for(Element informacion:eventos){
                        if(informacion.select("h2").first() != null){
                            nombre = informacion.select("h2").first().text();
                            if(lstTitulos.contains(nombre))
                                break;
                        }else
                        {
                            break;  //si no cumple con las 2 primeras etiquetas no siga
                        }
                        
                        if(informacion.select("span[class*=\"hf_event\"]").first() != null){
                            String auxFecha = informacion.select("span[class*=\"hf_event\"]").first().text().split("\\|")[1].trim();
                            fecha = auxFecha.split(" ")[3] + "-" + convertirMeses(auxFecha.split(" ")[2]) + "-" + auxFecha.split(" ")[1];
                            hora = informacion.select("span[class*=\"hf_event\"]").first().text().split("\\|")[0].trim();
                            if(hora.split(":").length > 1){
                                String auxHoraIzq = hora.split(":")[0].trim();
                                auxHoraIzq = auxHoraIzq.substring(auxHoraIzq.length()-2, auxHoraIzq.length());
                                String auxHoraDer = hora.split(":")[1].trim();
                                auxHoraDer = auxHoraDer.substring(0,2);
                                hora = auxHoraIzq + ":" + auxHoraDer;
                            }
                        }
                            
                        descripcion = informacion.text().replace(nombre, "").replace(fecha,"").trim();
                        
                        if(informacion.select("a[class*=\"ev_link_row\"]").first() != null){
                            enlace = armarUrl(urlFacultad,informacion.select("a[class*=\"ev_link_row\"]").first().attr("href"));
                        }
                                
                        eventoDto = new EventoDTO();
                        eventoDto.setNombre(nombre);
                        eventoDto.setFecha(fecha);
                        eventoDto.setDescripcion(descripcion);
                        eventoDto.setEnlace(enlace);
                        eventoDto.setPalabrasClave(palabrasClave);
                        eventoDto.setLugar(lugar);
                        eventoDto.setHora(hora);
                        eventoDto.setNombreContacto(nombreContacto);
                        eventoDto.setCorreoContacto(correoContacto);
                        
                        lstEventos.add(eventoDto);
                        lstTitulos.add(nombre);
                        
                    }
                    
                    //http://ciencias.uniandes.edu.co/eventos
                    eventos = docEventos.select("div[class=\"items-leading clearfix\"]");
                    for(Element informacion:eventos){
                        if(informacion.select("h2").first() != null){
                            nombre = informacion.select("h2").first().text();
                            if(lstTitulos.contains(nombre))
                                break;
                        }
                        else{
                            break;  //si no cumple con las 2 primeras etiquetas no siga
                        }
                    
                        if(informacion.select("table").first() != null){
                            Element tablaDescripcion = informacion.select("table").first();
                            Elements divDescripcion = tablaDescripcion.select("div");
                            for(Element div:divDescripcion){
                                descripcion += div.text();
                            }
                            eventoDto = new EventoDTO();
                            eventoDto.setNombre(nombre);
                            eventoDto.setFecha(fecha);
                            eventoDto.setDescripcion(descripcion);
                            eventoDto.setEnlace(url);
                            eventoDto.setPalabrasClave(palabrasClave);
                            eventoDto.setLugar(lugar);
                            eventoDto.setHora(hora);
                            eventoDto.setNombreContacto(nombreContacto);
                            eventoDto.setCorreoContacto(correoContacto);
                        
                            lstEventos.add(eventoDto);
                            lstTitulos.add(eventoDto.getNombre());
                        }
                        
                    }
                    
                    
                    //http://sistemas.uniandes.edu.co/es/inicio/eventos
                    eventos = docEventos.select("div[class=\"itemContainer itemContainerLast\"]");
                    for(Element informacion:eventos){
                        if(informacion.select("div[class*=\"catItemHeader\"]").first() != null){
                            nombre = informacion.select("a").first().text();
                            enlace = armarUrl(urlFacultad, informacion.select("a").first().attr("href").trim());
                            if(lstTitulos.contains(nombre))
                                break;
                        }else
                        {
                            break;  //si no cumple con las 2 primeras etiquetas no siga
                        }
                        
                        if(informacion.select("div[class=\"catItemExtraFields\"]").first() != null){
                            Element divCamposAdicionales1 = informacion.select("div[class=\"catItemExtraFields\"]").first();
                            Elements campos = divCamposAdicionales1.select("li");
                            for(Element campo : campos){
                                String labelCampo = campo.select("span[class=\"catItemExtraFieldsLabel\"]").first().text();
                                String valueCampo = campo.select("span[class=\"catItemExtraFieldsValue\"]").first().text();
                                
                                if(labelCampo.toUpperCase().contains("FECHA")){
                                    fecha = valueCampo.split(",")[1].trim();
                                    String dia = fecha.split(" ")[0];
                                    String mes = convertirMeses(fecha.split(" ")[1].toUpperCase().trim());
                                    String anio = fecha.split(" ")[2];
                                            
                                    fecha = anio + "-" + mes + "-" + dia;
                                }else if(labelCampo.toUpperCase().contains("HORA")){
                                    if(valueCampo.split(":")[0].length() == 1){
                                        hora = "0" + valueCampo.split(":")[0] + ":" + valueCampo.split(":")[1].substring(0,2);
                                    }else{
                                        hora = valueCampo.split(":")[0] + ":" + valueCampo.split(":")[0].substring(0,2);
                                    }
                                    
                                }else if(labelCampo.toUpperCase().contains("LUGAR")){
                                    lugar = valueCampo;
                                }
                            }
                        }

                        descripcion = informacion.select("div[class=\"catItemIntroText\"]").first().text();
                        
                        Element divTag = informacion.select("div[class=\"catItemTagsBlock\"]").first();
                        Elements tags = divTag.select("li");
                        for(Element tag : tags){
                            palabrasClave += tag.text() + ",";
                        }
                        
                        eventoDto = new EventoDTO();
                        eventoDto.setNombre(nombre);
                        eventoDto.setFecha(fecha);
                        eventoDto.setDescripcion(descripcion);
                        eventoDto.setEnlace(enlace);
                        eventoDto.setPalabrasClave(palabrasClave);
                        eventoDto.setLugar(lugar);
                        eventoDto.setHora(hora);
                        eventoDto.setNombreContacto(nombreContacto);
                        eventoDto.setCorreoContacto(correoContacto);
                        
                        lstEventos.add(eventoDto);
                        lstTitulos.add(nombre);
                    }
                }
                return lstEventos;
            } catch (Exception e) {
                //e.printStackTrace();
                LOG.log(Level.SEVERE,"ERROR --> EventoLogica.crawlerEventos()", e);
            }  
            return null;
            
        }
        
        public List<EventoDTO> obtenerEventosFiltro(String fecha, String hora, Long id_unidadAcademica){
            
            List<EventoDTO> lstEventosDto = new ArrayList<EventoDTO>();
            List<Evento> lstEventos = new ArrayList<Evento>();
             
            if(fecha.contains("none") && hora.contains("none")){
                lstEventos = persistencia.obtenerEventosDeUnidadAcademica(id_unidadAcademica);
            }else{
                lstEventos = persistencia.obtenerEventosPorFiltro(fecha, hora, id_unidadAcademica);
            }
            
            lstEventosDto = convertirEntidad(lstEventos);
            
            return lstEventosDto;
        }
        
        public String convertirMeses(String mes){
            String resultado = "01";
            switch (mes){
                case "ENERO":
                case "ENEO":    
                    resultado = "01";
                    break;
                case "FEBRERO":
                case "FEB":
                    resultado = "02";
                    break;
                case "MARZO":
                case "MAR":    
                    resultado = "03";
                    break;
                case "ABRIL":
                case "ABR":    
                    resultado = "04";
                    break;
                case "MAYO":
                case "MAY":    
                    resultado = "05";
                    break;
                case "JUNIO":
                case "JUN":    
                    resultado = "06";
                    break;
                case "JULIO":
                case "JUL":    
                    resultado = "07";
                    break;
                case "AGOSTO":
                case "AGO":    
                    resultado = "08";
                    break;
                case "SEPTIEMBRE":   
                case "SEPT":       
                    resultado = "09";
                    break;
                case "OCTUBRE":   
                case "OCT":       
                    resultado = "10";
                    break;
                case "NOVIEMBRE":   
                case "NOV":       
                    resultado = "11";
                    break;
                case "DICIEMBRE":   
                case "DIC":       
                    resultado = "12";
                    break;
            }
            return resultado;
        }
}
    