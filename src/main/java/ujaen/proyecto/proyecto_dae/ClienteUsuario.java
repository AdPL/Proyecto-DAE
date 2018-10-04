
package ujaen.proyecto.proyecto_dae;

import org.springframework.context.ApplicationContext;

/**
 *
 * @author adpl
 * @author Rafa
 */
public class ClienteUsuario {
    ApplicationContext context;
    
    public ClienteUsuario(ApplicationContext context) {
        this.context = context;
    }
    
    public void run() {
        UsuarioService usuario = (UsuarioService) context.getBean(UsuarioService.class);
        usuario.setNombre("Adrián");
        usuario.hola();
    }
}
