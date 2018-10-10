
package ujaen.proyecto.proyecto_dae.usuario;

/**
 *
 * @author adpl
 * @author Rafa
 */
public class UsuarioDTO {
    private int idUsuario;
    private String nombre;
    private String email;

    public UsuarioDTO(int id, String nombre, String email) {
        this.idUsuario = id;
        this.nombre = nombre;
        this.email = email;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int id) {
        this.idUsuario = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    @Override
    public String toString() {
        return this.nombre;
    }
}
