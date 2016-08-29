package co.edu.uniandes.bigdata.ProyectoBigData.logica;

import co.edu.uniandes.bigdata.ProyectoBigData.dto.*;
import co.edu.uniandes.bigdata.ProyectoBigData.persistencia.*;
import co.edu.uniandes.bigdata.ProyectoBigData.persistencia.entity.*;
import java.io.Console;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.*;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
			if(dto.getFecha()!=null){
				try {
					entidad.setFecha(fecha.parse(dto.getFecha()));
				} catch (ParseException ex) {
		            throw new RuntimeException("Error al convertir la fecha Fecha " + dto.getFecha());
		        }
			}
			entidad.setHora(dto.getHora());
			entidad.setLugar(dto.getLugar());
			entidad.setNombreContacto(dto.getNombreContacto());
			entidad.setCorreoContacto(dto.getCorreoContacto());
			entidad.setPalabrasClave(dto.getPalabrasClave());
		
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
			if(entidad.getFecha()!=null){
				dto.setFecha(fecha.format(entidad.getFecha()));
			}
			dto.setHora(entidad.getHora());
			dto.setLugar(entidad.getLugar());
			dto.setNombreContacto(entidad.getNombreContacto());
			dto.setCorreoContacto(entidad.getCorreoContacto());
			dto.setPalabrasClave(entidad.getPalabrasClave());
		
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
	
        public List<EventoDTO> crawlerEventos (Long id_unidadAcademica){
            try {
                 
                List<EventoDTO> lstEventos = new ArrayList<EventoDTO>();
                
                UnidadAcademica ua = persistenciaUa.obtener(id_unidadAcademica);
                
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
                
                Elements eventos = docEventos.select("a[href*=\"event\"], a[class*=\"event\"]");
                for(Element url:eventos){
                    EventoDTO eventoDto = new EventoDTO();
                    eventoDto.setNombre("NombreEvento");
                    eventoDto.setCorreoContacto(url.text());
                    lstEventos.add(eventoDto);
                    System.out.println("eventos: "+url);
                }
                
                return lstEventos;

            } catch (Exception e) {
                System.out.println("EventoLogica.crawlerEventos()" + e.toString());
            }  
            return null;
        }
}
