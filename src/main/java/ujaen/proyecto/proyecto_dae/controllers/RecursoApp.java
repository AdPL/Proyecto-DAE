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
import org.springframework.web.bind.annotation.CrossOrigin;
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
    
    /**
     * Consulta de los datos de un usuario
     * @param nombre Nombre del usuario en cuestión
     * @return 
     */
    @CrossOrigin
    @RequestMapping( value = "/usuarios/{nombre}", method = GET, produces = "application/json" )
    public ResponseEntity<UsuarioDTO> obtenerUsuario(@PathVariable String nombre) {
        Usuario usuario = app.obtenerUsuario(nombre);
        if ( usuario == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(usuario.getUsuarioDTO(), HttpStatus.OK);
    }
    
    /**
     * Registro de usuarios en la aplicación
     * @param usuario Usuario a registrar
     */
    @CrossOrigin
    @RequestMapping( value = "/usuarios", method = POST, consumes = "application/json" )
    public ResponseEntity<UsuarioDTO> registrarUsuario( @RequestBody Usuario usuario ) {
        if ( usuario == null ) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        if ( app.obtenerUsuario(usuario.getNombre()) != null ) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        
        String nombre = app.registrarUsuario(usuario.getNombre(), usuario.getPassword(), usuario.getPassword(), usuario.getEmail());
        Usuario u = app.obtenerUsuario(nombre);
        return new ResponseEntity<>(u.getUsuarioDTO(), HttpStatus.OK);
    }
    
    /**
     * Eventos que ha creado un usuario determinado
     * @param nombre Nombre del usuario
     * @param celebrado Filtrado de eventos
     * @return Colección de eventos
     */
    @RequestMapping( value = "/usuarios/{nombre}/organizador", method = GET, produces = "application/json" )
    public Collection<EventoDTO> obtenerEventosOrganizador( @PathVariable String nombre, @RequestParam boolean celebrado ) {
        return app.listaEventosOrganizador(nombre);
    }
    
    /**
     * Eventos a los que asiste un usuario determinado
     * @param nombre Nombre del usuario
     * @param celebrado Filtrado de eventos
     * @return Colección de eventos
     */
    @RequestMapping( value = "/usuarios/{nombre}/asistente", method = GET, produces = "application/json" )
    public Collection<EventoDTO> obtenerEventosAsistente( @PathVariable String nombre, @RequestParam boolean celebrado ) {
        //TODO: Combinar todo en una misma función dentro de app.
        return app.listaEventosPorCelebrar(nombre);
    }
    
    /**
     * Obtener los datos de un evento
     * @param titulo Título del evento
     * @return Evento si existe, null en otro caso
     */
    @RequestMapping( value = "/eventos/{titulo}", method = GET, produces = "application/json" )
    public EventoDTO obtenerEvento(@PathVariable String titulo) {
        return app.obtenerEvento(titulo);
    }
    
    /**
     * Creación de un evento
     * @param evento Evento a crear
     */
    @RequestMapping( value = "/eventos", method = POST, consumes = "application/json" ) //TODO: Solventar fechas
    public void crearEvento( @RequestBody Evento evento  ) {
        app.crearEvento(evento.getTitulo(), evento.getDescripcion(), evento.getLocalizacion(), Tipo.FESTIVAL, Calendar.getInstance(), evento.getnMax(), evento.getOrganizador().getNombre());
    }
    
    /**
     * Inscribir a un usuario en un evento
     * @param titulo Título del evento
     * @param nombre Nombre del usuario
     */
    //TODO: Tengo que pasarle el usuario en petición POST, no vale por pathvariable, enviaré el POST a asistentes
    @RequestMapping( value = "/eventos/{titulo}/asistentes/{nombre}", method = POST, consumes = "application/json" )
    public void inscribirUsuario( @PathVariable String titulo, @PathVariable String nombre ) {
        app.inscribirUsuario(titulo, nombre);
    }
    
    /**
     * Borrado de un evento
     * @param titulo Título del evento
     */
    @RequestMapping( value = "/eventos/{titulo}", method = DELETE, consumes = "application/json" )
    public void cancelarEvento( @PathVariable String titulo ) {
        app.cancelarEvento(titulo);
    }
}
