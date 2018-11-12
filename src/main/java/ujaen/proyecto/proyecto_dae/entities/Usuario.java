
/**
 * Clase que define un usuario
 * @author Adrián Perez López
 * @author Rafael Galán Ruiz
 */
package ujaen.proyecto.proyecto_dae.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import ujaen.proyecto.proyecto_dae.servicios.dto.UsuarioDTO;


@Entity
@Table(indexes = {@Index(name = "Index_nombres", columnList = "nombre", unique = true)})
public class Usuario implements Serializable {
    @ManyToMany(mappedBy="asistentes")
    private List<Evento> eventosInscrito;
    @ManyToMany(mappedBy="listaEspera")
    private List<Evento> eventosEspera;
    @OneToMany(mappedBy="organizador")
    private List<Evento> eventosOrganizador;
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    private String nombre;
    private String email;
    private String password;
    private int token;
    
    @Transient
    private Random aleatorio;
    
    public Usuario() {
        eventosInscrito = new ArrayList<>();
        eventosOrganizador = new ArrayList<>();
    }

    public Usuario(String nombre, String email, String password) {
        aleatorio = new Random();
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.eventosInscrito = new ArrayList<>();
        this.eventosOrganizador = new ArrayList<>();
        this.token = aleatorio.nextInt(Integer.MAX_VALUE);
    }

    public int getId() {
        return id;
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
        eventosInscrito.add(evento);
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

    public List<Evento> getEventosEspera() {
        return eventosEspera;
    }

    public void setEventosEspera(List<Evento> eventosEspera) {
        this.eventosEspera = eventosEspera;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }
    
    public void generarNuevoToken() {
        aleatorio = new Random();
        this.token = aleatorio.nextInt(Integer.MAX_VALUE);
    }
    
    public UsuarioDTO getUsuarioDTO() {
        return new UsuarioDTO(id, nombre, email);
    }
}