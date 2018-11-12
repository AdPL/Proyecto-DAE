/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ujaen.proyecto.proyecto_dae.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import ujaen.proyecto.proyecto_dae.beans.Tipo;
import ujaen.proyecto.proyecto_dae.entities.Evento;
import ujaen.proyecto.proyecto_dae.entities.Usuario;

/**
 *
 * @author adpl
 */
@Repository
@Transactional
public class EventoDAO {
    @PersistenceContext
    private EntityManager em;
        
    public Evento buscar(int id) {
        return em.find(Evento.class, id);
    }
    
    public void insertar(Evento evento) {
        em.persist(evento);
    }
    
    public void actualizar(Evento evento) {
        em.merge(evento);
    }
    
    public void eliminar(Evento evento) {
        em.remove(em.merge(evento));
    }
    
    @Cacheable(value="cacheEventos")
    public Evento obtenerEventoPorTitulo(String titulo) {
        /*try {
            Thread.sleep(5000); // Simulación de petición con alto coste
        } catch (InterruptedException ex) {
            Logger.getLogger(EventoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        Evento e = em.createQuery(
                "SELECT e FROM Evento e WHERE e.titulo = :titulo", Evento.class)
                .setParameter("titulo", titulo).getSingleResult();
        return e;
    }
    
    public Collection<Evento> obtenerEventoPorTipo(Tipo tipo) {
        Collection<Evento> e = em.createQuery(
                "SELECT e FROM Evento e WHERE e.tipo = :tipo", Evento.class)
                .setParameter("tipo", tipo).getResultList();
        return e;
    }
    
    public Collection<Evento> obtenerEventoPorTipoDescripcion(Tipo tipo, String descripcion) {
        Collection<Evento> e = em.createQuery(
                "SELECT e FROM Evento e WHERE e.tipo = :tipo AND e.descripcion LIKE CONCAT('%', :descripcion, '%')", Evento.class)
                .setParameter("tipo", tipo)
                .setParameter("descripcion", descripcion).getResultList();
        return e;
    }
    
    public void inscribirUsuario(Usuario usuario, int id) {
        Evento evento = em.find(Evento.class, id);
        Usuario u = em.merge(usuario);
        evento.agregarAsistente(u);
    }
    
    public Evento comprobarAsistencia(Usuario usuario) {
        Evento evento = em.createQuery(
                "SELECT e FROM Evento e WHERE :usuario MEMBER OF e.asistentes", Evento.class)
                .setParameter("usuario", usuario).getSingleResult();
        return evento;
    }
    
    //TODO: Correcta gestión de transacciones
    public void cancelarAsistencia(Usuario usuario, Evento evento) {
        Evento e = em.merge(evento);
        Usuario u = em.merge(usuario);
        
        Set<Calendar> fechas;
        fechas = e.getListaEspera().keySet();
        for ( Calendar fecha : fechas ) {
            System.out.println("He obtenido la fecha: " + fecha);
            e.quitarAsistente(u, fecha);
        }
    }
    
    //TODO: Correcta gestión de transacciones
    public void cancelarEvento(Usuario usuario, Evento evento) {
        Evento e = em.merge(evento);
        Usuario u = em.merge(usuario);
        if ( u.getNombre().equals(evento.getOrganizador().getNombre()) ) {
            eliminar(e);
        }
    }
}
