
package ujaen.proyecto.proyecto_dae.usuario;

import java.util.Collection;
import ujaen.proyecto.proyecto_dae.evento.Evento;
import ujaen.proyecto.proyecto_dae.evento.EventoDTO;

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
    int getNUsuarios();
}