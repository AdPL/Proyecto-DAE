
/**
 * Interfaz de la clase usuario
 * @author Adrián Pérez López
 * @author Rafael Galán Ruiz
 */
package ujaen.proyecto.proyecto_dae.servicios;

import java.util.Collection;
import ujaen.proyecto.proyecto_dae.entities.Usuario;
import ujaen.proyecto.proyecto_dae.excepciones.IdentificacionErronea;
import ujaen.proyecto.proyecto_dae.servicios.dto.EventoDTO;

public interface UsuarioService {
    Usuario obtenerUsuario(String nombre);
    String registrarUsuario(String nombre, String pass1, String pass2, String email);
    String identificarUsuario(String nombre, String pass) throws IdentificacionErronea;
    Collection<EventoDTO> listaEventosOrganizador(String nombre);
    Collection<EventoDTO> listaEventosPorCelebrar(String nombre);
    Collection<EventoDTO> listaEventosCelebrados(String nombre);
}
