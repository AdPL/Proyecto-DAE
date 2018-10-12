/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ujaen.proyecto.proyecto_dae.evento;

import java.util.Calendar;
import ujaen.proyecto.proyecto_dae.usuario.UsuarioDTO;


/**
 *
 * @author Rafa
 */
public class EventoDTO {
    private int id;
    private int nMax;
    private String titulo;
    private String localizacion;
    private String descripcion;
    private Calendar fecha;
    private Tipo tipo;
    private UsuarioDTO organizador;

    public EventoDTO(int id, int nMax, String titulo, String localizacion, String descripcion, Calendar fecha, Tipo tipo, UsuarioDTO organizador) {
        this.id = id;
        this.nMax = nMax;
        this.titulo = titulo;
        this.localizacion = localizacion;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.tipo = tipo;
        this.organizador = organizador;
    }

    
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the nMax
     */
    public int getnMax() {
        return nMax;
    }

    /**
     * @param nMax the nMax to set
     */
    public void setnMax(int nMax) {
        this.nMax = nMax;
    }

    /**
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * @return the localizacion
     */
    public String getLocalizacion() {
        return localizacion;
    }

    /**
     * @param localizacion the localizacion to set
     */
    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the fecha
     */
    public Calendar getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the tipo
     */
    public Tipo getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the organizador
     */
    public UsuarioDTO getOrganizador() {
        return organizador;
    }

    /**
     * @param organizador the organizador to set
     */
    public void setOrganizador(UsuarioDTO organizador) {
        this.organizador = organizador;
    }
    
    @Override
    public String toString() {
        return "Evento " + titulo + " | Descripción: " + descripcion 
                + " Lugar: " + localizacion + " Tipo: " + tipo.toString();
    }
}
