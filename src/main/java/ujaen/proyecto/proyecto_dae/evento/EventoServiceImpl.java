
package ujaen.proyecto.proyecto_dae.evento;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import ujaen.proyecto.proyecto_dae.usuario.Usuario;

/**
 *
 * @author adpl
 * @author Rafa
 */
public class EventoServiceImpl implements EventoService {
    private List<EventoDTO> eventos;
    
    public EventoServiceImpl() {
        eventos = new ArrayList<>();
    }
    
    @Override
    public int getNEventos() {
        return eventos.size();
    }
    
    /*@Override
    public void crearEvento(Usuario usuario, String titulo, String descripcion, String localizacion, int nMax, Date fecha, Tipo tipo) { //TODO: Completar método
        boolean existe = false;
        if (usuario != null) {
            EventoDTO nuevoEvento = new EventoDTO(1, nMax, titulo, descripcion, localizacion, fecha, tipo, usuario);
            for (EventoDTO evento : eventos) {
                if ( evento.getTitulo().equals(titulo) ) {
                    existe = true;
                    System.out.println("ERROR: Ya existe un evento con ese título");
                }
            }
            if ( !existe ) {
                eventos.add(nuevoEvento);
                usuario.agregarEventoOrganizador(nuevoEvento);
                inscribirUsuario(usuario, nuevoEvento);
                System.out.println("Evento creado correctamente");
            }
        } else {
            System.out.println("ERROR: Debe estar identificado para crear un evento");
        }
    }
    */
    @Override
    public Collection<EventoDTO> listaEventos() {
        return eventos;
    }

    @Override
    public void inscribirUsuario(Usuario usuario, Evento evento) { //TODO: Método inscribirUsuario de la clase ujaen.proyecto.proyecto_dae.evento.EventoServiceImpl
        usuario.agregarEventoAsistente(evento);
        evento.agregarAsistente(usuario);
        System.out.println(usuario.getNombre() + " inscrito en el evento " + evento.getTitulo());
    }

    @Override
    public void cancelarAsistencia(Usuario usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //TODO: Método cancelarAsistencia de la clase ujaen.proyecto.proyecto_dae.evento.EventoServiceImpl
    }

    @Override
    public Collection<Usuario> listaAsistentes(Evento evento) {
        return evento.getAsistentes(); //TODO: Método listaAsistentes de la clase ujaen.proyecto.proyecto_dae.evento.EventoServiceImpl
    }

    @Override
    public EventoDTO buscarEvento(String titulo) { //TODO: Método buscarEvento de la clase ujaen.proyecto.proyecto_dae.evento.EventoServiceImpl
        for (EventoDTO evento : eventos) {
            if ( titulo.equals(evento.getTitulo())) {
                return evento;
            }
        }
        return null;
    }

    @Override
    public Collection<EventoDTO> buscarEvento(Tipo tipo) {
        Collection<EventoDTO> busq = new ArrayList<>();
        for (EventoDTO evento : eventos) {
            if ( tipo == evento.getTipo() ) {
                busq.add(evento);
            }
        }
        return busq;
    }

    @Override
    public Collection<EventoDTO> buscarEvento(Tipo tipo, String descripcion) { //TODO: Método buscarEvento con filtrado, hay mejores clases a contains
        Collection<EventoDTO> busq = new ArrayList<>();
        for (EventoDTO evento : eventos) {
            if ( tipo == evento.getTipo() && evento.getDescripcion().contains(descripcion) ) {
                busq.add(evento);
            }
        }
        return busq;
    }

    /*@Override
    public void cancelarEvento(Usuario usuario, EventoDTO evento) {
        if( eventos.contains(evento) && usuario.equals(evento.getOrganizador())) {
            for ( Usuario usu : evento.getAsistentes() ) {
                usu.eliminarEventoAsistente(evento); //TODO: Eliminación de asistentes: Esto no está funcionando
            }
            eventos.remove(evento);
            usuario.eliminarEventoOrganizado(evento);
        } else {
            System.out.println("ERROR: No puedes borrar un evento que no es tuyo");
        }
        //TODO: Método cancelarEvento de la clase ujaen.proyecto.proyecto_dae.evento.EventoServiceImpl
    }*/
}
