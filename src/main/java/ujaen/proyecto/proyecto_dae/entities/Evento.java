/**
 * Clase que define un evento
 * @author Adrián Pérez López
 * @author Rafael Galán Ruiz
 */
package ujaen.proyecto.proyecto_dae.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.data.annotation.Version;
import ujaen.proyecto.proyecto_dae.beans.Tipo;
import ujaen.proyecto.proyecto_dae.servicios.dto.EventoDTO;

@Entity
public class Evento implements Serializable {
    @ManyToMany
    private List<Usuario> asistentes;
    @ManyToMany
    private Map<Calendar, Usuario> listaEspera;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    private int nMax;
    private String titulo;
    private String localizacion;
    private String descripcion;
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar fecha;
    private Tipo tipo;
    
    @ManyToOne
    private Usuario organizador;
    
    @Version
    int version;

    public Evento() {}

    public Evento(int nMax, String titulo, String descripcion, String localizacion, Tipo tipo, Calendar fecha, Usuario usuario) {
        this.nMax = nMax;
        this.titulo = titulo;
        this.localizacion = localizacion;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.tipo = tipo;
        this.asistentes = new ArrayList<>();
        this.listaEspera = new HashMap<>();
        this.organizador = usuario;
    }
    
    public int getId() {
        return id;
    }

    public int getnMax() {
        return nMax;
    }

    public void setnMax(Integer nMax) {
        this.nMax = nMax;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Calendar getFecha() {
        return fecha;
    }

    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public List<Usuario> getAsistentes() {
        return asistentes;
    }

    public void setAsistentes(List<Usuario> asistentes) {
        this.asistentes = asistentes;
    }

    public Usuario getOrganizador() {
        return organizador;
    }

    public void setOrganizador(Usuario organizador) {
        this.organizador = organizador;
    }

    public void agregarAsistente(Usuario usuario) {
        if ( asistentes.contains(usuario) ) {
            System.out.println("El usuario ya setá inscrito en este evento");
        } else {
            if ( asistentes.size() < nMax ) {
                asistentes.add(usuario);
                System.out.println("Usuario agregado a la lista de asistentes");
            } else {
                listaEspera.put(Calendar.getInstance(), usuario);
                System.out.println("Usuario agregado a la lista de espera");
            }
        }
    }

    public void quitarAsistente(Usuario usuario, Calendar fecha) {
        if ( asistentes.contains(usuario) ) {
            asistentes.remove(usuario);
            System.out.println("Usuario " + usuario.getNombre() + " cancela su asistencia al evento " + titulo);
            if ( !listaEspera.isEmpty() ) {
                System.out.println(listaEspera.get(fecha) + " es el primero de la lista de Espera, ahora está en asistentes");
                Usuario u = listaEspera.remove(fecha);
                asistentes.add(u);
            }
        } else if ( listaEspera.containsKey(usuario) ) {
            listaEspera.remove(usuario);
            System.out.println("Usuario " + usuario.getNombre() + " se cancela de la lista de espera al evento " + titulo);
        }
    }

    public Map<Calendar, Usuario> getListaEspera() {
        return listaEspera;
    }

    public void setListaEspera(Map<Calendar, Usuario> listaEspera) {
        this.listaEspera = listaEspera;
    }
    
    public int getPlazasDisponibles() {
        return nMax - asistentes.size();
    }

    @Override
    public String toString() {
        return "Evento " + titulo + " | Descripción: " + descripcion
                + " Lugar: " + localizacion + " Tipo: " + tipo.toString()
                + " Fecha: " + fecha.getTime().toString();
    }

    public EventoDTO getEventoDTO() {
        return new EventoDTO(id, nMax, titulo, localizacion, descripcion, fecha, tipo, organizador.getUsuarioDTO());
    }
}
