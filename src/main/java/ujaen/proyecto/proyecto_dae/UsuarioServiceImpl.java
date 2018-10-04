
package ujaen.proyecto.proyecto_dae;

/**
 *
 * @author adpl
 * @author Rafa
 */
public class UsuarioServiceImpl implements UsuarioService{
    String nombre;
    
    public UsuarioServiceImpl() {
        nombre = "";
    }
    
    private static class UsuarioServiceImplHolder {
        private static final UsuarioServiceImpl INSTANCE = new UsuarioServiceImpl();
    }
    
    public static UsuarioServiceImpl getInstance() {
        return UsuarioServiceImplHolder.INSTANCE;
    }

    @Override
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public void hola() {
        System.out.println("El usuario " + getNombre() + " dice hola.");
    }
}
