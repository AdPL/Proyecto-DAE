
package ujaen.proyecto.proyecto_dae.servicios;

import java.util.Collection;
import java.util.Calendar;
import ujaen.proyecto.proyecto_dae.servicios.dto.EventoDTO;
import ujaen.proyecto.proyecto_dae.beans.Tipo;

/**
 *
 * @author adpl
 * @author Rafa
 */
public interface UsuarioService {
    int registrarUsuario(String nombre, String pass1, String pass2, String email);
    int identificarUsuario(String nombre, String pass);
    void cerrarSesionUsuario(int sesion);
    Collection<EventoDTO> listaEventosInscrito(int sesion);
    Collection<EventoDTO> listaEventosOrganizador(int sesion);
    EventoDTO crearEvento(String titulo, String descripcion, String localizacion, Tipo tipo, Calendar fecha, int nMax, int sesion);
}