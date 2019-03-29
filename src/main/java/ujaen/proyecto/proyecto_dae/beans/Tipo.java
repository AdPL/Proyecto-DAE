
package ujaen.proyecto.proyecto_dae.beans;

/**
 * Clase que describe el Tipo de un evento
 * @author Adrián Pérez López
 * @author Rafael Galán Ruiz
 */


public enum Tipo {
    CULTURAL("Evento cultural", "Los eventos de la categoría cultural son todos aquellos eventos donde los asistentes visitaran distintos monumentos, obras y museos."),
    FESTIVAL("Festival", "Este tipo recoge aquellos eventos en los que se realizan múltiples conciertos."),
    CONCIERTO("Concierto", "Eventos en los que un artista da un concierto.");

    private final String titulo;
    private final String descripcion;

    private Tipo(String titulo, String descripcion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

}
