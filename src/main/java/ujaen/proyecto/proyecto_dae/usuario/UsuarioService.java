
package ujaen.proyecto.proyecto_dae.usuario;

import java.util.Collection;
import ujaen.proyecto.proyecto_dae.evento.Evento;

/**
 *
 * @author adpl
 * @author Rafa
 */
public interface UsuarioService {
    UsuarioDTO registrarUsuario(String nombre, String pass1, String pass2, String email);
    UsuarioDTO identificarUsuario(String identificacion, String pass);
    Collection<Evento> listaEventosInscrito(Usuario usuario);
    Collection<Evento> listaEventosOrganizador(Usuario usuario);
    int getNUsuarios();
}