
package ujaen.proyecto.proyecto_dae.beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ujaen.proyecto.proyecto_dae.servicios.dto.EventoDTO;
import ujaen.proyecto.proyecto_dae.servicios.EventoService;
import ujaen.proyecto.proyecto_dae.excepciones.EventoNoExiste;
import ujaen.proyecto.proyecto_dae.excepciones.IdentificacionErronea;
import ujaen.proyecto.proyecto_dae.beans.Usuario;
import ujaen.proyecto.proyecto_dae.servicios.dto.UsuarioDTO;
import ujaen.proyecto.proyecto_dae.servicios.UsuarioService;

/**
 *
 * @author adria
 */
public class GestorEventos implements EventoService, UsuarioService {
    private Map<String, Evento> eventos;
    private Map<String, Usuario> usuarios;
    private Map<Integer, Usuario> sesiones;
    
    public GestorEventos() {
        eventos = new HashMap<>();
        usuarios = new HashMap<>();
        sesiones = new HashMap<>();
    }
    
    @Override
    public Collection<EventoDTO> listaEventos() {
        Collection<EventoDTO> e = new ArrayList<>();
        for (Evento evento : eventos.values()) {
            e.add(evento.getEventoDTO());
        }
        return e;
    }

    @Override
    public Collection<UsuarioDTO> listaAsistentes(EventoDTO evento) {
        Evento e = obtenerEvento(evento.getTitulo());
        if ( e == null ) return null;
        Collection<UsuarioDTO> usuarios = new ArrayList<>();
        for (Usuario asistente : e.getAsistentes()) {
            usuarios.add(asistente.getUsuarioDTO());
        }
        return usuarios;
    }

    @Override
    public EventoDTO buscarEvento(String titulo) { //TODO: Método buscarEvento de la clase ujaen.proyecto.proyecto_dae.evento.EventoServiceImpl
        for (Evento evento : eventos.values()) {
            if ( titulo.equals(evento.getTitulo())) {
                return evento.getEventoDTO();
            }
        }
        return null;
    }

    @Override
    public Collection<EventoDTO> buscarEvento(Tipo tipo) {
        Collection<EventoDTO> busq = new ArrayList<>();
        for (Evento evento : eventos.values()) {
            if ( tipo == evento.getTipo() ) {
                busq.add(evento.getEventoDTO());
            }
        }
        return busq;
    }

    @Override
    public Collection<EventoDTO> buscarEvento(Tipo tipo, String descripcion) { //TODO: Método buscarEvento con filtrado, hay mejores clases a contains
        Collection<EventoDTO> busq = new ArrayList<>();
        for (Evento evento : eventos.values()) {
            if ( tipo == evento.getTipo() && evento.getDescripcion().contains(descripcion) ) {
                busq.add(evento.getEventoDTO());
            }
        }
        return busq;
    }

    @Override
    public int getNUsuarios() {
        return usuarios.size();
    }
    
    @Override
    public int registrarUsuario(String nombre, String pass1, String pass2, String email) {
        Usuario usuario = null;
        int sesion = -1;
        if ( obtenerUsuario(nombre) != null ) return -1;
        
        if ( pass1.equals(pass2) ) {
            usuario = new Usuario(nombre, email, pass1);
            usuarios.put(nombre, usuario);
            sesion = usuario.getToken();
            sesiones.put(sesion, usuario);
        }
        
        return sesion;
    }

    @Override
    public int identificarUsuario(String nombre, String pass) {
        Usuario usuario = obtenerUsuario(nombre);
        if ( usuario == null ) return -1;
        int sesion = usuario.getToken();
        
        if ( nombre.equals(usuario.getNombre()) && pass.equals(usuario.getPassword()) ) {
            sesiones.put(sesion, usuario);
        }
        
        return sesion;
    }
    
    @Override
    public void cerrarSesionUsuario(int sesion) {
        sesiones.remove(sesion);
    }

