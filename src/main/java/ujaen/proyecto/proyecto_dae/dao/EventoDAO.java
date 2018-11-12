/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ujaen.proyecto.proyecto_dae.dao;

import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
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
    
    public Evento obtenerEventoPorTitulo(String titulo) {
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
        evento.agregarAsistente(usuario);
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
        e.quitarAsistente(u);
        em.merge(evento);
        em.merge(usuario);
    }
    
    //TODO: Correcta gestión de transacciones
    public void cancelarEvento(Usuario usuario, Evento evento) {
        Evento e = em.merge(evento);
        Usuario u = em.merge(usuario);
        if ( u.getNombre().equals(evento.getOrganizador().getNombre()) ) {
            eliminar(e);
        }
        em.merge(usuario);
    }
}
