package principal;

import java.util.Scanner;
import java.io.IOException;
//IMPORTAMOS LA CLASE PARA REALIZAR EL SORTEO
import logica.Sorteo;
import archivos.GestionJugadores;
import archivos.GestionEstado;
import logica.Carton;
import archivos.GestionPreguntas;

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
		// CREAMOS UN OBJETO PARA GESTIONAR LAS PREGUNTAS
		GestionPreguntas gestionPreguntas = new GestionPreguntas();
		// CONTADOR PARA IDENTIFICAR CADA SOLICITUD
		int idSolicitud = 0;

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
					// CREAMOS UN NUEVO SORTEO PARA LA PARTIDA
					Sorteo sorteo = new Sorteo();
					
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

					// CARGAMOS LAS PREGUNTAS PARA LA PARTIDA
					try {
					    gestionPreguntas.iniciarPartida();
					} catch (IOException e) {
					    System.out.println("ERROR AL CARGAR LAS PREGUNTAS.");
					    e.printStackTrace();
					    break;
					}

					// INICIAMOS LA PARTIDA CUANDO TODO ESTA PREPARADO
					gestionEstado.iniciarPartida();

					System.out.println("¡LA PARTIDA HA COMENZADO!");
					// LIMPIAMOS EL SALTO DE LINEA PENDIENTE DEL TECLADO
					teclado.nextLine();
					// REPETIMOS EL SORTEO HASTA EXTRAER LOS 49 NUMEROS
					while (true)
					{
						//PEDIMOS QUE SAQUE EL NUMERO
						System.out.println("Pulsa ENTER para extraer el siguiente numero...");
						teclado.nextLine();
						// COMPROBAMOS SI HAY SOLICITUDES DE LINEA O BINGO
						boolean haySolicitud = !gestionEstado.obtenerSolicitudLinea().isEmpty()
						        || !gestionEstado.obtenerSolicitudBingo().isEmpty();

						if (haySolicitud) {

						    try {
						        idSolicitud = atenderSolicitudes(gestionEstado, gestionPreguntas, idSolicitud);

						    } catch (IOException e) {
						        System.out.println("ERROR AL GESTIONAR LA SOLICITUD.");
						    } catch (InterruptedException e) {
						        System.out.println("Se ha interrumpido la espera de la respuesta.");
						    }

						    // COMPROBAMOS SI EL BINGO HA FINALIZADO LA PARTIDA
						    if (gestionEstado.obtenerEstado().equals("FINALIZADA")) {
						        System.out.println(gestionEstado.obtenerResultado());
						        break;
						    }

						    // VOLVEMOS AL INICIO DEL BUCLE ANTES DE EXTRAER OTRO NUMERO
						    continue;
						}
						int numero = sorteo.extraerNumero();

						if (numero == -1) {

						    // FINALIZAMOS LA PARTIDA POR EMPATE
						    gestionEstado.finalizarPartida("EMPATE: SE HAN EXTRAIDO LOS 49 NUMEROS SIN BINGO.");

						    System.out.println("¡SE HAN EXTRAIDO LOS 49 NUMEROS!");
						    System.out.println("EL SORTEO HA FINALIZADO.");
						    System.out.println(gestionEstado.obtenerResultado());

						    break;
						}
						// GUARDAMOS EL NUMERO EXTRAIDO EN EL ARCHIVO COMPARTIDO
						gestionEstado.actualizarNumero(numero);
						
						// MOSTRAMOS EL NUMERO EXTRAIDO POR CONSOLA
						System.out.println("NUMERO EXTRAIDO: " + numero);

					}
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
	
	// ATIENDE LAS SOLICITUDES DE LINEA Y BINGO
	public static int atenderSolicitudes(GestionEstado gestionEstado,
	        GestionPreguntas gestionPreguntas, int idSolicitud)
	        throws IOException, InterruptedException {

	    String nombre = "";
	    boolean esBingo = false;

	    // COMPROBAMOS SI HAY UNA SOLICITUD DE LINEA
	    if (!gestionEstado.obtenerSolicitudLinea().isEmpty()) {

	        nombre = gestionEstado.obtenerSolicitudLinea();

	    } else if (!gestionEstado.obtenerSolicitudBingo().isEmpty()) {

	        // SI NO HAY LINEA, COMPROBAMOS SI HAY BINGO
	        nombre = gestionEstado.obtenerSolicitudBingo();
	        esBingo = true;

	    } else {

	        // NO HAY SOLICITUDES PENDIENTES
	        return idSolicitud;
	    }

	 // LIMPIAMOS LOS ARCHIVOS DE LA VALIDACION ANTERIOR
	    gestionPreguntas.limpiarArchivosComunicacion();
	    // OBTENEMOS UNA PREGUNTA ALEATORIA
	    String[] pregunta = gestionPreguntas.obtenerPreguntaAleatoria();

	    if (pregunta == null) {

	        // FINALIZAMOS LA PARTIDA POR FALTA DE PREGUNTAS
	        gestionEstado.finalizarPartida("PARTIDA FINALIZADA: NO QUEDAN PREGUNTAS DE SOSTENIBILIDAD.");

	        System.out.println("NO QUEDAN PREGUNTAS DISPONIBLES.");
	        System.out.println("LA PARTIDA HA FINALIZADO.");

	        return idSolicitud;
	    }

	    // AUMENTAMOS EL IDENTIFICADOR DE LA SOLICITUD
	    idSolicitud++;

	    // PUBLICAMOS LA PREGUNTA PARA EL JUGADOR
	    gestionPreguntas.publicarPregunta(nombre, pregunta, idSolicitud);

	    System.out.println("Pregunta enviada a " + nombre);

	    // ESPERAMOS LA RESPUESTA DURANTE 30 SEGUNDOS
	    String respuesta = gestionPreguntas.esperarRespuesta(idSolicitud);

	    String resultado;

	    // COMPROBAMOS LA RESPUESTA
	    if (respuesta.isEmpty()) {

	        resultado = "TIEMPO_AGOTADO";

	    } else if (gestionPreguntas.comprobarRespuesta(respuesta, pregunta)) {

	        resultado = "CORRECTA";

	    } else {

	        resultado = "INCORRECTA";
	    }

	    // PUBLICAMOS EL RESULTADO PARA EL JUGADOR
	    gestionPreguntas.publicarResultado(idSolicitud, resultado);

	    // MOSTRAMOS EL RESULTADO EN CENTRAL
	    System.out.println("RESULTADO DE " + nombre + ": " + resultado);

	    // SI LA RESPUESTA ES CORRECTA, VALIDAMOS LA SOLICITUD
	    if (resultado.equals("CORRECTA")) {

	        if (esBingo) {

	           

	            // PUBLICAMOS EL AVISO DE BINGO
	            gestionEstado.publicarAviso("¡BINGO! " + nombre + " HA GANADO LA PARTIDA.");
	            
	            // FINALIZAMOS LA PARTIDA

	            gestionEstado.finalizarPartida("GANADOR: " + nombre);

	            System.out.println("¡BINGO VALIDADO! GANADOR: " + nombre);

	        } else {

	            gestionEstado.registrarLineaCantada();

	            // PUBLICAMOS EL AVISO DE LINEA
	            gestionEstado.publicarAviso("¡LINEA VALIDADA! " + nombre + " HA CONSEGUIDO LINEA.");

	            System.out.println("¡LINEA VALIDADA!");
	        }
	    }
	    // ELIMINAMOS LA SOLICITUD ATENDIDA
	    if (esBingo) {
	        gestionEstado.limpiarSolicitudBingo();
	    } else {
	        gestionEstado.limpiarSolicitudLinea();
	    }

	    return idSolicitud;
	}
}