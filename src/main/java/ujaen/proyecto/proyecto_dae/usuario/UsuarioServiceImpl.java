
package ujaen.proyecto.proyecto_dae.usuario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import ujaen.proyecto.proyecto_dae.evento.Evento;
import ujaen.proyecto.proyecto_dae.evento.EventoDTO;

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
            if ( !usuarios.contains(usuario) ) { //ToDo: No es válida la comparación
                usuarios.add(usuario);
            } else {
                System.out.println("ERROR: El usuario ya existe");
            }
        } else {
            System.out.println("ERROR: Las contraseñas no coinciden.");
        }
        return usuario != null ? usuario.getUsuarioDTO() : null;
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
    public Collection<EventoDTO> listaEventosInscrito(UsuarioDTO usuario) {
        Usuario u;
        u = comprobarSesion(usuario);
        if ( u == null ) return null;
        
        List<EventoDTO> eventosDTO = new ArrayList<>();
        
        for (Evento evento : u.getEventosInscrito() ) {
            eventosDTO.add(evento.getEventoDTO());
        }
        return eventosDTO;
    }

    @Override
    public Collection<EventoDTO> listaEventosOrganizador(UsuarioDTO usuario) {
        Usuario u;
        u = comprobarSesion(usuario);
        if ( u == null ) return null;
        
        List<EventoDTO> eventosDTO = new ArrayList<>();
        
        for (Evento evento : u.getEventosInscrito() ) {
            eventosDTO.add(evento.getEventoDTO());
        }
        return eventosDTO;
    }
    
    public Usuario comprobarSesion(UsuarioDTO usuario) {
        for (Usuario u : usuarios) {
            if ( usuario.getNombre().equals(u.getNombre()) ) {
                return u;
            }
        }
        return null;
    }
}
