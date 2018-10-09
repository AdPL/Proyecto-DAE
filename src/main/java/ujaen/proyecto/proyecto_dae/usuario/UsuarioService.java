
package ujaen.proyecto.proyecto_dae.usuario;

import java.util.Collection;
import java.util.Date;
import ujaen.proyecto.proyecto_dae.evento.Evento;
import ujaen.proyecto.proyecto_dae.evento.EventoDTO;
import ujaen.proyecto.proyecto_dae.evento.Tipo;

/**
 *
 * @author adpl
 * @author Rafa
 */
public interface UsuarioService {
    UsuarioDTO registrarUsuario(String nombre, String pass1, String pass2, String email);
    UsuarioDTO identificarUsuario(String identificacion, String pass);
    Collection<EventoDTO> listaEventosInscrito(UsuarioDTO usuario);
    Collection<EventoDTO> listaEventosOrganizador(UsuarioDTO usuario);
    EventoDTO crearEvento(String titulo, String descripcion, String localizacion, Tipo tipo, Date fecha, int nMax, UsuarioDTO usuario);
    int getNUsuarios();
}