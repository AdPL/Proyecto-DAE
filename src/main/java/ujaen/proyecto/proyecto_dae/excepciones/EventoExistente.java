/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ujaen.proyecto.proyecto_dae.excepciones;

/**
 *
 * @author adpl
 */
public class EventoExistente extends RuntimeException {
    
    public EventoExistente(String message) {
        super(message);
    }
    
}
