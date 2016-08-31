package co.edu.uniandes.bigdata.ProyectoBigData.logica;

import co.edu.uniandes.bigdata.ProyectoBigData.dto.*;
import co.edu.uniandes.bigdata.ProyectoBigData.persistencia.*;
import co.edu.uniandes.bigdata.ProyectoBigData.persistencia.entity.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import javax.persistence.*;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * @generated
 */
@Stateless
public class FeedsLogica {

    @EJB
    private FeedsDAO persistencia;
    
    /**
     * @generated
     */
    public List<FeedsDTO> obtenerTodos() {
        return convertirEntidad(persistencia.obtenerTodos());
    }

    /**
     * @generated
     */
    public FeedsDTO obtener(Long id) {
        return convertirEntidad(persistencia.obtener(id));
    }

    /**
     * @generated
     */
    public FeedsDTO guardar(FeedsDTO dto) {
        return convertirEntidad(persistencia.guardar(convertirDTO(dto)));
    }

    /**
     * @generated
     */
    public void borrar(Long id) {
        persistencia.borrar(id);
    }

    /**
     * @generated
     */
    public void actualizar(FeedsDTO dto) {
        persistencia.actualizar(convertirDTO(dto));
    }

    /**
     * @generated
     */
    private Feeds convertirDTO(FeedsDTO dto) {
        if (dto == null) {
            return null;
        }
        Feeds entidad = new Feeds();
        entidad.setId(dto.getId());
        entidad.setCategoria(dto.getCategoria());
        entidad.setFuente(dto.getFuente());
        entidad.setTitulo(dto.getTitulo());
        entidad.setUrl(dto.getUrl());
        entidad.setPublicado(dto.getPublicado());

        return entidad;
    }

    /**
     * @generated
     */
    private List<Feeds> convertirDTO(List<FeedsDTO> dtos) {
        List<Feeds> entidades = new ArrayList<Feeds>();
        for (FeedsDTO dto : dtos) {
            entidades.add(convertirDTO(dto));
        }
        return entidades;
    }

    /**
     * @generated
     */
    private FeedsDTO convertirEntidad(Feeds entidad) {
        FeedsDTO dto = new FeedsDTO();
        dto.setId(entidad.getId());
        dto.setCategoria(entidad.getCategoria());
        dto.setFuente(entidad.getFuente());
        dto.setTitulo(entidad.getTitulo());
        dto.setUrl(entidad.getUrl());
        dto.setPublicado(entidad.getPublicado());

        return dto;
    }

    /**
     * @generated
     */
    private List<FeedsDTO> convertirEntidad(List<Feeds> entidades) {
        List<FeedsDTO> dtos = new ArrayList<FeedsDTO>();
        for (Feeds entidad : entidades) {
            dtos.add(convertirEntidad(entidad));
        }
        return dtos;
    }

    public List<FeedsDTO> obtenerTodosLosFeeds() {

        try {
            List<FeedsDTO> lstFeedsDto = new ArrayList<>();
            FeedsReader test = new FeedsReader();
            test.updateFeeds();

            List<Feed> feeds = test.filterFeedsXquery("All", "title", "the ", false);
            System.out.println("****" + feeds.size());
            for (Iterator<Feed> iterator = feeds.iterator(); iterator.hasNext();) {
                Feed feed = iterator.next();
                FeedsDTO feedDto = new FeedsDTO();
                feedDto.setCategoria(feed.getCategory());
                feedDto.setTitulo(feed.getTitle());
                feedDto.setFuente(feed.getSource());
                feedDto.setUrl(feed.getUrl());
                feedDto.setPublicado(feed.getPubDate());
                lstFeedsDto.add(feedDto);
                System.out.println(feed.getTitle());
            }

            return lstFeedsDto;
        } catch (Exception e) {
            System.out.println("co.edu.uniandes.bigdata.ProyectoBigData.logica.FeedsLogica.obtenerPorFiltro()" + e.toString());
            e.printStackTrace();
        }

        return null;
    }
    
    public List<FeedsDTO> obtenerPorFiltro(String categoria, String filtroFeed, String texto, boolean excluyente) {

        try {
            List<FeedsDTO> lstFeedsDto = new ArrayList<>();
            FeedsReader test = new FeedsReader();
            test.updateFeeds();

            List<Feed> feeds = test.filterFeedsXquery(categoria, filtroFeed, texto, excluyente);
            System.out.println("****" + feeds.size());
            for (Iterator<Feed> iterator = feeds.iterator(); iterator.hasNext();) {
                Feed feed = iterator.next();
                FeedsDTO feedDto = new FeedsDTO();
                feedDto.setCategoria(feed.getCategory());
                feedDto.setTitulo(feed.getTitle());
                feedDto.setFuente(feed.getSource());
                feedDto.setUrl(feed.getUrl());
                feedDto.setPublicado(feed.getPubDate());
                lstFeedsDto.add(feedDto);
                System.out.println(feed.getTitle());
            }

            return lstFeedsDto;
        } catch (Exception e) {
            System.out.println("co.edu.uniandes.bigdata.ProyectoBigData.logica.FeedsLogica.obtenerPorFiltro()" + e.toString());
            e.printStackTrace();
        }

        return null;
    }
    
    public List<FeedsDTO> obtenerPorFiltroRegex(String categoria, String texto, boolean excluyente) {

        try {
            List<FeedsDTO> lstFeedsDto = new ArrayList<>();
            FeedsReader fr = new FeedsReader();
            fr.updateFeeds();

            List<Feed> feeds = fr.filterFeedsRegex(categoria, texto, excluyente);
            System.out.println("****" + feeds.size());
            for (Iterator<Feed> iterator = feeds.iterator(); iterator.hasNext();) {
                Feed feed = iterator.next();
                FeedsDTO feedDto = new FeedsDTO();
                feedDto.setCategoria(feed.getCategory());
                feedDto.setTitulo(feed.getTitle());
                feedDto.setFuente(feed.getSource());
                feedDto.setUrl(feed.getUrl());
                feedDto.setPublicado(feed.getPubDate());
                lstFeedsDto.add(feedDto);
                System.out.println(feed.getTitle());
            }

            return lstFeedsDto;
        } catch (Exception e) {
            System.out.println("co.edu.uniandes.bigdata.ProyectoBigData.logica.FeedsLogica.obtenerPorFiltro()" + e.toString());
            e.printStackTrace();
        }

        return null;
    }
}
