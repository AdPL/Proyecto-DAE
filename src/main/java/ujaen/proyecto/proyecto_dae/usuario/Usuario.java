/**
 * Clase que define un usuario
 * @author Adrián Perez López
 * @author Rafael Galán Ruiz
 */
package ujaen.proyecto.proyecto_dae.usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import ujaen.proyecto.proyecto_dae.evento.Evento;


public class Usuario {
    private List<Evento> eventosInscrito;
    private List<Evento> eventosOrganizador;
    
    private int id;
    private String nombre;
    private String email;
    private String password;
    private int token;
    
    private Random aleatorio;
    
    public Usuario() {
        eventosInscrito = new ArrayList<>();
        eventosOrganizador = new ArrayList<>();
    }

    public Usuario(int id, String nombre, String email, String password) {
        aleatorio = new Random();
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.eventosInscrito = new ArrayList<>();
        this.eventosOrganizador = new ArrayList<>();
        this.token = aleatorio.nextInt(Integer.MAX_VALUE);
        System.out.println("Token: " + this.token);
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    
    public UsuarioDTO getUsuarioDTO() {
        return new UsuarioDTO(id, nombre, email);
    }
    
    
}
