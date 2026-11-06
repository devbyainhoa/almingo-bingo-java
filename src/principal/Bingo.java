package principal;

import java.util.Scanner;
import java.util.List;
//IMPORTAMOS LA CLASE PARA GESTIONAR LOS JUGADORES
import archivos.GestionJugadores; 
//IMPORTAMOS LA CLASE PARA GESTIONAR EL ESTADO DE LA PARTIDA
import archivos.GestionEstado;
//IMPORTAMOS LA CLASE PARA GENERAR LOS CARTONES
import logica.Carton;

public class Bingo {

	// INDICAMOS EL MODO DE FUNCIONAMIENTO DE ESTE ORDENADOR
	private static final String MODO = "JUGADOR";

	public static void main(String[] args) 
	{
		Scanner teclado = new Scanner(System.in); 
		
		// COMPROBAMOS EL MODO DE FUNCIONAMIENTO
		if (MODO.equals("CENTRAL")) {
		    menuCentral(teclado);
		} else {
		    menuJugador(teclado);
		}
	}

	// METODO PARA MOSTRAR EL MENU DEL ADMINISTRADOR
	public static void menuCentral(Scanner teclado) 
	{
		int opcion = 0;
		
		// CREAMOS UN OBJETO PARA GESTIONAR LOS JUGADORES
		GestionJugadores gestion = new GestionJugadores();
		// CREAMOS UN OBJETO PARA GESTIONAR EL ESTADO DE LA PARTIDA
		GestionEstado gestionEstado = new GestionEstado();

		// COMPROBAMOS LA OPCION ELEGIDA
		// REPETIMOS EL MENU HASTA QUE EL ADMINISTRADOR ELIJA SALIR
		while (opcion != 3) 
		{			
			// MOSTRAMOS LAS OPCIONES DEL ADMINISTRADOR
			System.out.println("===== MODO CENTRAL =====");
			System.out.println("1. CONSULTAR JUGADORES REGISTRADOS");
			System.out.println("2. INICIAR PARTIDA");
			System.out.println("3. SALIR");
			System.out.println("Selecciona una opcion o pulse 3 para salir del menú:");
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
			        if (cantidadJugadores < 2) {
			            System.out.println("Se necesitan al menos 2 jugadores para iniciar la partida.");
			        } else {
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
				        	while (!cartonValido) {

				        	    // CREAMOS UN NUEVO OBJETO CARTON
				        	    cartones[i] = new Carton();

				        	    // GENERAMOS LOS NUMEROS DEL CARTON
				        	    cartones[i].llenarCarton();

				        	    // DE MOMENTO CONSIDERAMOS QUE EL CARTON ES VALIDO
				        	    cartonValido = true;
				        	 // COMPROBAMOS LOS CARTONES GENERADOS ANTERIORMENTE
				        	    for (int j = 0; j < i; j++) {

				        	        // COMPROBAMOS SI CUMPLE LA DIFERENCIA MINIMA
				        	        if (!cartones[i].cumpleDiferencia(cartones[j])) {

				        	            // INDICAMOS QUE EL CARTON NO ES VALIDO
				        	            cartonValido = false;
				        	        }
				        	    }
				        	}
				        }
				     // MOSTRAMOS LOS CARTONES GENERADOS
				        for (int i = 0; i < cantidadJugadores; i++) {

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
	
	// METODO PARA MOSTRAR EL MENU DEL JUGADOR
	public static void menuJugador(Scanner teclado) 
	{
		
		int opcion = 0; // VARIABLE PARA GUARDAR LA OPCIÓN ESCOGIDA POR EL JUGADOR
		// GUARDAMOS EL NOMBRE DEL JUGADOR
		System.out.println("Introduce tu nombre:");
		String nombreJugador = teclado.next();
		// CREAMOS UN OBJETO PARA GESTIONAR LOS JUGADORES
		GestionJugadores gestion = new GestionJugadores();
		// CREAMOS UN OBJETO PARA CONSULTAR EL ESTADO DE LA PARTIDA
		GestionEstado gestionEstado = new GestionEstado();
		// COMPROBAMOS SI EL JUGADOR ESTA REGISTRADO
		if (gestion.existeJugador(nombreJugador)) 
		{
		    System.out.println("Jugador encontrado. Bienvenido " + nombreJugador);
		} else {
			// REGISTRAMOS AUTOMATICAMENTE AL NUEVO JUGADOR
		    System.out.println("El jugador no esta registrado. Vamos a registrarlo.");
		    gestion.registrarJugador(nombreJugador);
		}
		// AVISAMOS QUE EL JUGADOR ESTA ESPERANDO
		System.out.println("Esperando a que comience la partida...");
		// ESPERAMOS A QUE EL ADMINISTRADOR INICIE LA PARTIDA
		while (gestionEstado.obtenerEstado().equals("ESPERANDO"))
		{
		    // ESPERAMOS UN SEGUNDO ANTES DE VOLVER A CONSULTAR
		    try
		    {
		        Thread.sleep(1000);
		    } catch (InterruptedException e) {
		        System.out.println("Se ha interrumpido la espera.");
		    }
		}
		// RECUPERAMOS EL CARTON DEL JUGADOR
		Carton miCarton = gestion.obtenerCarton(nombreJugador);
		// COMPROBAMOS SI HEMOS RECUPERADO EL CARTON
		if (miCarton != null)
		{
		    // MOSTRAMOS EL CARTON AL JUGADOR
		    System.out.println("===== TU CARTON =====");
		    miCarton.mostrarCarton();
		}
		else
		{
		    System.out.println("No se ha podido recuperar tu carton.");
		    return;
		}
		// REPETIMOS EL MENU HASTA QUE EL JUGADOR ABANDONE
		while (opcion != 4) 
		{
		    System.out.println("===== MODO JUGADOR =====");
		    System.out.println("1. MARCAR UN NUMERO");
		    System.out.println("2. CANTAR LINEA");
		    System.out.println("3. CANTAR BINGO");
		    System.out.println("4. ABANDONAR PARTIDA");
		    System.out.println("Selecciona una opcion:");

		    opcion = teclado.nextInt();

		    // COMPROBAMOS LA OPCION SELECCIONADA
		    switch (opcion) 
		    {
		    case 1:

		        // PEDIMOS AL JUGADOR EL NUMERO QUE QUIERE MARCAR
		        System.out.println("Introduce el numero que quieres marcar:");

		        // GUARDAMOS EL NUMERO INTRODUCIDO
		        int numero = teclado.nextInt();

		        // RECUPERAMOS LOS NUMEROS QUE HAN SIDO EXTRAIDOS
		        List<Integer> numerosSalidos = gestionEstado.obtenerNumerosSalidos();

		        // COMPROBAMOS SI EL NUMERO HA SIDO EXTRAIDO
		        if (numerosSalidos.contains(numero))
		        {
		            // COMPROBAMOS SI EL NUMERO ESTA EN EL CARTON Y LO MARCAMOS
		            if (miCarton.marcarNum(numero))
		            {
		                System.out.println("Numero marcado correctamente.");

		                // GUARDAMOS LAS CASILLAS MARCADAS DEL JUGADOR
		                gestion.guardarMarcas(nombreJugador, miCarton);
		            }
		            else
		            {
		                System.out.println("Ese numero no esta en tu carton o ya esta marcado.");
		            }
		        }
		        else
		        {
		            System.out.println("Ese numero todavia no ha salido.");
		        }

		        // MOSTRAMOS EL CARTON ACTUALIZADO
		        miCarton.mostrarCarton();

		        break;

		        case 2:
		            System.out.println("Cantar linea.");
		            break;

		        case 3:
		            System.out.println("Cantar bingo.");
		            break;

		        case 4:
		            System.out.println("Has abandonado la partida.");
		            break;

		        default:
		            System.out.println("Opcion no valida.");
		    }
		}
		
	}
}