    @Override
    public Collection<EventoDTO> listaEventosInscrito(int sesion) { //TODO: Añadir los que está en lista de espera
        Usuario usuario = obtenerSesion(sesion);
        
        if ( usuario == null ) return null;
        
        List<EventoDTO> eventosDTO = new ArrayList<>();
        
        for (Evento evento : usuario.getEventosInscrito() ) {
            eventosDTO.add(evento.getEventoDTO());
        }
        return eventosDTO;
    }

    @Override
    public Collection<EventoDTO> listaEventosOrganizador(int sesion) {
        Usuario usuario = obtenerSesion(sesion);
        
        if ( usuario == null ) return null;
        
        List<EventoDTO> eventosDTO = new ArrayList<>();
        
        for (Evento evento : usuario.getEventosOrganizador()) {
            eventosDTO.add(evento.getEventoDTO());
        }
        return eventosDTO;
    }  

    @Override
    public EventoDTO crearEvento(String titulo, String descripcion, String localizacion, Tipo tipo, Calendar fecha, int nMax, int sesion) {
        Usuario usuario = obtenerSesion(sesion);
        
        if ( usuario == null ) return null;
        if ( obtenerEvento(titulo) != null ) return null;
        
        Evento evento = new Evento(nMax, titulo, descripcion, localizacion, tipo, fecha, usuario);
        usuario.agregarEventoOrganizador(evento);
        eventos.put(titulo, evento);
        return evento.getEventoDTO();
    }

    @Override
    public void inscribirUsuario(int sesion, EventoDTO evento) throws IdentificacionErronea, EventoNoExiste {
        Usuario usuario = obtenerSesion(sesion);
        Evento e = obtenerEvento(evento.getTitulo());
        
        if ( usuario == null ) throw new IdentificacionErronea("Usuario o contraseña incorrectos"); //TODO: Configurar Throw Exception correcta
        if ( e == null ) throw new EventoNoExiste("El evento no existe");
        
        usuario.agregarEventoAsistente(e);
        e.agregarAsistente(usuario);
    }
    
    @Override
    public void cancelarEvento(int sesion, EventoDTO evento) throws IdentificacionErronea, EventoNoExiste {
        Usuario usuario = obtenerSesion(sesion);
        Evento e = obtenerEvento(evento.getTitulo());
        
        if ( usuario == null ) throw new IdentificacionErronea("Usuario o contraseña incorrectos"); //TODO: Configurar Throw Exception correcta
        if ( e == null ) throw new EventoNoExiste("El evento no existe");
        
        if ( e.getOrganizador().getNombre().equals(usuario.getNombre()) ) {
            for ( Usuario u : e.getAsistentes() ) {
                u.eliminarEventoAsistente(e);
            }
            usuario.eliminarEventoOrganizado(e); //TODO: Eliminar el evento de la lista de cada usuario que tiene de a que eventos asistirá
            eventos.remove(e.getTitulo());
        }
    }

    @Override
    public void cancelarAsistencia(int sesion, EventoDTO evento) throws IdentificacionErronea, EventoNoExiste {
        Usuario usuario = obtenerSesion(sesion);
        Evento e = obtenerEvento(evento.getTitulo());
        
        if ( usuario == null ) throw new IdentificacionErronea("Usuario o contraseña incorrectos"); //TODO: Configurar Throw Exception correcta
        if ( e == null ) throw new EventoNoExiste("El evento no existe");
        
        e.quitarAsistente(usuario);
        usuario.eliminarEventoAsistente(e);
    }

    @Override
    public int getNEventos() {
        return eventos.size();
    }
    
    private Usuario obtenerUsuario(String nombre) {
        return usuarios.get(nombre);
    }
    
    private Usuario obtenerSesion(int sesion) {
        return sesiones.get(sesion);
    }
    
    private Evento obtenerEvento(String titulo) {
        return eventos.get(titulo);
    }
}
