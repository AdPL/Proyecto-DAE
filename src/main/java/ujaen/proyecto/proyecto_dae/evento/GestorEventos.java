
package ujaen.proyecto.proyecto_dae.evento;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ujaen.proyecto.proyecto_dae.excepciones.EventoNoExiste;
import ujaen.proyecto.proyecto_dae.excepciones.IdentificacionErronea;
import ujaen.proyecto.proyecto_dae.usuario.Usuario;
import ujaen.proyecto.proyecto_dae.usuario.UsuarioDTO;
import ujaen.proyecto.proyecto_dae.usuario.UsuarioService;

/**
 *
 * @author adria
 */
public class GestorEventos implements EventoService, UsuarioService {
    private Map<Integer, Evento> eventos;
    private Map<Integer, Usuario> usuarios;
    
    public GestorEventos() {
        eventos = new HashMap<>();
        usuarios = new HashMap<>();
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
        Evento e = buscar(evento.getTitulo());
        if ( e == null) return null;
        Collection<UsuarioDTO> usuarios = new ArrayList<>();
        for (Usuario asistente : e.getAsistentes()) {
            usuarios.add(asistente.getUsuarioDTO());
        }
        usuarios.add(e.getOrganizador().getUsuarioDTO());
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
        if ( pass1.equals(pass2) ) {
            for (Usuario u : usuarios.values() ) {
                if ( nombre.equals(u.getNombre()) || email.equals(u.getEmail()) ) {
                    System.out.println("Ese nombre de usuario o email ya está registrado");
                    return -1;
                }
            }
            usuario = new Usuario(nombre, email, pass1);
            usuarios.put(usuario.getIdUsuario(), usuario);
        } else {
            System.out.println("ERROR: Las contraseñas no coinciden.");
        }
        return usuario != null ? usuario.getToken() : -1;
    }

    @Override
    public int identificarUsuario(String identificacion, String pass) {
        for (Usuario usuario : usuarios.values()) { //TODO: Optimizar búsquedas
            if ((identificacion.equals(usuario.getNombre()) || identificacion.equals(usuario.getEmail()) ) && pass.equals(usuario.getPassword())) {
                return usuario.getToken();
            }
        }
        System.out.println("ERROR: Datos incorrectos");
        return -1;
    }

    @Override
    public Collection<EventoDTO> listaEventosInscrito(int sesion) { //TODO: Añadir los que está en lista de espera
        Usuario usuario = comprobarSesion(sesion);
        
        if ( usuario == null ) return null;
        
        List<EventoDTO> eventosDTO = new ArrayList<>();
        
        for (Evento evento : usuario.getEventosInscrito() ) {
            eventosDTO.add(evento.getEventoDTO());
        }
        return eventosDTO;
    }

    @Override
    public Collection<EventoDTO> listaEventosOrganizador(int sesion) {
        Usuario usuario = comprobarSesion(sesion);
        
        if ( usuario == null ) return null;
        
        List<EventoDTO> eventosDTO = new ArrayList<>();
        
        for (Evento evento : usuario.getEventosOrganizador()) {
            eventosDTO.add(evento.getEventoDTO());
        }
        return eventosDTO;
    }  

    @Override
    public EventoDTO crearEvento(String titulo, String descripcion, String localizacion, Tipo tipo, Calendar fecha, int nMax, int sesion) {
        Usuario usuario = comprobarSesion(sesion);
        
        if ( usuario == null ) return null;
        
        for (Evento evento : eventos.values()) {
            if ( titulo.equals(evento.getTitulo()) ) return null;
        }
        Evento evento = new Evento(1, nMax, titulo, descripcion, localizacion, tipo, fecha, usuario);
        usuario.agregarEventoOrganizador(evento);
        eventos.put(1, evento);
        return evento.getEventoDTO();
    }

    @Override
    public void inscribirUsuario(int sesion, EventoDTO evento) throws IdentificacionErronea, EventoNoExiste {
        Usuario usuario = comprobarSesion(sesion);
        Evento e = buscar(evento.getTitulo());
        
        if ( usuario == null ) throw new IdentificacionErronea("Usuario o contraseña incorrectos"); //TODO: Configurar Throw Exception correcta
        if ( e == null ) throw new EventoNoExiste("El evento no existe");
        
        usuario.agregarEventoAsistente(e);
        e.agregarAsistente(usuario);
        
        //TODO: Método inscribirUsuario de la clase ujaen.proyecto.proyecto_dae.evento.GestorEventos
    }
    
    @Override
    public void cancelarEvento(int sesion, EventoDTO evento) throws IdentificacionErronea, EventoNoExiste {
        Usuario usuario = comprobarSesion(sesion);
        Evento e = buscar(evento.getTitulo());
        
        if ( usuario == null ) throw new IdentificacionErronea("Usuario o contraseña incorrectos"); //TODO: Configurar Throw Exception correcta
        if ( e == null ) throw new EventoNoExiste("El evento no existe");
        
        if ( e.getOrganizador().getNombre().equals(usuario.getNombre())) {
            usuario.eliminarEventoOrganizado(e); //TODO: Eliminar el evento de la lista de cada usuario que tiene de a que eventos asistirá
            eventos.remove(e.getIdEvento());
        }
    }

    @Override
    public void cancelarAsistencia(int sesion, EventoDTO evento) throws IdentificacionErronea, EventoNoExiste {
        Usuario usuario = comprobarSesion(sesion);
        Evento e = buscar(evento.getTitulo());
        
        if ( usuario == null ) throw new IdentificacionErronea("Usuario o contraseña incorrectos"); //TODO: Configurar Throw Exception correcta
        if ( e == null ) throw new EventoNoExiste("El evento no existe");
        
        e.quitarAsistente(usuario);
        usuario.eliminarEventoAsistente(e);
    }

    @Override
    public int getNEventos() {
        return eventos.size();
    }
    
    private Evento buscar(String titulo) {
        for (Evento evento : eventos.values()) {
            if ( titulo.equals(evento.getTitulo())) {
                return evento;
            }
        }
        return null;
    }
    
    public Usuario comprobarSesion(int sesion) {
        for (Usuario usuario : usuarios.values()) {
            if ( usuario.getToken() == sesion ) {
                return usuario;
            }
        }
        return null;
    }
}
