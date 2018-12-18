/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ujaen.proyecto.proyecto_dae.controllers;

import java.util.Calendar;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ujaen.proyecto.proyecto_dae.beans.GestorEventos;
import ujaen.proyecto.proyecto_dae.beans.Tipo;
import ujaen.proyecto.proyecto_dae.entities.Evento;
import ujaen.proyecto.proyecto_dae.entities.Usuario;
import ujaen.proyecto.proyecto_dae.servicios.dto.EventoDTO;
import ujaen.proyecto.proyecto_dae.servicios.dto.UsuarioDTO;
import ujaen.proyecto.proyecto_dae.excepciones.*;

/**
 *
 * @author adpl
 */

@RestController
@RequestMapping("/app")
public class RecursoApp {
    @Autowired
    GestorEventos app;
    
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({UsuarioNoExiste.class, EventoNoExiste.class})
    public void handlerNoExiste() {}
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({UsuarioIncorrecto.class})
    public void handlerUsuarioIncorrecto() {}
    
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({UsuarioExistente.class, EventoExistente.class})
    public void handlerExistente() {}
    
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({UsuarioErrorAutorizacion.class})
    public void handlerErrorAutorizacion() {}
    
    /**
     * Consulta de los datos de un usuario
     * @param nombre Nombre del usuario en cuestión
     * @return 
     */
    @CrossOrigin
    @RequestMapping( value = "/usuarios/{nombre}", method = GET, produces = "application/json" )
    @ResponseStatus(HttpStatus.OK)
    public UsuarioDTO obtenerUsuario(@PathVariable String nombre) {
        Usuario usuario = app.obtenerUsuario(nombre);
        if ( usuario == null) { throw new UsuarioNoExiste("Usuario no encontrado"); }
        
        return usuario.getUsuarioDTO();
    }
    
    /**
     * Registro de usuarios en la aplicación
     * @param usuario Usuario a registrar
     */
    @CrossOrigin
    @RequestMapping( value = "/usuarios", method = POST, consumes = "application/json" )
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDTO registrarUsuario( @RequestBody Usuario usuario ) {
        if ( usuario == null ) { throw new UsuarioIncorrecto("Usuario incorrecto"); }
        if ( app.obtenerUsuario(usuario.getNombre()) != null ) { throw new UsuarioExistente("El usuario ya existe"); }
        
        String nombre = app.registrarUsuario(usuario.getNombre(), usuario.getPassword(), usuario.getPassword(), usuario.getEmail());
        Usuario u = app.obtenerUsuario(nombre);
        return u.getUsuarioDTO();
    }
    
    @CrossOrigin
    @RequestMapping( value = "/usuarios/login", method = POST, consumes = "application/json" )
    @ResponseStatus(HttpStatus.OK)
    public UsuarioDTO identificarUsuario( @RequestBody Usuario usuario ) {
        String nombre = app.identificarUsuario(usuario.getNombre(), usuario.getPassword());
        if ( nombre == null ) { throw new UsuarioErrorAutorizacion("Usuario o contraseña incorrectos"); }
        
        Usuario u = app.obtenerUsuario(nombre);
        return u.getUsuarioDTO();
    }
    
    /**
     * Eventos que ha creado un usuario determinado
     * @param nombre Nombre del usuario
     * @param celebrado Filtrado de eventos
     * @return Colección de eventos
     */
    @CrossOrigin
    @RequestMapping( value = "/usuarios/{nombre}/organizador", method = GET, produces = "application/json" )
    @ResponseStatus(HttpStatus.OK)
    public Collection<EventoDTO> obtenerEventosOrganizador( @PathVariable String nombre, @RequestParam(defaultValue = "false") boolean celebrado ) {
        Usuario usuario = app.obtenerUsuario(nombre);
        if ( usuario == null ) { throw new UsuarioNoExiste("El usuario no existe"); }
        
        Collection<EventoDTO> eventos = app.listaEventosOrganizador(nombre);
        if ( eventos.isEmpty() ) { throw new EventoNoExiste("El usuario no tiene eventos como organizador"); }
        
        return eventos;
    }
    
