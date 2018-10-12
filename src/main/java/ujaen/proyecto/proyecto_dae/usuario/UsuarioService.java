
package ujaen.proyecto.proyecto_dae.usuario;

import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import ujaen.proyecto.proyecto_dae.evento.Evento;
import ujaen.proyecto.proyecto_dae.evento.EventoDTO;
import ujaen.proyecto.proyecto_dae.evento.Tipo;

/**
 *
 * @author adpl
 * @author Rafa
 */
public interface UsuarioService {
    int registrarUsuario(String nombre, String pass1, String pass2, String email);
    int identificarUsuario(String identificacion, String pass);
    Collection<EventoDTO> listaEventosInscrito(int sesion);
    Collection<EventoDTO> listaEventosOrganizador(int sesion);
    EventoDTO crearEvento(String titulo, String descripcion, String localizacion, Tipo tipo, int fecha, int mes, int anio, int nMax, int sesion)throws ParseException;
    int getNUsuarios();
}