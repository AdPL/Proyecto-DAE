/**
 * Clase de aplicación de SpringBoot
 * @author Adrián Pérez López
 * @author Rafael Galán Ruiz
 */
package ujaen.proyecto.proyecto_dae;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;


@SpringBootApplication(scanBasePackages = "ujaen.proyecto.proyecto_dae")
@EntityScan(basePackages = "ujaen.proyecto.proyecto_dae.entities")
public class App {
    public static void main(String[] args) throws Exception {
        SpringApplication servidor = new SpringApplication(App.class);
        servidor.run(args);
    }
    
}
