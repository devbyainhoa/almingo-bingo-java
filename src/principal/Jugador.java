
package principal;

import java.util.Scanner;
import java.util.List;
import java.io.IOException;
import java.util.ArrayList;

import archivos.GestionJugadores;
import archivos.GestionEstado;
import logica.Carton;
import archivos.GestionPreguntas;


public class Jugador {

    public static void main(String[] args) {

        // CREAMOS EL SCANNER PARA LEER LOS DATOS DEL TECLADO
        Scanner teclado = new Scanner(System.in);

        // INICIAMOS EL MENU DEL JUGADOR
        menuJugador(teclado);
    }
    

    public static void menuJugador(Scanner teclado) {

        // CREAMOS LOS OBJETOS PARA GESTIONAR JUGADORES Y ESTADO
        GestionJugadores gestion = new GestionJugadores();
        GestionEstado gestionEstado = new GestionEstado();
        GestionPreguntas gestionPreguntas = new GestionPreguntas();
        
     // COMPROBAMOS SI EL ARCHIVO DE ESTADO NECESITA INICIALIZARSE
        gestionEstado.inicializarEstadoSiNecesario();

        // PEDIMOS EL NOMBRE DEL JUGADOR
        System.out.println("===== MODO JUGADOR =====");
        System.out.print("Introduce tu nombre: ");
        String nombre = teclado.next();

     // COMPROBAMOS SI HAY UNA PARTIDA EN CURSO
        if (gestionEstado.obtenerEstado().equals("EN_CURSO")) {
            System.out.println("No puedes registrarte porque hay una partida en curso.");
            return;
        }

        // COMPROBAMOS SI EL JUGADOR YA ESTA REGISTRADO
        if (gestion.existeJugador(nombre)) {
            System.out.println("Jugador encontrado.");
        }

        // REGISTRAMOS O ACTUALIZAMOS LOS DATOS DEL JUGADOR
        gestion.registrarJugador(nombre);

        // ESPERAMOS HASTA QUE EL CENTRAL INICIE LA PARTIDA
        System.out.println("Esperando a que el central inicie la partida...");

        while (gestionEstado.obtenerEstado().equals("ESPERANDO")) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Se ha interrumpido la espera.");
                return;
            }
        }

        // RECUPERAMOS Y MOSTRAMOS EL CARTON DEL JUGADOR
        Carton miCarton = gestion.obtenerCarton(nombre);

        System.out.println("\n¡LA PARTIDA HA COMENZADO!");
        miCarton.mostrarCarton();
        // INICIAMOS LA DETECCION AUTOMATICA DE NUMEROS
        Thread hiloNumeros = new Thread(() -> detectarNumerosNuevos(gestionEstado));
        hiloNumeros.setDaemon(true);
        hiloNumeros.start();

        // MOSTRAMOS EL MENU DEL JUGADOR
        int opcion;

        do {
        	// COMPROBAMOS SI CENTRAL HA FINALIZADO LA PARTIDA
        	if (gestionEstado.obtenerEstado().equals("FINALIZADA")) {

        	    System.out.println("\n===== PARTIDA FINALIZADA =====");
        	    System.out.println(gestionEstado.obtenerResultado());

        	    break;
        	}
            System.out.println("\n===== MENU DEL JUGADOR =====");
            System.out.println("1. MARCAR NUMERO");
            System.out.println("2. CANTAR LINEA");
            System.out.println("3. CANTAR BINGO");
            System.out.println("4. ABANDONAR PARTIDA");
            System.out.print("Selecciona una opcion: ");

            opcion = teclado.nextInt();

            switch (opcion) {

                case 1:

                    // PEDIMOS EL NUMERO QUE QUIERE MARCAR
                    System.out.print("Introduce el numero que quieres marcar: ");
                    int numero = teclado.nextInt();

                    // CONSULTAMOS LOS NUMEROS QUE HAN SALIDO
                    List<Integer> numerosSalidos = gestionEstado.obtenerNumerosSalidos();

                    // COMPROBAMOS SI EL NUMERO HA SIDO EXTRAIDO
                    if (numerosSalidos.contains(numero)) {

                        // MARCAMOS EL NUMERO EN EL CARTON
                        if (miCarton.marcarNum(numero)) {

                            System.out.println("Numero marcado correctamente.");

                            // GUARDAMOS LAS MARCAS EN EL ARCHIVO
                            gestion.guardarMarcas(nombre, miCarton);

                            // MOSTRAMOS EL CARTON ACTUALIZADO
                            miCarton.mostrarCarton();

                        } else {
                            System.out.println("Ese numero no esta en tu carton o ya estaba marcado.");
                        }

                    } else {
                        System.out.println("Ese numero todavia no ha salido.");
                    }

                    break;

                case 2:

                    // COMPROBAMOS SI YA SE HA CANTADO UNA LINEA
                    if (gestionEstado.lineaCantada())
                    {
                        System.out.println("La linea ya ha sido cantada en esta partida.");

                    } else {

                        // COMPROBAMOS SI EL CARTON TIENE UNA FILA COMPLETA
                        if (miCarton.comprobarLinea())
                        {
                            // REGISTRAMOS LA SOLICITUD DE LINEA
                            gestionEstado.solicitarLinea(nombre);

                            System.out.println("Solicitud de linea enviada al central.");
                            System.out.println("Esperando la validacion de sostenibilidad...");
                         // MOSTRAMOS LA PREGUNTA Y RECOGEMOS LA RESPUESTA
                            responderPregunta(teclado, gestionPreguntas, nombre);

                        } else {

                            System.out.println("Todavia no tienes ninguna linea completa.");
                        }
                    }

                    break;

                case 3:

                    // COMPROBAMOS SI EL CARTON TIENE TODOS LOS NUMEROS MARCADOS
                    if (miCarton.comprobarBingo())
                    {
                        // REGISTRAMOS LA SOLICITUD DE BINGO
                        gestionEstado.solicitarBingo(nombre);

                        System.out.println("Solicitud de bingo enviada al central.");
                        System.out.println("Esperando la validacion de sostenibilidad...");
                     // MOSTRAMOS LA PREGUNTA Y RECOGEMOS LA RESPUESTA
                        responderPregunta(teclado, gestionPreguntas, nombre);

                    } else {

                        System.out.println("Todavia no tienes todos los numeros marcados.");
                    }

                    break;

                case 4:

                    // EL JUGADOR ABANDONA LA PARTIDA
                    gestion.eliminarJugador(nombre);

                    System.out.println("Has abandonado la partida.");

                    break;

                default:

                    System.out.println("Opcion no valida.");
            }

        } while (opcion != 4);

    }
    
 // MUESTRA LA PREGUNTA DE SOSTENIBILIDAD AL JUGADOR
    public static void responderPregunta(Scanner teclado, GestionPreguntas gestionPreguntas, String nombre) {

        try {

            String[] pregunta = null;
         // CREAMOS EL OBJETO PARA CONSULTAR EL ESTADO DE LA PARTIDA
            GestionEstado gestionEstado = new GestionEstado();

            while (pregunta == null) {

                // COMPROBAMOS SI CENTRAL HA FINALIZADO LA PARTIDA
                if (gestionEstado.obtenerEstado().equals("FINALIZADA")) {

                    System.out.println("\n" + gestionEstado.obtenerResultado());
                    return;
                }

                // COMPROBAMOS SI CENTRAL HA PUBLICADO LA PREGUNTA
                pregunta = gestionPreguntas.leerPreguntaActual(nombre);

                Thread.sleep(500);
            }

            // MOSTRAMOS LA PREGUNTA Y SUS OPCIONES
            System.out.println("\n===== PREGUNTA DE SOSTENIBILIDAD =====");
            System.out.println(pregunta[2]);
            System.out.println("A) " + pregunta[3]);
            System.out.println("B) " + pregunta[4]);
            System.out.println("C) " + pregunta[5]);
         // PEDIMOS LA RESPUESTA AL JUGADOR
            String respuesta;

            do {
                System.out.print("Introduce tu respuesta (A, B o C): ");
                respuesta = teclado.next().toUpperCase();

                if (!respuesta.equals("A") && !respuesta.equals("B") && !respuesta.equals("C")) {
                    System.out.println("Respuesta no valida. Introduce A, B o C.");
                }

            } while (!respuesta.equals("A") && !respuesta.equals("B") && !respuesta.equals("C"));

            // OBTENEMOS EL ID DE LA SOLICITUD
            int idSolicitud = Integer.parseInt(pregunta[0]);

            // ENVIAMOS LA RESPUESTA A CENTRAL
            gestionPreguntas.enviarRespuesta(idSolicitud, respuesta);

            System.out.println("Respuesta enviada al central.");
            
         // ESPERAMOS A QUE CENTRAL PUBLIQUE EL RESULTADO
            String resultado = "";

            while (resultado.isEmpty()) {
                resultado = gestionPreguntas.leerResultado(idSolicitud);
                Thread.sleep(500);
            }

            // MOSTRAMOS EL RESULTADO AL JUGADOR
            if (resultado.equals("CORRECTA")) {
                System.out.println("¡Respuesta correcta! Solicitud validada.");

            } else if (resultado.equals("INCORRECTA")) {
                System.out.println("Respuesta incorrecta. Solicitud rechazada.");

            } else if (resultado.equals("TIEMPO_AGOTADO")) {
                System.out.println("Se ha agotado el tiempo. Solicitud rechazada.");
            }

        } catch (IOException e) {

            System.out.println("ERROR AL LEER LA PREGUNTA.");

        } catch (InterruptedException e) {

            System.out.println("Se ha interrumpido la espera.");

        }
    }
    
 // COMPRUEBA SI HAN SALIDO NUMEROS NUEVOS
    public static void detectarNumerosNuevos(GestionEstado gestionEstado) 
    {

        List<Integer> numerosVistos = new ArrayList<>();

        while (gestionEstado.obtenerEstado().equals("EN_CURSO")) {

            List<Integer> numerosSalidos = gestionEstado.obtenerNumerosSalidos();

            for (int numero : numerosSalidos) {

                if (!numerosVistos.contains(numero)) {

                    System.out.println("\n¡HA SALIDO EL NUMERO: " + numero + "!");

                    numerosVistos.add(numero);
                }
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
