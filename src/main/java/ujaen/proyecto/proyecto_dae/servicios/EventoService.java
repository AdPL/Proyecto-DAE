
package ujaen.proyecto.proyecto_dae.servicios;

import java.util.Collection;
import java.util.Calendar;
import ujaen.proyecto.proyecto_dae.servicios.dto.EventoDTO;
import ujaen.proyecto.proyecto_dae.beans.Tipo;
import ujaen.proyecto.proyecto_dae.excepciones.EventoNoExiste;
import ujaen.proyecto.proyecto_dae.excepciones.IdentificacionErronea;
import ujaen.proyecto.proyecto_dae.servicios.dto.UsuarioDTO;

/**
 *
 * @author adpl
 * @author Rafa
 */
public interface EventoService {
    Collection<UsuarioDTO> listaAsistentes(EventoDTO evento);
    EventoDTO buscarEvento(String titulo) throws EventoNoExiste;
    Collection<EventoDTO> buscarEvento(Tipo tipo);
    Collection<EventoDTO> buscarEvento(Tipo tipo, String descripcion);
    Collection<EventoDTO> buscarDescripcion(String descripcion);
    EventoDTO crearEvento(String titulo, String descripcion, String localizacion, Tipo tipo, Calendar fecha, int nMax, int sesion);
    void inscribirUsuario(int sesion, EventoDTO evento) throws IdentificacionErronea, EventoNoExiste;
    void cancelarAsistencia(int sesion, EventoDTO evento) throws IdentificacionErronea, EventoNoExiste;
    void cancelarEvento(int sesion, EventoDTO evento) throws IdentificacionErronea, EventoNoExiste;
}
