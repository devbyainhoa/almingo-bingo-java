package principal;

import java.util.Scanner;

// IMPORTAMOS LA CLASE PARA GESTIONAR LOS JUGADORES
import archivos.GestionJugadores;

// IMPORTAMOS LA CLASE PARA GESTIONAR EL ESTADO DE LA PARTIDA
import archivos.GestionEstado;

// IMPORTAMOS LA CLASE PARA GENERAR LOS CARTONES
import logica.Carton;

public class Central
{
    // METODO PRINCIPAL PARA EJECUTAR EL PROGRAMA CENTRAL
    public static void main(String[] args)
    {
        Scanner teclado = new Scanner(System.in);

        // MOSTRAMOS EL MENU CENTRAL
        menuCentral(teclado);
    }

    // METODO PARA MOSTRAR EL MENU DEL ADMINISTRADOR
    public static void menuCentral(Scanner teclado)
    {
        int opcion = 0;

        // CREAMOS UN OBJETO PARA GESTIONAR LOS JUGADORES
        GestionJugadores gestion = new GestionJugadores();

        // CREAMOS UN OBJETO PARA GESTIONAR EL ESTADO DE LA PARTIDA
        GestionEstado gestionEstado = new GestionEstado();

        // REPETIMOS EL MENU HASTA QUE EL ADMINISTRADOR ELIJA SALIR
        while (opcion != 3)
        {
            // MOSTRAMOS LAS OPCIONES DEL ADMINISTRADOR
            System.out.println("===== MODO CENTRAL =====");
            System.out.println("1. CONSULTAR JUGADORES REGISTRADOS");
            System.out.println("2. INICIAR PARTIDA");
            System.out.println("3. SALIR");
            System.out.println("Selecciona una opcion o pulse 3 para salir del menu:");

            opcion = teclado.nextInt();

            // COMPROBAMOS LA OPCION ELEGIDA
            switch (opcion)
            {
                case 1:

                    // CONSULTAMOS LOS JUGADORES REGISTRADOS
                    gestion.consultarJugadores();
                    break;

                case 2:

                    // COMPROBAMOS CUANTOS JUGADORES ESTAN REGISTRADOS
                    int cantidadJugadores = gestion.contarJugadores();

                    // COMPROBAMOS SI HAY AL MENOS DOS JUGADORES
                    if (cantidadJugadores < 2)
                    {
                        System.out.println("Se necesitan al menos 2 jugadores para iniciar la partida.");
                    }
                    else
                    {
                        System.out.println("Hay suficientes jugadores. Preparando la partida...");

                        // PREPARAMOS EL ESTADO INICIAL DE LA PARTIDA
                        gestionEstado.actualizarEstado();

                        // CREAMOS UN ARRAY PARA GUARDAR LOS CARTONES
                        Carton[] cartones = new Carton[cantidadJugadores];

                        // RECORREMOS EL ARRAY DE CARTONES
                        for (int i = 0; i < cantidadJugadores; i++)
                        {
                            // INDICAMOS SI EL CARTON CUMPLE LAS CONDICIONES
                            boolean cartonValido = false;

                            // REPETIMOS LA GENERACION HASTA OBTENER UN CARTON VALIDO
                            while (!cartonValido)
                            {
                                // CREAMOS UN NUEVO OBJETO CARTON
                                cartones[i] = new Carton();

                                // GENERAMOS LOS NUMEROS DEL CARTON
                                cartones[i].llenarCarton();

                                // DE MOMENTO CONSIDERAMOS QUE EL CARTON ES VALIDO
                                cartonValido = true;

                                // COMPROBAMOS LOS CARTONES GENERADOS ANTERIORMENTE
                                for (int j = 0; j < i; j++)
                                {
                                    // COMPROBAMOS SI CUMPLE LA DIFERENCIA MINIMA
                                    if (!cartones[i].cumpleDiferencia(cartones[j]))
                                    {
                                        // INDICAMOS QUE EL CARTON NO ES VALIDO
                                        cartonValido = false;
                                    }
                                }
                            }
                        }

                        // MOSTRAMOS LOS CARTONES GENERADOS
                        for (int i = 0; i < cantidadJugadores; i++)
                        {
                            System.out.println("CARTON DEL JUGADOR " + (i + 1));

                            cartones[i].mostrarCarton();

                            System.out.println("--------------------");
                        }

                        // GUARDAMOS LOS CARTONES EN EL ARCHIVO COMPARTIDO
                        gestion.guardarCartones(cartones);

                        // INICIAMOS LA PARTIDA CUANDO LOS CARTONES ESTAN PREPARADOS
                        gestionEstado.iniciarPartida();

                        System.out.println("¡LA PARTIDA HA COMENZADO!");
                    }

                    break;

                case 3:

                    System.out.println("SALIENDO DEL PROGRAMA...");
                    break;

                default:

                    // AVISAMOS SI LA OPCION NO ES VALIDA
                    System.out.println("Opcion no valida. Intentalo de nuevo.");
                    break;
            }
        }
    }
}