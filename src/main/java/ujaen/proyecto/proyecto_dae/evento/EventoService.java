
package ujaen.proyecto.proyecto_dae.evento;

import java.util.Collection;
import java.util.Date;
import ujaen.proyecto.proyecto_dae.usuario.Usuario;
import ujaen.proyecto.proyecto_dae.evento.EventoDTO;

/**
 *
 * @author adpl
 * @author Rafa
 */
public interface EventoService {
    Collection<EventoDTO> listaEventos();
    Collection<Usuario> listaAsistentes(Evento evento);
    EventoDTO buscarEvento(String titulo);
    Collection<EventoDTO> buscarEvento(Tipo tipo);
    Collection<EventoDTO> buscarEvento(Tipo tipo, String descripcion);
    //void crearEvento(Usuario usuario, String titulo, String descripcion, String localizacion, int nMax, Date fecha, Tipo tipo);
    //void cancelarEvento(Usuario usuario, Evento evento);
    void inscribirUsuario(Usuario usuario, Evento evento);
    void cancelarAsistencia(Usuario usuario);
    int getNEventos();
}
