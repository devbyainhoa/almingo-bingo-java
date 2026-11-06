
package principal;

import java.util.Scanner;
import java.util.List;

import archivos.GestionJugadores;
import archivos.GestionEstado;
import logica.Carton;

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

        // PEDIMOS EL NOMBRE DEL JUGADOR
        System.out.println("===== MODO JUGADOR =====");
        System.out.print("Introduce tu nombre: ");
        String nombre = teclado.next();

        // COMPROBAMOS SI EL JUGADOR YA ESTA REGISTRADO
        if (gestion.existeJugador(nombre)) {
            System.out.println("Jugador encontrado.");
        } else {
            gestion.registrarJugador(nombre);
            System.out.println("Jugador registrado correctamente.");
        }

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

        // MOSTRAMOS EL MENU DEL JUGADOR
        int opcion;

        do {
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

                    // PENDIENTE DE IMPLEMENTAR LA VALIDACION DE LINEA
                    System.out.println("La comprobacion de linea esta pendiente.");

                    break;

                case 3:

                    // PENDIENTE DE IMPLEMENTAR LA VALIDACION DE BINGO
                    System.out.println("La comprobacion de bingo esta pendiente.");

                    break;

                case 4:

                    // EL JUGADOR ABANDONA LA PARTIDA
                    System.out.println("Has abandonado la partida.");

                    break;

                default:

                    System.out.println("Opcion no valida.");
            }

        } while (opcion != 4);

    }
}
