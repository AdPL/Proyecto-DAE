
package ujaen.proyecto.proyecto_dae;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ujaen.proyecto.proyecto_dae.beans.GestorEventos;

/**
 *
 * @author adpl
 * @author Rafa
 */
@Configuration
public class AppConfig {
    @Bean
    public GestorEventos gestorEventos() {
        return new GestorEventos();
    }
}
