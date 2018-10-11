
package ujaen.proyecto.proyecto_dae.cliente;

import java.time.Instant;
import java.util.Date;
import java.util.Scanner;
import org.springframework.context.ApplicationContext;
import ujaen.proyecto.proyecto_dae.evento.EventoDTO;
import ujaen.proyecto.proyecto_dae.evento.GestorEventos;
import ujaen.proyecto.proyecto_dae.evento.Tipo;
import ujaen.proyecto.proyecto_dae.usuario.UsuarioDTO;

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
        int sesion = 0;
        EventoDTO evento;
        
        GestorEventos gestorEventos = (GestorEventos) context.getBean(GestorEventos.class);
        
        sesion = gestorEventos.registrarUsuario("adpl", "oretania", "oretania", "adrianpelopez@gmail.com");
        gestorEventos.crearEvento("prueba", "prueba", "Linares", Tipo.FESTIVAL, Date.from(Instant.now()), 1, sesion);
        sesion = gestorEventos.registrarUsuario("pepe", "pepe", "pepe", "pepe@gmail.com");
        sesion = gestorEventos.registrarUsuario("paco", "paco", "paco", "paco@gmail.com");
        sesion = gestorEventos.registrarUsuario("fsa", "oretania", "oretania", "adrianpelopez@gmail.com");
        
        System.out.println("Número de usuarios en el sistema: " + gestorEventos.getNUsuarios());
        System.out.println("Número de eventos en el sistema: " + gestorEventos.getNEventos());
        
        sesion = gestorEventos.identificarUsuario("pepe", "pepe");
        evento = gestorEventos.buscarEvento("prueba");
        gestorEventos.inscribirUsuario(sesion, evento);
        
        sesion = gestorEventos.identificarUsuario("paco", "paco");
        evento = gestorEventos.buscarEvento("prueba");
        gestorEventos.inscribirUsuario(sesion, evento);
        
        System.out.println(gestorEventos.listaAsistentes(evento));
        //System.out.println(gestorEventos.listaEventosInscrito(sesion));
        
        sesion = gestorEventos.identificarUsuario("pepe", "pepe");
        gestorEventos.cancelarAsistencia(sesion, evento);
        System.out.println(gestorEventos.listaAsistentes(evento));
        //System.out.println(gestorEventos.listaEventosInscrito(sesion));
        
