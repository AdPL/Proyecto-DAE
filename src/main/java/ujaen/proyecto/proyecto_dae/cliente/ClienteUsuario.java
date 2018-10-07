
package ujaen.proyecto.proyecto_dae.cliente;

import java.time.Instant;
import java.util.Date;
import java.util.Scanner;
import ujaen.proyecto.proyecto_dae.usuario.UsuarioService;
import org.springframework.context.ApplicationContext;
import ujaen.proyecto.proyecto_dae.evento.Evento;
import ujaen.proyecto.proyecto_dae.evento.EventoService;
import ujaen.proyecto.proyecto_dae.evento.Tipo;
import ujaen.proyecto.proyecto_dae.usuario.Usuario;

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
        Scanner sc = new Scanner(System.in);
        int opcion;
        Usuario usuario = null;
        Evento evento = null;

        UsuarioService usuarioService = (UsuarioService) context.getBean(UsuarioService.class);
        EventoService   eventoService =  (EventoService) context.getBean(EventoService.class);
        eventoService.crearEvento(usuarioService.registrarUsuario("pepe", "pepe", "pepe", "pepe@gmail.com"), "pepe", "pepe", "jaen", 20, Date.from(Instant.now()));
        String nombre, pass, email, titulo, descripcion, localizacion, busqueda;
        int nMax;
        
        do {
            System.out.println("Número de usuarios en el sistema: " + usuarioService.getNUsuarios());
            System.out.println("Número de eventos en el sistema: " + eventoService.getNEventos());
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
                    usuario = usuarioService.registrarUsuario(nombre, pass, pass, email);
                break;
                case 2:
                    sc.nextLine();
                    System.out.print("Nombre / Email: ");
                    nombre = sc.nextLine();
                    System.out.print("Contraseña: ");
                    pass = sc.nextLine();
                    usuario = usuarioService.identificarUsuario(nombre, pass);
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
                        eventoService.crearEvento(usuario, titulo, descripcion, localizacion, nMax, Date.from(Instant.now()));
                    } else {
                        System.out.println("ERROR: Debe estar identificado para crear un evento");
                    }
                break;
                case 4:
                    if ( usuario != null ) {
                        System.out.println(usuarioService.listaEventosInscrito(usuario));
                    } else {
                        System.out.println("ERROR: Debe estar identificado para consultar sus eventos");
                    }
                break;
                case 5:
                    if ( usuario != null ) {
                        System.out.println(usuarioService.listaEventosOrganizador(usuario));
                    } else {
                        System.out.println("ERROR: Debe estar identificado para consultar sus eventos");
                    }
                break;
                case 6: 
                    sc.nextLine();
                    System.out.print("Título del evento: ");
                    titulo = sc.nextLine();
                    evento = eventoService.buscarEvento(titulo);
                    if ( evento != null) {
                        for ( Usuario user : evento.getAsistentes()) {
                            System.out.println(user.getNombre());
                        }
                        System.out.println("Plazas disponibles: " + evento.getPlazasDisponibles());
                    } else {
                        System.out.println("ERROR: Evento no encontrado");
                    }
                break;
                case 7:
                    System.out.println("Eventos de tipo " + Tipo.FESTIVAL.getTitulo() + ": " + Tipo.FESTIVAL.getDescripcion());
                    System.out.println(eventoService.buscarEvento(Tipo.FESTIVAL));
                break;
                case 8:
                    sc.nextLine();
                    System.out.print("Búsqueda: ");
                    busqueda = sc.nextLine();
                    System.out.println("Eventos de tipo " + Tipo.FESTIVAL.getTitulo() + ": " + Tipo.FESTIVAL.getDescripcion());
                    System.out.println(eventoService.buscarEvento(Tipo.FESTIVAL, busqueda));
                break;
                case 9:
                    sc.nextLine();
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
                    }
                break;
                case 10:
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
            }
        } while ( opcion != 0);
    }
}
