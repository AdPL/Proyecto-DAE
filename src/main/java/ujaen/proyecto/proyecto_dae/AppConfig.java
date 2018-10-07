
package ujaen.proyecto.proyecto_dae;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ujaen.proyecto.proyecto_dae.usuario.UsuarioService;
import ujaen.proyecto.proyecto_dae.usuario.UsuarioServiceImpl;
import ujaen.proyecto.proyecto_dae.evento.EventoService;
import ujaen.proyecto.proyecto_dae.evento.EventoServiceImpl;

/**
 *
 * @author adpl
 * @author Rafa
 */
@Configuration
public class AppConfig {
    @Bean
    public UsuarioService usuarioService() {
        System.out.println("User Bean loaded");
        return new UsuarioServiceImpl();
    }
    
    @Bean
    public EventoService eventoService() {
        System.out.println("Event Bean loaded");
        return new EventoServiceImpl();
    }
}
