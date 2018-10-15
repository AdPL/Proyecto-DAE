/**
 * Clase de aplicación de SpringBoot
 * @author Adrián Pérez López
 * @author Rafael Galán Ruiz
 */
package ujaen.proyecto.proyecto_dae;

import ujaen.proyecto.proyecto_dae.cliente.ClienteUsuario;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class App {
    public static void main(String[] args) throws Exception {
        SpringApplication servidor = new SpringApplication(App.class);
        ApplicationContext context = servidor.run(args);
        
        ClienteUsuario usuario = new ClienteUsuario(context);
        usuario.run();
        
    }
    
}
