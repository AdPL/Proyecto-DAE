/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ujaen.proyecto.proyecto_dae.excepciones;

/**
 *
 * @author adria
 */
public class UsuarioExistente extends RuntimeException {
    
    public UsuarioExistente(String message) {
        super(message);
    }
}
