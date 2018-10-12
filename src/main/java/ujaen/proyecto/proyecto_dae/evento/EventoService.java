
package ujaen.proyecto.proyecto_dae.evento;

import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import ujaen.proyecto.proyecto_dae.usuario.Usuario;
import ujaen.proyecto.proyecto_dae.evento.EventoDTO;
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
    EventoDTO crearEvento(String titulo, String descripcion, String localizacion, Tipo tipo, int dia, int mes, int anio, int nMax, int sesion)throws ParseException;
    void inscribirUsuario(int sesion, EventoDTO evento);
    void cancelarAsistencia(int sesion, EventoDTO evento);
    int getNEventos();
}
