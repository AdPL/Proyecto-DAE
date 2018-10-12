/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ujaen.proyecto.proyecto_dae.evento;

//import java.text.DateFormat;
//import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
//import java.util.Date;

import java.util.List;
import ujaen.proyecto.proyecto_dae.usuario.Usuario;
import ujaen.proyecto.proyecto_dae.usuario.UsuarioDTO;
import ujaen.proyecto.proyecto_dae.usuario.UsuarioService;
//import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author adria
 */
public class GestorEventos implements EventoService, UsuarioService {
    private List<Evento> eventos;
    private List<Usuario> usuarios;
    
    public GestorEventos() {
        eventos = new ArrayList<>();
        usuarios = new ArrayList<>();
    }
    
    @Override
    public Collection<EventoDTO> listaEventos() {
        Collection<EventoDTO> e = new ArrayList<>();
        for (Evento evento : eventos) {
            e.add(evento.getEventoDTO());
        }
        return e;
    }

    @Override
    public Collection<UsuarioDTO> listaAsistentes(EventoDTO evento) {
        Evento e = buscar(evento.getTitulo());
        if ( e == null) return null;
        Collection<UsuarioDTO> usuarios = new ArrayList<>();
        for (Usuario asistente : e.getAsistentes()) {
            usuarios.add(asistente.getUsuarioDTO());
        }
        usuarios.add(e.getOrganizador().getUsuarioDTO());
        return usuarios;
    }

    @Override
    public EventoDTO buscarEvento(String titulo) { //TODO: Método buscarEvento de la clase ujaen.proyecto.proyecto_dae.evento.EventoServiceImpl
        for (Evento evento : eventos) {
            if ( titulo.equals(evento.getTitulo())) {
                return evento.getEventoDTO();
            }
        }
        return null;
    }

    @Override
    public Collection<EventoDTO> buscarEvento(Tipo tipo) {
        Collection<EventoDTO> busq = new ArrayList<>();
        for (Evento evento : eventos) {
            if ( tipo == evento.getTipo() ) {
                busq.add(evento.getEventoDTO());
            }
        }
        return busq;
    }

    @Override
    public Collection<EventoDTO> buscarEvento(Tipo tipo, String descripcion) { //TODO: Método buscarEvento con filtrado, hay mejores clases a contains
        Collection<EventoDTO> busq = new ArrayList<>();
        for (Evento evento : eventos) {
            if ( tipo == evento.getTipo() && evento.getDescripcion().contains(descripcion) ) {
                busq.add(evento.getEventoDTO());
            }
        }
        return busq;
    }

    @Override
    public int getNUsuarios() {
        return usuarios.size();
    }
    
    @Override
    public int registrarUsuario(String nombre, String pass1, String pass2, String email) {
        Usuario usuario = null;
        if (pass1.equals(pass2)) {
            usuario = new Usuario(1, nombre, email, pass1);
            if ( !usuarios.contains(usuario) ) { //ToDo: No es válida la comparación
                usuarios.add(usuario);
            } else {
                System.out.println("ERROR: El usuario ya existe");
            }
        } else {
            System.out.println("ERROR: Las contraseñas no coinciden.");
        }
        return usuario != null ? usuario.getToken() : -1;
    }

    @Override
    public int identificarUsuario(String identificacion, String pass) {
        for (Usuario usuario : usuarios) {
            if ((identificacion.equals(usuario.getNombre()) || identificacion.equals(usuario.getEmail()) ) && pass.equals(usuario.getPassword())) {
                return usuario.getToken();
            }
        }
        return -1;
    }

    @Override
    public Collection<EventoDTO> listaEventosInscrito(int sesion) {
        Usuario usuario = comprobarSesion(sesion);
        
        if ( usuario == null ) return null;
        
        List<EventoDTO> eventosDTO = new ArrayList<>();
        
        for (Evento evento : usuario.getEventosInscrito() ) {
            eventosDTO.add(evento.getEventoDTO());
        }
        return eventosDTO;
    }

    @Override
    public Collection<EventoDTO> listaEventosOrganizador(int sesion) {
        Usuario usuario = comprobarSesion(sesion);
        
        if ( usuario == null ) return null;
        
        List<EventoDTO> eventosDTO = new ArrayList<>();
        
        for (Evento evento : usuario.getEventosOrganizador()) {
            eventosDTO.add(evento.getEventoDTO());
        }
        return eventosDTO;
    }  

    @Override
    public EventoDTO crearEvento(String titulo, String descripcion, String localizacion, Tipo tipo, int dia, int mes, int anio, int nMax, int sesion){
        Usuario usuario = comprobarSesion(sesion);
        Calendar fecha = Calendar.getInstance();
        //Al mes hay que restarle 1 porque los cuenta de 0 a 11 (0 es enero y 11 diciembre)
        fecha.set(anio, mes-1, dia);
        
        if ( usuario == null ) return null;
        
        for (Evento evento : eventos) {
            if ( titulo.equals(evento.getTitulo()) ) return null;
        }
        Evento evento = new Evento(1, nMax, titulo, descripcion, localizacion, tipo, fecha, usuario);
        usuario.agregarEventoOrganizador(evento);
        eventos.add(evento);
        return evento.getEventoDTO();
    }

    @Override
    public void inscribirUsuario(int sesion, EventoDTO evento) {
        Usuario usuario = comprobarSesion(sesion);
        Evento e = buscar(evento.getTitulo());
        if(usuario != null){
            usuario.getEventosInscrito().add(e);
            if(e.getAsistentes().size() < e.getnMax()){
                e.getAsistentes().add(usuario);
            }else{
                e.getListaEspera().add(usuario);
            }
        }else{
            System.out.println("ERROR: Debe estar identificado para crear un evento");
        }
    }

    @Override
    //pasas id de sesion(token), y un evento DTO
    public void cancelarAsistencia(int sesion, EventoDTO evento) {
        Usuario usuario = comprobarSesion(sesion);
        Evento even = buscar(evento.getTitulo());
        if(usuario != null){
            if(usuario.getEventosInscrito().contains(even)){
                //elimino el evento de la lista de eventosInscrito del usuario
                usuario.getEventosInscrito().remove(even);
                //Elimino el usuario de la lista de usuarios(Asistentes) del evento
                even.getAsistentes().remove(usuario);
                //Si hay lista de Espera, paso el primer inscrito en la lista de espera a la lista de Asistentes
                if(!even.getListaEspera().isEmpty()){
                    even.getAsistentes().add(even.getListaEspera().get(0));
                    even.getListaEspera().remove(0);
                }
            }
        }else{
            System.out.println("ERROR: Debe estar identificado para crear un evento");
        }
      
      
    }

    @Override
    public int getNEventos() {
        return eventos.size();
    }
    
    public Evento buscar(String titulo) {
        for (Evento evento : eventos) {
            if ( titulo.equals(evento.getTitulo())) {
                return evento;
            }
        }
        return null;
    }
    
    public Usuario comprobarSesion(int sesion) {
        for (Usuario usuario : usuarios) {
            if ( usuario.getToken() == sesion ) {
                return usuario;
            }
        }
        return null;
    }

    
}
