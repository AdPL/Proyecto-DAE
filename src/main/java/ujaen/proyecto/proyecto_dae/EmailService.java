/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ujaen.proyecto.proyecto_dae;

/**
 *
 * @author adpl
 */
interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);
}
