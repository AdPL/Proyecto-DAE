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
import ujaen.proyecto.proyecto_dae.servicios.dto.UsuarioDTO;

/**
 *
 * @author adpl
 */
@Component
public class ServicioDatosUsuarioEventos implements UserDetailsService {

    @Autowired
    GestorEventos gestorEventos;
    
    @Override
    public UserDetails loadUserByUsername(String nombre) throws UsernameNotFoundException {
        Usuario usuario = gestorEventos.obtenerUsuario(nombre);
        if(usuario == null){
            throw new IdentificacionErronea("Error");
        }
        return User.withUsername(nombre).password("oretania").build();
    }
    
}
