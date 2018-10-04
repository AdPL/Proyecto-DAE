
package ujaen.proyecto.proyecto_dae;

import ujaen.proyecto.proyecto_dae.usuario.UsuarioService;
import ujaen.proyecto.proyecto_dae.usuario.UsuarioServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}
