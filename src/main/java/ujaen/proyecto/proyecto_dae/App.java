
package ujaen.proyecto.proyecto_dae;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author adpl
 * @author Rafa
 */
@SpringBootApplication
public class App {
    public static void main(String[] args) throws Exception {
        SpringApplication servidor = new SpringApplication(App.class);
        ApplicationContext context = servidor.run(args);
        
        ClienteUsuario usuario = new ClienteUsuario(context);
        usuario.run();
        
    }
    
}
