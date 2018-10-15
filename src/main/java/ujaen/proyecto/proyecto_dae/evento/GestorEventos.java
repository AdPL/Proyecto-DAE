/**Gestor Eventos: Clase que implementa las interfaces UsuarioService.java y EventoService.java
 * @author Adrián Pérez López
 * @author Rafael Galán Ruiz
 */
package ujaen.proyecto.proyecto_dae.evento;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import ujaen.proyecto.proyecto_dae.usuario.Usuario;
import ujaen.proyecto.proyecto_dae.usuario.UsuarioDTO;
import ujaen.proyecto.proyecto_dae.usuario.UsuarioService;
import java.util.Calendar;


public class GestorEventos implements EventoService, UsuarioService {
    private List<Evento> eventos;
    private List<Usuario> usuarios;
    
    public GestorEventos() {
        eventos = new ArrayList<>();
        usuarios = new ArrayList<>();
    }
    
    @Override
    public Collection<EventoDTO> listaEventos() {
        Collection<EventoDTO> e = new ArrayList<>();
        for (Evento evento : eventos) {
            e.add(evento.getEventoDTO());
        }
        return e;
    }
    /**
     * Método que nos devuelve los asistentes a un evento
     * @author Adrián Pérez López
     * @author Rafael Galán Ruiz
     * @param evento
     * @return Una lista con los asistentes al evento
     */
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

    /**
     * Método que devuelve el evento que se busca por el titulo
     * @author Adrián Pérez López
     * @author Rafael Galán Ruiz
     * @param titulo
     * @return Un evento con el título igual al que se ha pasado como argumento
     */
    @Override
    public EventoDTO buscarEvento(String titulo) { //TODO: Método buscarEvento de la clase ujaen.proyecto.proyecto_dae.evento.EventoServiceImpl
        for (Evento evento : eventos) {
            if ( titulo.equals(evento.getTitulo())) {
                return evento.getEventoDTO();
            }
        }
        return null;
    }

    
    /**
     * Este método devuelve los eventos que coincide con el tipo que se ha pasado como argumento
     * @author Adrián Pérez López
     * @author Rafael Galán Ruiz
     * @param tipo
     * @return Los eventos que coincide con el tipo que se ha pasado como argumento
     */
    @Override
    public Collection<EventoDTO> buscarEvento(Tipo tipo) {
        Collection<EventoDTO> busq = new ArrayList<>();
        for (Evento evento : eventos) {
            if ( tipo == evento.getTipo() ) {
                busq.add(evento.getEventoDTO());
            }
        }
        return busq;
    }

