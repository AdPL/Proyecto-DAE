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
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import ujaen.proyecto.proyecto_dae.beans.Tipo;
import ujaen.proyecto.proyecto_dae.entities.Evento;

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
    
    
    
    //Busqueda por titulo
    public Evento bucarTitulo(String titulo){
        Evento evento;
        evento = em.createQuery("select e from Evento e where e.titulo=:titulo", Evento.class).setParameter("titulo", titulo).getSingleResult();
        return evento;
    }
    
    //Busqueda por tipo
    public Collection<Evento> eventosTipo(Tipo tipo){
        Collection<Evento> eventos =em.createQuery("select e from Evento e where e.tipo=:tipo",Evento.class).setParameter("tipo",tipo).getResultList();
        return eventos;
    }
    
    //Busqueda por descripcion
    //Hay que mejorar la d
    public Collection<Evento> eventosDescripcion(String descripcion){
        Collection<Evento> eventos;
        String des ='%'+descripcion+'%';
        eventos = em.createQuery("select e from Evento e where e.descripcion like :des",Evento.class).setParameter("des",des).getResultList();
        return eventos;
    }
    
}
