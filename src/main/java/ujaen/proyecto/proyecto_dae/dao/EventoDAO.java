/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ujaen.proyecto.proyecto_dae.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;
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
}
