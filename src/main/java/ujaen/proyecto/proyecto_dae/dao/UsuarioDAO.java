/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ujaen.proyecto.proyecto_dae.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ujaen.proyecto.proyecto_dae.entities.Evento;
import ujaen.proyecto.proyecto_dae.entities.Usuario;

/**
 *
 * @author adpl
 */

@Repository
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class UsuarioDAO {
    @PersistenceContext
    private EntityManager em;
    
    public Usuario buscar(int id) {
        return em.find(Usuario.class, id);
    }
    
    @Transactional(readOnly = false)
    public void insertar(Usuario usuario) {
        em.persist(usuario);
    }
    
    @Transactional(readOnly = false)
    public void actualizar(Usuario usuario) {
        em.merge(usuario);
    }
    
    @Transactional(propagation=Propagation.SUPPORTS)
    public Usuario obtenerUsuarioPorNombre(String nombre) {
        Usuario u = em.createQuery(
                "SELECT u FROM Usuario u WHERE u.nombre = :nombre", Usuario.class)
                .setParameter("nombre", nombre).getSingleResult();
        return u;
    }
    
    @Transactional(propagation=Propagation.SUPPORTS)
    public List<Evento> obtenerEventosInscritoPasados(Usuario usuario) {
        List<Evento> eventos = em.createQuery(
                "SELECT e FROM Evento e WHERE :usuario MEMBER OF e.asistentes AND CURRENT_TIMESTAMP > e.fecha", Evento.class)
        .setParameter("usuario", usuario).getResultList();
        
        return eventos;
    }
    
    @Transactional(propagation=Propagation.SUPPORTS)
    public List<Evento> obtenerEventosInscritoPorCelebrar(Usuario usuario) {
        List<Evento> eventos = em.createQuery(
                "SELECT e FROM Evento e WHERE :usuario MEMBER OF e.asistentes AND CURRENT_TIMESTAMP < e.fecha", Evento.class)
        .setParameter("usuario", usuario).getResultList();
        
        return eventos;
    }
}
