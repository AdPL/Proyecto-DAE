/**
 * DTO de la clase usuario
 * @author Adrián Perez López
 * @author Rafael Galá Ruiz
 */
package ujaen.proyecto.proyecto_dae.usuario;


public class UsuarioDTO {
    private int id;
    private String nombre;
    private String email;

    public UsuarioDTO(int id, String nombre, String email) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
