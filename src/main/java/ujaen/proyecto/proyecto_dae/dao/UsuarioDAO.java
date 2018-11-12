/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ujaen.proyecto.proyecto_dae.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;
import ujaen.proyecto.proyecto_dae.entities.Evento;
import ujaen.proyecto.proyecto_dae.entities.Usuario;

/**
 *
 * @author adpl
 */

//TODO: Revisar consulta por setParameter y gestionarla de modo literals o string según sea el parámetro
//TODO: Problema al devolver null, revisar que debe hacerse en esos casos
//TODO: Caché

@Repository
@Transactional
public class UsuarioDAO {
    @PersistenceContext
    private EntityManager em;
        
    public Usuario buscar(int id) {
        return em.find(Usuario.class, id);
    }
    
    public void insertar(Usuario usuario) {
        em.persist(usuario);
    }
    
    public void actualizar(Usuario usuario) {
        em.merge(usuario);
    }
    
    public Usuario obtenerUsuarioPorNombre(String nombre) {
        Usuario u = em.createQuery(
                "SELECT u FROM Usuario u WHERE u.nombre = :nombre", Usuario.class)
                .setParameter("nombre", nombre).getSingleResult();
        return u;
    }
    
    public Usuario obtenerUsuarioPorToken(int token) {
        Usuario u = em.createQuery(
                "SELECT u FROM Usuario u WHERE u.token = :token", Usuario.class)
                .setParameter("token", token).getSingleResult();
        return u;
    }
    
    public Usuario auntentificarUsuario(String nombre, String password) {
        Usuario u = em.createQuery(
                "SELECT u FROM Usuario u WHERE u.nombre = :nombre AND u.password = :password", Usuario.class)
                .setParameter("nombre", nombre)
                .setParameter("password", password)
                .getSingleResult();

        return u;
    }
    
    public List<Evento> obtenerEventosInscrito(Usuario usuario) {
        List<Evento> eventos = em.createQuery(
                "SELECT e FROM Evento e WHERE :usuario MEMBER OF e.asistentes", Evento.class)
        .setParameter("usuario", usuario).getResultList();
        
        return eventos;
    }
    
    public List<Evento> obtenerEventosOrganizador(Usuario usuario) {
        List<Evento> eventos = em.createQuery(
                "SELECT e FROM Evento e WHERE e.organizador = :usuario", Evento.class)
        .setParameter("usuario", usuario).getResultList();
        
        return eventos;
    }
    
    public List<Evento> obtenerEventosInscritoPasados(Usuario usuario) {
        List<Evento> eventos = em.createQuery(
                "SELECT e FROM Evento e WHERE :usuario MEMBER OF e.asistentes AND CURRENT_TIMESTAMP > e.fecha", Evento.class)
        .setParameter("usuario", usuario).getResultList();
        
        return eventos;
    }
    
    public List<Evento> obtenerEventosInscritoPorCelebrar(Usuario usuario) {
        List<Evento> eventos = em.createQuery(
                "SELECT e FROM Evento e WHERE :usuario MEMBER OF e.asistentes AND CURRENT_TIMESTAMP < e.fecha", Evento.class)
        .setParameter("usuario", usuario).getResultList();
        
        return eventos;
    }
}