    /**
     * Método que devuelve los eventos que conice con el tipo y con la descripción que se han pasado como argumentos
     * @author Adrián Pérez López
     * @author Rafael Galán Ruiz
     * @param tipo
     * @param descripcion
     * @return Los eventos que coinciden con el tipo y con la descripción que se han pasado como argumentos
     */
    @Override
    public Collection<EventoDTO> buscarEvento(Tipo tipo, String descripcion) { //TODO: Método buscarEvento con filtrado, hay mejores clases a contains
        Collection<EventoDTO> busq = new ArrayList<>();
        for (Evento evento : eventos) {
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
    /**
     * Método para registrar un usuario en el sistema
     * @param nombre
     * @param pass1
     * @param pass2
     * @param email
     * @return 
     */
    @Override
    public int registrarUsuario(String nombre, String pass1, String pass2, String email) {
        Usuario usuario = null;
        if (pass1.equals(pass2)) {
            usuario = new Usuario(1, nombre, email, pass1);
            if ( !usuarios.contains(usuario) ) { //ToDo: No es válida la comparación
                usuarios.add(usuario);
            } else {
                System.out.println("ERROR: El usuario ya existe");
            }
        } else {
            System.out.println("ERROR: Las contraseñas no coinciden.");
        }
        return usuario != null ? usuario.getToken() : -1;
    }

    @Override
    public int identificarUsuario(String identificacion, String pass) {
        for (Usuario usuario : usuarios) {
            if ((identificacion.equals(usuario.getNombre()) || identificacion.equals(usuario.getEmail()) ) && pass.equals(usuario.getPassword())) {
                return usuario.getToken();
            }
        }
        return -1;
    }

    /**
     * Este método devuelve una lista con los eventos a los que se ha inscrito un usuario
     * @author Adrián Pérez Lopez
     * @author Rafael Galán Ruiz
     * @param sesion
     * @return Lista de eventos con los a los que se incrito el usuario
     */
    @Override
    public Collection<EventoDTO> listaEventosInscrito(int sesion) {
        Usuario usuario = comprobarSesion(sesion);
        
        if ( usuario == null ) return null;
        
        List<EventoDTO> eventosDTO = new ArrayList<>();
        
        for (Evento evento : usuario.getEventosInscrito() ) {
            eventosDTO.add(evento.getEventoDTO());
        }
        return eventosDTO;
    }

    /**
     * Método que lista los eventos que organiza un usuario
     * @author Adrián Pérez López
     * @author Rafael Galán Ruiz
     * @param sesion
     * @return Una lista con los eventos organizados por el usuario
     */
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
 
    //Probar con otra forma de crear el evento
    /**
     * Método para crear un evento
     * @author Adrián Pérez López
     * @author Rafael Galán Ruiz
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
    public EventoDTO crearEvento(String titulo, String descripcion, String localizacion, Tipo tipo, Calendar fecha, int nMax, int sesion){
        Usuario usuario = comprobarSesion(sesion);
        Calendar fechaActual = Calendar.getInstance();
        
        if ( usuario == null ) return null;
        if(fecha.before(fechaActual)) return null;
        for (Evento evento : eventos) {
            if ( titulo.equals(evento.getTitulo()) ) return null;
        }
        Evento evento = new Evento(1, nMax, titulo, descripcion, localizacion, tipo, fecha, usuario);
        usuario.agregarEventoOrganizador(evento);
        
        eventos.add(evento);
        return evento.getEventoDTO();
    }

    /**
     * Este método devuelve una Colección de eventosDTO con los eventos que están aún por celebrar
     * @author Adrián Pérez López
     * @author Rafael Galán Ruiz
     * @param sesion
     * @return Colección de eventosDTO
     */
    @Override
    public Collection<EventoDTO> listaEventoPorCelebrar(int sesion) {
        Usuario usuario = comprobarSesion(sesion);
        Calendar fechaActual = Calendar.getInstance();
        if ( usuario == null ) return null;
        
        List<EventoDTO> eventosPendientes = new ArrayList<>();

        for (Evento evento : usuario.getEventosInscrito()) {
            if(fechaActual.before(evento.getFecha()))
                eventosPendientes.add(evento.getEventoDTO());
        }
        for (Evento evento : usuario.getEventosOrganizador()) {
            if(fechaActual.before(evento.getFecha()))
                eventosPendientes.add(evento.getEventoDTO());
        }
        return eventosPendientes;
    }
    
    /**
     * Devuelve una lista de eventos ya celebrados por el usuario
     * @author Adrián Pérez López
     * @author Rafael Galán Ruiz
     * @param sesion
     * @return Colección de eventosDTO
     */
    @Override
    public Collection<EventoDTO> listaEventoCelebrados(int sesion) {
        Usuario usuario = comprobarSesion(sesion);
        Calendar fechaActual = Calendar.getInstance();
        if ( usuario == null ) return null;
        
        List<EventoDTO> eventosCelebrados = new ArrayList<>();

        for (Evento evento : usuario.getEventosInscrito()) {
            if(fechaActual.after(evento.getFecha()))
                eventosCelebrados.add(evento.getEventoDTO());
        }
        for (Evento evento : usuario.getEventosOrganizador()) {
            if(fechaActual.after(evento.getFecha()))
                eventosCelebrados.add(evento.getEventoDTO());
        }
        return eventosCelebrados;
    }
    
    /**
     * Método para inscribir un usuario en el sistema
     * @author Adrián Pérez López
     * @author Rafael Galán Ruiz
     * @param sesion
     * @param evento 
     */
    @Override
    public void inscribirUsuario(int sesion, EventoDTO evento) {
        Usuario usuario = comprobarSesion(sesion);
        Evento e = buscar(evento.getTitulo());
        if(usuario != null){
            usuario.getEventosInscrito().add(e);
            if(e.getAsistentes().size() < e.getnMax()){
                e.getAsistentes().add(usuario);
            }else{
                e.getListaEspera().add(usuario);
            }
        }else{
            System.out.println("ERROR: Debe estar identificado para crear un evento");
        }
    }

    /**
     * Método para cancelar la asistencia a un evento por parte de un usuario
     * @author Adrián Pérez López
     * @author Rafael Galán Ruiz
     * @param sesion
     * @param evento 
     */
    @Override
    public void cancelarAsistencia(int sesion, EventoDTO evento) {
        Usuario usuario = comprobarSesion(sesion);
        Evento even = buscar(evento.getTitulo());
        if(usuario != null){
            if(usuario.getEventosInscrito().contains(even)){
                //elimino el evento de la lista de eventosInscrito del usuario
                usuario.getEventosInscrito().remove(even);
                //Elimino el usuario de la lista de usuarios(Asistentes) del evento
                even.getAsistentes().remove(usuario);
                //Si hay lista de Espera, paso el primer inscrito en la lista de espera a la lista de Asistentes
                if(!even.getListaEspera().isEmpty()){
                    even.getAsistentes().add(even.getListaEspera().get(0));
                    even.getListaEspera().remove(0);
                }
            }
        }else{
            System.out.println("ERROR: Debe estar identificado para crear un evento");
        }
      
      
    }

    /**
     * Este método devuelve el número de eventos que hay en el sistema
     * @author Adrián Pérez López
     * @author Rafael Galán Ruiz
     * @return int 
     */
    @Override
    public int getNEventos() {
        return eventos.size();
    }
    /**
     * Método para buscar un evento
     * @author Adrián Pérez lópez
     * @author Rafael Galán Ruiz
     * @param titulo
     * @return El evento con el titulo igual al string que se ha pasado como argumento
     */
    public Evento buscar(String titulo) {
        for (Evento evento : eventos) {
            if ( titulo.equals(evento.getTitulo())) {
                return evento;
            }
        }
        return null;
    }
    
    
    public Usuario comprobarSesion(int sesion) {
        for (Usuario usuario : usuarios) {
            if ( usuario.getToken() == sesion ) {
                return usuario;
            }
        }
        return null;
    }

    
}
