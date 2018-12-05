
package ujaen.proyecto.proyecto_dae.servicios;

import java.util.Collection;
import java.util.Calendar;
import java.util.List;
import ujaen.proyecto.proyecto_dae.servicios.dto.EventoDTO;
import ujaen.proyecto.proyecto_dae.beans.Tipo;
import ujaen.proyecto.proyecto_dae.excepciones.EventoNoExiste;
import ujaen.proyecto.proyecto_dae.excepciones.IdentificacionErronea;

/**
 *
 * @author adpl
 * @author Rafa
 */
public interface EventoService {
    EventoDTO obtenerEvento(String titulo) throws EventoNoExiste;
    List<EventoDTO> buscarEvento(Tipo tipo);
    Collection<EventoDTO> buscarEvento(Tipo tipo, String descripcion);
    void crearEvento(String titulo, String descripcion, String localizacion, Tipo tipo, Calendar fecha, int nMax, String nombre);
    void inscribirUsuario(String titulo, String nombre) throws IdentificacionErronea, EventoNoExiste;
    void cancelarAsistencia(String nombre, EventoDTO evento) throws IdentificacionErronea, EventoNoExiste;
    void cancelarEvento(String titulo) throws IdentificacionErronea, EventoNoExiste;
}
