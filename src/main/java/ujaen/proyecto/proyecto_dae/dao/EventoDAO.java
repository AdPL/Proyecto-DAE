/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ujaen.proyecto.proyecto_dae.dao;

import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ujaen.proyecto.proyecto_dae.beans.Tipo;
import ujaen.proyecto.proyecto_dae.entities.Evento;
import ujaen.proyecto.proyecto_dae.entities.Usuario;

/**
 *
 * @author adpl
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class EventoDAO {
    @PersistenceContext
    private EntityManager em;
        
    public Evento buscar(int id) {
        return em.find(Evento.class, id);
    }
    
    @Transactional(readOnly = false)
    public void insertar(Evento evento) {
        em.persist(evento);
    }
    
    @Transactional(readOnly = false)
    public void actualizar(Evento evento) {
        em.merge(evento);
    }
    
    @Transactional(readOnly = false)
    public void eliminar(Evento evento) {
        em.remove(em.merge(evento));
    }
    
    @Cacheable(value="cacheEventos")
    @Transactional(propagation=Propagation.SUPPORTS)
    public Evento obtenerEventoPorTitulo(String titulo) {
        try {
            Thread.sleep(5000); // Simulación de petición con alto coste
        } catch (InterruptedException ex) {
            Logger.getLogger(EventoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        Evento e = em.createQuery(
                "SELECT e FROM Evento e WHERE e.titulo = :titulo", Evento.class)
                .setParameter("titulo", titulo).getSingleResult();
        return e;
    }
    
    @Transactional(propagation=Propagation.SUPPORTS)
    public Collection<Evento> obtenerEventoPorTipo(Tipo tipo) {
        Collection<Evento> e = em.createQuery(
                "SELECT e FROM Evento e WHERE e.tipo = :tipo", Evento.class)
                .setParameter("tipo", tipo).getResultList();
        return e;
    }
    
    @Transactional(propagation=Propagation.SUPPORTS)
    public Collection<Evento> obtenerEventoPorTipoDescripcion(Tipo tipo, String descripcion) {
        Collection<Evento> e = em.createQuery(
                "SELECT e FROM Evento e WHERE e.tipo = :tipo AND e.descripcion LIKE CONCAT('%', :descripcion, '%')", Evento.class)
                .setParameter("tipo", tipo)
                .setParameter("descripcion", descripcion).getResultList();
        return e;
    }
    
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public void inscribirUsuario(Usuario usuario, int id) {
        Evento evento = em.find(Evento.class, id);
        Usuario u = em.merge(usuario);
        evento.agregarAsistente(u);
    }
    
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public Usuario cancelarAsistencia(Usuario usuario, Evento evento) {
        Evento e = em.merge(evento);
        Usuario u = em.merge(usuario);
        
        Calendar primeraFecha = GregorianCalendar.getInstance();
        primeraFecha.set(2050, 1, 1);
        
        e.getListaEspera().forEach(((Calendar k, Usuario v) -> {
            if ( primeraFecha.getTime().after(k.getTime()) ) {
                primeraFecha.setTime(k.getTime());
            }
        }));
        
        Usuario nuevo = e.getListaEspera().get(primeraFecha);
        
        e.quitarAsistente(u, primeraFecha);

        return nuevo;
    }
    
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public void cancelarEvento(Usuario usuario, Evento evento) {
        Evento e = em.merge(evento);
        Usuario u = em.merge(usuario);
        if ( u.getNombre().equals(evento.getOrganizador().getNombre()) ) {
            eliminar(e);
        }
    }
}
