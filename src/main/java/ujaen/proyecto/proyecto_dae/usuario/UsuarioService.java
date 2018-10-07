
package ujaen.proyecto.proyecto_dae.usuario;

import java.util.Collection;
import ujaen.proyecto.proyecto_dae.evento.Evento;

/**
 *
 * @author adpl
 * @author Rafa
 */
public interface UsuarioService {
    Usuario registrarUsuario(String nombre, String pass1, String pass2, String email);
    Usuario identificarUsuario(String identificacion, String pass);
    Collection<Evento> listaEventosInscrito(Usuario usuario);
    Collection<Evento> listaEventosOrganizador(Usuario usuario);
    int getNUsuarios();
}