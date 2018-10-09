
package ujaen.proyecto.proyecto_dae.evento;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ujaen.proyecto.proyecto_dae.usuario.Usuario;


/**
 *
 * @author adpl
 * @author Rafa
 */
public class Evento {
    private List<Usuario> asistentes;
    private List<Usuario> listaEspera;
    
    private int id;
    private int nMax;
    private String titulo;
    private String localizacion;
    private String descripcion;
    private Date fecha;
    private Tipo tipo;
    private Usuario organizador;

    public Evento() {
    }

    public Evento(int id, int nMax, String titulo, String descripcion, String localizacion, Tipo tipo, Date fecha, Usuario usuario) {
        this.id = id;
        this.nMax = nMax;
        this.titulo = titulo;
        this.localizacion = localizacion;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.tipo = tipo;
        this.asistentes = new ArrayList<>();
        this.listaEspera = new ArrayList<>();
        this.organizador = usuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getnMax() {
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
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

    public List<Usuario> getListaEspera() {
        return listaEspera;
    }

    public void setListaEspera(List<Usuario> listaEspera) {
        this.listaEspera = listaEspera;
    }

    public Usuario getOrganizador() {
        return organizador;
    }

    public void setOrganizador(Usuario organizador) {
        this.organizador = organizador;
    }
    
    public void agregarAsistente(Usuario usuario) {
        if ( asistentes.contains(usuario) || listaEspera.contains(usuario)) {
            System.out.println("El usuario ya está inscrito en este evento");
        } else {
            if ( asistentes.size() < nMax ) {
                asistentes.add(usuario);
                System.out.println("Usuario agregado a la lista de asistentes");
            } else {
                listaEspera.add(usuario);
                System.out.println("Usuario agregado a la lista de espera");
            }
        }
    }
    
    public int getPlazasDisponibles() {
        return nMax - asistentes.size();
    }
    
    @Override
    public String toString() {
        return "Evento " + titulo + " | Descripción: " + descripcion 
                + " Lugar: " + localizacion + " Tipo: " + tipo.toString();
    }
    
    public EventoDTO getEventoDTO(){
        return new EventoDTO(id, nMax, titulo, localizacion, descripcion, fecha, tipo, organizador);
    }
}
