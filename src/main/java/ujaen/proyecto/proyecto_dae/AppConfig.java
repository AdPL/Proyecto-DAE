/**
 * Clase de configuración de SpringBoot
 * @author Adrián Pérez López
 * @author Rafael Galán Ruiz
 */
package ujaen.proyecto.proyecto_dae;

import javax.sql.DataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ujaen.proyecto.proyecto_dae.beans.GestorEventos;


@Configuration
public class AppConfig {
    @Bean
    public GestorEventos gestorEventos() {
        return new GestorEventos();
    }
    
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }
}