    /**
     * Eventos a los que asiste un usuario determinado
     * @param nombre Nombre del usuario
     * @param celebrado Filtrado de eventos
     * @return Colección de eventos
     */
    @CrossOrigin
    @RequestMapping( value = "/usuarios/{nombre}/asistente", method = GET, produces = "application/json" )
    @ResponseStatus(HttpStatus.OK)
    public Collection<EventoDTO> obtenerEventosAsistente( @PathVariable String nombre, @RequestParam(defaultValue = "false") boolean celebrado ) {
        Usuario usuario = app.obtenerUsuario(nombre);
        if ( usuario == null ) { throw new UsuarioNoExiste("El usuario no existe"); }
        
        Collection<EventoDTO> eventos = app.listaEventosPorCelebrar(nombre);
        if ( eventos.isEmpty() ) { throw new EventoNoExiste("El usuario no tiene eventos como organizador"); }

        return eventos;
    }
    
    /**
     * Obtener los datos de un evento
     * @param titulo Título del evento
     * @return Evento si existe, null en otro caso
     */
    @CrossOrigin
    @RequestMapping( value = "/eventos/{titulo}", method = GET, produces = "application/json" )
    @ResponseStatus(HttpStatus.OK)
    public EventoDTO obtenerEvento(@PathVariable String titulo) {
        EventoDTO evento = app.obtenerEvento(titulo);
        if ( evento == null ) { throw new EventoNoExiste("El evento no existe"); }
        
        return evento;
    }
    
    /**
     * Creación de un evento
     * @param evento Evento a crear
     */
    @CrossOrigin
    @RequestMapping( value = "/eventos", method = GET, produces = "application/json" ) //TODO: Falta paginado
    @ResponseStatus(HttpStatus.OK)
    public Collection<EventoDTO> listaEventos( ) {
        return app.listaEventosPorCelebrar("adpl");
    }
    
    /**
     * Creación de un evento
     * @param evento Evento a crear
     */
    @CrossOrigin
    @RequestMapping( value = "/eventos", method = POST, consumes = "application/json" ) //TODO: Solventar fechas
    @ResponseStatus(HttpStatus.CREATED)
    public EventoDTO crearEvento( @RequestBody Evento evento  ) {
        EventoDTO eventoDTO = app.obtenerEvento(evento.getTitulo());
        if ( eventoDTO != null ) { throw new EventoExistente("El evento ya existe"); }
        
        app.crearEvento(evento.getTitulo(), evento.getDescripcion(), evento.getLocalizacion(), Tipo.FESTIVAL, Calendar.getInstance(), evento.getnMax(), "adpl");
        eventoDTO = app.obtenerEvento(evento.getTitulo());
        return eventoDTO;
    }
    
    /**
     * Inscribir a un usuario en un evento
     * @param titulo Título del evento
     * @param nombre Nombre del usuario
     */
    @CrossOrigin
    @RequestMapping( value = "/eventos/{titulo}/asistentes/{nombre}", method = POST )
    @ResponseStatus(HttpStatus.OK)
    public void inscribirUsuario( @PathVariable String titulo, @PathVariable String nombre ) {
        titulo = titulo.replace("%20", " ");
        EventoDTO evento = app.obtenerEvento(titulo);
        Usuario usuario = app.obtenerUsuario(nombre);
        if ( evento == null ) { throw new EventoNoExiste("El evento al que desea inscribirse no existe"); }
        if ( usuario == null ) { throw new UsuarioNoExiste("Usuario no encontrado"); }
        
        app.inscribirUsuario(titulo, nombre);
    }
    
    /**
     * Borrado de un evento
     * @param titulo Título del evento
     */
    @CrossOrigin
    @RequestMapping( value = "/eventos/{titulo}", method = DELETE )
    public void cancelarEvento( @PathVariable String titulo ) {
        titulo = titulo.replace("%20", " ");
        app.cancelarEvento(titulo);
    }
}
