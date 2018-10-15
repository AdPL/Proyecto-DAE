/**
 * Interfaz de la clase evento
 * @author Adrián Pérez López
 * @author Rafael Galán Ruiz
 */
package ujaen.proyecto.proyecto_dae.evento;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import ujaen.proyecto.proyecto_dae.usuario.Usuario;
import ujaen.proyecto.proyecto_dae.evento.EventoDTO;
import ujaen.proyecto.proyecto_dae.usuario.UsuarioDTO;


public interface EventoService {
    Collection<EventoDTO> listaEventos();
    Collection<UsuarioDTO> listaAsistentes(EventoDTO evento);
    EventoDTO buscarEvento(String titulo);
    Collection<EventoDTO> buscarEvento(Tipo tipo);
    Collection<EventoDTO> buscarEvento(Tipo tipo, String descripcion);
    EventoDTO crearEvento(String titulo, String descripcion, String localizacion, Tipo tipo, Calendar fecha, int nMax, int sesion);
    public Collection<EventoDTO> listaEventoPorCelebrar(int sesion);
    public Collection<EventoDTO> listaEventoCelebrados(int sesion);
    void inscribirUsuario(int sesion, EventoDTO evento);
    void cancelarAsistencia(int sesion, EventoDTO evento);
    int getNEventos();
}
