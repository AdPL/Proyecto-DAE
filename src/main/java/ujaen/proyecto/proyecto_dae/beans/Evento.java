/**
 * Clase que define un evento
 * @author Adrián Pérez López
 * @author Rafael Galán Ruiz
 */
package ujaen.proyecto.proyecto_dae.evento;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import ujaen.proyecto.proyecto_dae.servicios.dto.EventoDTO;

public class Evento {
    private List<Usuario> asistentes;
    private List<Usuario> listaEspera;

    private static int id = 1;
    private int idEvento;
    private int nMax;
    private String titulo;
    private String localizacion;
    private String descripcion;
    private Calendar fecha;
    private Tipo tipo;
    private Usuario organizador;

    public Evento() {
    }

    public Evento(int nMax, String titulo, String descripcion, String localizacion, Tipo tipo, Calendar fecha, Usuario usuario) {
        this.idEvento = Evento.id++;
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

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
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

    public Collection<Usuario> getListaEspera() {
        return listaEspera;
    }

    public void setListaEspera(ArrayList<Usuario> listaEspera) {
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

    public void quitarAsistente(Usuario usuario) {
        if ( asistentes.contains(usuario) ) {
            asistentes.remove(usuario);
            System.out.println("Usuario " + usuario.getNombre() + " cancela su asistencia al evento " + titulo);
            if ( !listaEspera.isEmpty() ) {
                System.out.println(listaEspera.get(0).getNombre() + " es el primero de la lista de Espera, ahora está en asistentes");
                asistentes.add(listaEspera.remove(0));
            }
        } else if ( listaEspera.contains(usuario) ) {
            listaEspera.remove(usuario);
            System.out.println("Usuario " + usuario.getNombre() + " se cancela de la lista de espera al evento " + titulo);
        }
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
