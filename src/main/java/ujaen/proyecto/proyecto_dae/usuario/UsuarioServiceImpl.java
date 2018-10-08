
package ujaen.proyecto.proyecto_dae.usuario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import ujaen.proyecto.proyecto_dae.evento.Evento;

/**
 *
 * @author adpl
 * @author Rafa
 */
public class UsuarioServiceImpl implements UsuarioService {
    private List<Usuario> usuarios;
    
    public UsuarioServiceImpl() {
        usuarios = new ArrayList<>();
    }
    
    @Override
    public int getNUsuarios() {
        return usuarios.size();
    }
    
    @Override
    public UsuarioDTO registrarUsuario(String nombre, String pass1, String pass2, String email) {
        Usuario usuario = null;
        if (pass1.equals(pass2)) {
            usuario = new Usuario(1, nombre, email, pass1);
            if ( !usuarios.contains(usuario) ) { // No es válida la comparación
                usuarios.add(usuario);
            } else {
                System.out.println("ERROR: El usuario ya existe");
            }
        } else {
            System.out.println("ERROR: Las contraseñas no coinciden.");
        }
        return usuario.getUsuarioDTO();
    }

    @Override
    public UsuarioDTO identificarUsuario(String identificacion, String pass) {
        for (Usuario usuario : usuarios) {
            if ((identificacion.equals(usuario.getNombre()) || identificacion.equals(usuario.getEmail()) ) && pass.equals(usuario.getPassword())) {
                return usuario.getUsuarioDTO();
            }
        }
        return null;
    }

    @Override
    public Collection<Evento> listaEventosInscrito(Usuario usuario) {
        return usuario.getEventosInscrito();
    }

    @Override
    public Collection<Evento> listaEventosOrganizador(Usuario usuario) {
        return usuario.getEventosOrganizador();
    }
}
