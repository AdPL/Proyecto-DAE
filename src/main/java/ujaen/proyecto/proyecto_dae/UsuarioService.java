
package ujaen.proyecto.proyecto_dae;

import org.springframework.stereotype.Component;

/**
 *
 * @author adpl
 * @author Rafa
 */
@Component
public interface UsuarioService {
    public void setNombre(String nombre);
    public String getNombre();
    public void hola();
}
