
/**
 * Interfaz de la clase usuario
 * @author Adrián Pérez López
 * @author Rafael Galán Ruiz
 */
package ujaen.proyecto.proyecto_dae.servicios;

import java.util.Collection;
import ujaen.proyecto.proyecto_dae.excepciones.IdentificacionErronea;
import ujaen.proyecto.proyecto_dae.servicios.dto.EventoDTO;

public interface UsuarioService {
    int registrarUsuario(String nombre, String pass1, String pass2, String email);
    int identificarUsuario(String nombre, String pass) throws IdentificacionErronea;
    Collection<EventoDTO> listaEventosInscrito(int sesion);
    Collection<EventoDTO> listaEventosOrganizador(int sesion);
    Collection<EventoDTO> listaEventosPorCelebrar(int sesion);
    Collection<EventoDTO> listaEventosCelebrados(int sesion);
}
