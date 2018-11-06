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
import ujaen.proyecto.proyecto_dae.beans.Usuario;

/**
 *
 * @author adpl
 */

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
}
