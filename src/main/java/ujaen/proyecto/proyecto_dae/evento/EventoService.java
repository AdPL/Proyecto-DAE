
package ujaen.proyecto.proyecto_dae.evento;

import java.util.Collection;
import java.util.Date;
import ujaen.proyecto.proyecto_dae.usuario.UsuarioDTO;

/**
 *
 * @author adpl
 * @author Rafa
 */
public interface EventoService {
    Collection<EventoDTO> listaEventos();
    Collection<UsuarioDTO> listaAsistentes(EventoDTO evento);
    EventoDTO buscarEvento(String titulo);
    Collection<EventoDTO> buscarEvento(Tipo tipo);
    Collection<EventoDTO> buscarEvento(Tipo tipo, String descripcion);
    EventoDTO crearEvento(String titulo, String descripcion, String localizacion, Tipo tipo, Date fecha, int nMax, int sesion);
    void inscribirUsuario(int sesion, EventoDTO evento);
    void cancelarAsistencia(int sesion, EventoDTO evento);
    int getNEventos();
}
