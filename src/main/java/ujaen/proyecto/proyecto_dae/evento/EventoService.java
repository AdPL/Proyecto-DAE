
package ujaen.proyecto.proyecto_dae.evento;

import java.util.Collection;
import java.util.Date;
import ujaen.proyecto.proyecto_dae.usuario.Usuario;

/**
 *
 * @author adpl
 * @author Rafa
 */
public interface EventoService {
    Collection<Evento> listaEventos();
    Collection<Usuario> listaAsistentes(Evento evento);
    Evento buscarEvento(String titulo);
    Collection<Evento> buscarEvento(Tipo tipo);
    Collection<Evento> buscarEvento(Tipo tipo, String descripcion);
    void crearEvento(Usuario usuario, String titulo, String descripcion, String localizacion, int nMax, Date fecha);
    void cancelarEvento(Usuario usuario, Evento evento);
    void inscribirUsuario(Usuario usuario, Evento evento);
    void cancelarAsistencia(Usuario usuario);
    int getNEventos();
}