/*        Scanner sc = new Scanner(System.in);
        int opcion;
        UsuarioDTO usuario = null;
        EventoDTO evento = null;
        int sesion = 0;

        GestorEventos gestorEventos = (GestorEventos) context.getBean(GestorEventos.class);
        
        usuario = gestorEventos.registrarUsuario("adpl", "oretania", "oretania", "adrianpelopez@gmail.com");
        
        String nombre, pass, email, titulo, descripcion, localizacion, busqueda;
        int nMax;
        
        do {
            System.out.println("Número de usuarios en el sistema: " + gestorEventos.getNUsuarios());
            System.out.println("Número de eventos en el sistema: " + gestorEventos.getNEventos());
            System.out.println();
            System.out.println("1. Registrar usuario");
            System.out.println("2. Identificar usuario");
            System.out.println("3. Crear evento");
            System.out.println("4. Listar mis eventos (Inscrito)");
            System.out.println("5. Listar mis eventos (Organizador)");
            System.out.println("6. Lista de asistentes a un evento");
            System.out.println("7. Buscar evento por tipo (FESTIVAL)");
            System.out.println("8. Buscar evento por tipo (FESTIVAL) y descripción");
            System.out.println("9. Inscribirse en un evento");
            System.out.println("10. Cancelar un evento");
            System.out.print("Opción: ");
            opcion = sc.nextInt();
            switch (opcion) {
                case 1:
                    sc.nextLine();

                    System.out.print("Nombre: ");
                    nombre = sc.nextLine();
                    System.out.print("Contraseña: ");
                    pass = sc.nextLine();
                    System.out.print("Email: ");
                    email = sc.nextLine();
                    usuario = gestorEventos.registrarUsuario(nombre, pass, pass, email);
                break;
                case 2:
                    sc.nextLine();
                    System.out.print("Nombre / Email: ");
                    nombre = sc.nextLine();
                    System.out.print("Contraseña: ");
                    pass = sc.nextLine();
                    usuario = gestorEventos.identificarUsuario(nombre, pass);
                    if ( usuario != null ) {
                        System.out.println("Bienvenido " + usuario.getNombre());
                    } else {
                        System.out.println("ERROR: Usuario no encontrado.");
                    }
                break;
                case 3:
                    if ( usuario != null ) {
                        sc.nextLine();
                        System.out.print("Título: ");
                        titulo = sc.nextLine();
                        System.out.print("Descripción: ");
                        descripcion = sc.nextLine();
                        System.out.print("Localización: ");
                        localizacion = sc.nextLine();
                        System.out.print("Máximo asistentes: ");
                        nMax = sc.nextInt();
                        gestorEventos.crearEvento(titulo, descripcion, localizacion, Tipo.CONCIERTO, Date.from(Instant.now()), nMax, usuario);
                    } else {
                        System.out.println("ERROR: Debe estar identificado para crear un evento");
                    }
                break;
                case 4:
                    if ( usuario != null ) {
                        System.out.println(gestorEventos.listaEventosInscrito(usuario));
                    } else {
                        System.out.println("ERROR: Debe estar identificado para consultar sus eventos");
                    }
                break;
                case 5:
                    if ( usuario != null ) {
                        System.out.println(gestorEventos.listaEventosOrganizador(usuario));
                    } else {
                        System.out.println("ERROR: Debe estar identificado para consultar sus eventos");
                    }
                break;
                case 6:
                    sc.nextLine();
                    System.out.print("Título del evento: ");
                    titulo = sc.nextLine();
                    evento = gestorEventos.buscarEvento(titulo);
                    
                    if ( evento != null) {
                        for ( UsuarioDTO u : gestorEventos.listaAsistentes(evento) ) {
                            System.out.println(u.getNombre());
                        }
                        //System.out.println("Plazas disponibles: " + evento.getPlazasDisponibles());
                    } else {
                        System.out.println("ERROR: Evento no encontrado");
                    }
                break;
                case 7:
                    System.out.println("Eventos de tipo " + Tipo.FESTIVAL.getTitulo() + ": " + Tipo.FESTIVAL.getDescripcion());
                    System.out.println(gestorEventos.buscarEvento(Tipo.FESTIVAL));
                break;
                case 8:
                    sc.nextLine();
                    System.out.print("Búsqueda: ");
                    busqueda = sc.nextLine();
                    System.out.println("Eventos de tipo " + Tipo.FESTIVAL.getTitulo() + ": " + Tipo.FESTIVAL.getDescripcion());
                    System.out.println(gestorEventos.buscarEvento(Tipo.FESTIVAL, busqueda));
                break;
                case 9:
                    System.out.println("No disponible");
                    /*sc.nextLine();
                    if ( usuario != null ) {
                        System.out.println("Está identificado como " + usuario.getNombre());
                        System.out.print("Título: ");
                        titulo = sc.nextLine();
                        evento = eventoService.buscarEvento(titulo);
                        if ( evento != null ) {
                            eventoService.inscribirUsuario(usuario, evento);
                        } else {
                            System.out.println("ERROR: Evento no encontrado");
                        }
                    } else {
                        System.out.println("ERROR: Debe estar identificado para consultar sus eventos");
                    }*/
                /*break;
                case 10: 
                    System.out.println("No disponible");*/
                    /*
                    if ( usuario != null ) {
                        sc.nextLine();
                        System.out.print("Título: ");
                        busqueda = sc.nextLine();
                        evento = eventoService.buscarEvento(busqueda);
                        if ( evento != null) {
                            eventoService.cancelarEvento(usuario, evento);
                            evento = null;
                        } else {
                            System.out.println("ERROR: Evento no encontrado");
                        }
                    } else {
                        System.out.println("ERROR: Debe estar identificado para consultar sus eventos");
                    }
                break;
            }
        } while ( opcion != 0);*/
    }
}
