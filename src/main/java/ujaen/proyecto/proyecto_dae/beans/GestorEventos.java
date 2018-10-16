
/**
 * Clase que gestiona los eventos y usuarios
 * @author Adrián Pérez López
 * @author Rafael Galán Ruiz
 */

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

public class GestorEventos implements EventoService, UsuarioService {
    private Map<String, Evento> eventos;
    private Map<String, Usuario> usuarios;
    private Map<Integer, Usuario> sesiones;

    public GestorEventos() {
        eventos = new HashMap<>();
        usuarios = new HashMap<>();
        sesiones = new HashMap<>();
    }

    /**
     * Método que nos devuelve los asistentes a un evento
     * @param evento Evento del que se recopilaran los usuarios
     * @return Una lista con los asistentes al evento
     */
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

    /**
     * Método que devuelve el evento que se busca por su titulo
     * @param titulo Título del evento que se busca
     * @return Evento correspondiente al título buscado
     */
    @Override
    public EventoDTO buscarEvento(String titulo) { //TODO: Método buscarEvento de la clase ujaen.proyecto.proyecto_dae.evento.EventoServiceImpl
        for (Evento evento : eventos.values()) {
            if ( titulo.equals(evento.getTitulo())) {
                return evento.getEventoDTO();
            }
        }
        return null;
    }

    /**
     * Este método devuelve los eventos que coincide con el tipo que se ha pasado como argumento
     * @param tipo Tipo del evento
     * @return Colección de eventos del tipo consultado
     */
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

    /**
     * Método que devuelve los eventos que coinciden con el tipo y
     * con la descripción consultada
     * @param tipo Tipo del evento
     * @param descripcion Búsqueda en la descripción del evento
     * @return Los eventos que coinciden con el tipo y con la descripción
     */
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

    /**
     * Método para registrar un usuario en el sistema
     * @param nombre Nombre del usuario
     * @param pass1 Contraseña introducida
     * @param pass2 Comprobación de la contraseña (segundo campo)
     * @param email Email del usuario
     * @return Token de sesión si se completa el registro
     */
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

    /**
     * Devuelve una lista con los eventos a los que se ha inscrito un usuario
     * @param sesion Token de la sesión actual del usuario
     * @return Colección de eventos con los a los que se incrito el usuario
     */
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

    /**
     * Método que lista los eventos que organiza un usuario
     * @param sesion Token de la sesión actual del usuario
     * @return Colección con los eventos organizados por el usuario
     */
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

    /**
     * Método para crear un evento
     * @param titulo
     * @param descripcion
     * @param localizacion
     * @param tipo
     * @param fecha
     * @param nMax
     * @param sesion
     * @return El evento creado
     */
    @Override
    public EventoDTO crearEvento(String titulo, String descripcion, String localizacion, Tipo tipo, Calendar fecha, int nMax, int sesion) {
        Usuario usuario = obtenerSesion(sesion);

        if ( usuario == null ) return null;
        if ( obtenerEvento(titulo) != null ) return null;

        if( fecha.before(Calendar.getInstance()) ) return null;

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

    /**
     * Devuelve una Colección de eventosDTO con los eventos que están aún por celebrar
     * @param sesion
     * @return Colección de eventosDTO
     */
    @Override
    public Collection<EventoDTO> listaEventoPorCelebrar(int sesion) {
        Usuario usuario = obtenerSesion(sesion);
        Calendar fechaActual = Calendar.getInstance();
        if ( usuario == null ) return null;

        Collection<EventoDTO> eventosPendientes = new ArrayList<>();

        for ( Evento evento : usuario.getEventosInscrito() ) {
            if(fechaActual.before(evento.getFecha()))
                eventosPendientes.add(evento.getEventoDTO());
        }
        for ( Evento evento : usuario.getEventosOrganizador() ) {
            if( fechaActual.before(evento.getFecha()) )
                eventosPendientes.add(evento.getEventoDTO());
        }
        return eventosPendientes;
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

    @Override
    public Collection<EventoDTO> listaEventoCelebrados(int sesion) {
        throw new UnsupportedOperationException("Not supported yet."); //TODO: Método listaEventoCelebrados de la clase ujaen.proyecto.proyecto_dae.beans.GestorEventos
    }
}
