package archivos;
//IMPORTAMOS LAS CLASES NECESARIAS PARA TRABAJAR CON ARCHIVOS
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.StandardOpenOption; // PERMITE AÑADIR INFORMACION AL FINAL DEL ARCHIVO
import java.util.List; // PERMITE TRABAJAR CON LISTAS

public class PruebaArchivo {
	public static void main(String[] args) 
	{
		// INDICAMOS LA RUTA DE LA CARPETA COMPARTIDA
		Path carpetaCompartida = Paths.get("C:\\Users\\DAM1\\Desktop\\ALMINGO\\prueba-red");
		
		// INDICAMOS LA RUTA DE LA CARPETA DE ACCIONES
		Path carpetaAcciones = carpetaCompartida.resolve("acciones");
		// INDICAMOS LA RUTA DEL ARCHIVO DE JUGADORES
		Path archivoJugadores = carpetaCompartida.resolve("jugadores.txt");

		// CREAMOS LA CARPETA SI TODAVIA NO EXISTE
		try {
			Files.createDirectories(carpetaAcciones);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// COMPROBAMOS SI EL ARCHIVO DE JUGADORES EXISTE
		if (!Files.exists(archivoJugadores)) 
		{

		    try {
		        // CREAMOS EL ARCHIVO DE JUGADORES
		        Files.createFile(archivoJugadores);

		    } catch (IOException e) {
		        System.out.println("Error al crear el archivo de jugadores");
		    }
		}
		
		// GUARDAMOS UN JUGADOR DE PRUEBA
		try {
		    Files.writeString(archivoJugadores,
		            "jugador1;Selene;12,25,38,41,3,19,27,44,8,16,32,5;ACTIVO;NO;NO");

		} catch (IOException e) {
		    System.out.println("Error al guardar el jugador");
		}
		// AÑADIMOS UN SEGUNDO JUGADOR SIN BORRAR EL ANTERIOR
		try {
		    Files.writeString(archivoJugadores,
		            "\njugador2;Laura;7,14,22,35,2,18,29,43,9,11,31,40;ACTIVO;NO;NO",
		            StandardOpenOption.APPEND);

		} catch (IOException e) 
			{
			    System.out.println("Error al añadir el jugador");
			}
		
		// LEEMOS TODAS LAS LINEAS DEL ARCHIVO DE JUGADORES
		try {
		    List<String> jugadores = Files.readAllLines(archivoJugadores);

		    // MOSTRAMOS LOS JUGADORES POR CONSOLA
		    for (String jugador : jugadores) {
		        System.out.println(jugador);
		    }

		} catch (IOException e) {
		    System.out.println("Error al leer los jugadores");
		}
		// CREAMOS UN OBJETO PARA GESTIONAR LOS JUGADORES
		GestionJugadores gestion = new GestionJugadores();

		// CONSULTAMOS LOS JUGADORES REGISTRADOS
		gestion.consultarJugadores();
	}

}
