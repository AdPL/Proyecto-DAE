/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ujaen.proyecto.proyecto_dae;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ujaen.proyecto.proyecto_dae.beans.GestorEventos;
import ujaen.proyecto.proyecto_dae.entities.Usuario;
import ujaen.proyecto.proyecto_dae.excepciones.IdentificacionErronea;

/**
 *
 * @author adria
 */
@Component
public class ServicioDatosSeguridadEventos implements UserDetailsService {

    @Autowired
    GestorEventos gestorEventos;
    
    @Override
    public UserDetails loadUserByUsername(String nombre) throws UsernameNotFoundException {
        Usuario usuario = gestorEventos.obtenerUsuario(nombre);
        if ( usuario == null ) {
            throw new IdentificacionErronea("ERROR LOGIN");
        }
        
        //TODO: Fix this, is working without BCryptEncoder, using plain text
        return User.withUsername(nombre).password("{noop}oretania").roles("USUARIO").build();
    }
    
}
