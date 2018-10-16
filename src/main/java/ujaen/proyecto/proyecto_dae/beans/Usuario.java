
/**
 * Clase que define un usuario
 * @author Adrián Perez López
 * @author Rafael Galán Ruiz
 */
package ujaen.proyecto.proyecto_dae.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import ujaen.proyecto.proyecto_dae.servicios.dto.UsuarioDTO;


public class Usuario {
    private List<Evento> eventosInscrito;
    private List<Evento> eventosOrganizador;
    
    private static int id = 1;
    private int idUsuario;
    private String nombre;
    private String email;
    private String password;
    private int token;
    
    private Random aleatorio;
    
    public Usuario() {
        eventosInscrito = new ArrayList<>();
        eventosOrganizador = new ArrayList<>();
    }

    public Usuario(String nombre, String email, String password) {
        aleatorio = new Random();
        this.idUsuario = Usuario.id++;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.eventosInscrito = new ArrayList<>();
        this.eventosOrganizador = new ArrayList<>();
        this.token = aleatorio.nextInt(Integer.MAX_VALUE);
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public List<Evento> getEventosInscrito() {
        return eventosInscrito;
    }

    public void setEventosInscrito(List<Evento> eventosInscrito) {
        this.eventosInscrito = eventosInscrito;
    }
    
    public List<Evento> getEventosOrganizador() {
        return eventosOrganizador;
    }
    
    public void agregarEventoAsistente(Evento evento) {
        if ( !eventosOrganizador.contains(evento) ) {
            this.eventosInscrito.add(evento);
        }
    }
    
    public void agregarEventoOrganizador(Evento evento) {
        if ( !eventosInscrito.contains(evento) ) {
            this.eventosOrganizador.add(evento);
        }
    }

    public void setEventosOrganizador(List<Evento> eventosOrganizador) {
        this.eventosOrganizador = eventosOrganizador;
    }
    
    public void eliminarEventoAsistente(Evento evento) {
        this.eventosInscrito.remove(evento);
    }
    
    public void eliminarEventoOrganizado(Evento evento) {
        this.eventosOrganizador.remove(evento);
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }
    
    public void generarNuevoToken() {
        this.token = aleatorio.nextInt(Integer.MAX_VALUE);
    }
    
    public UsuarioDTO getUsuarioDTO() {
        return new UsuarioDTO(idUsuario, nombre, email);
    }
}