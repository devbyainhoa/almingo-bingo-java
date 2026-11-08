package principal;
import archivos.GestionPreguntas;
import java.io.IOException;

public class PruebaPreguntas {

	public static void main(String[] args) {
		GestionPreguntas gestion = new GestionPreguntas();

		try {
		    gestion.iniciarPartida();

		    String[] pregunta = gestion.obtenerPreguntaAleatoria();
		    System.out.println("RESPUESTA CORRECTA: " + pregunta[5]);

		    gestion.publicarPregunta("Ainhoa", pregunta, 1);

		    String[] datos = gestion.leerPreguntaActual("Ainhoa", 1);

		    if (datos != null) {

		        System.out.println("PREGUNTA RECIBIDA:");
		        System.out.println(datos[2]);
		        System.out.println("A) " + datos[3]);
		        System.out.println("B) " + datos[4]);
		        System.out.println("C) " + datos[5]);
		        
		        gestion.limpiarRespuesta();

		        gestion.enviarRespuesta(1, "C");

		        String respuesta = gestion.leerRespuesta(1);

		        System.out.println("RESPUESTA RECIBIDA POR CENTRAL: " + respuesta);

		        System.out.println("¿ES CORRECTA? " +
		                gestion.comprobarRespuesta(respuesta, pregunta));

		    } else {
		        System.out.println("NO SE HA PODIDO LEER LA PREGUNTA.");
		    }

		} catch (IOException e) {
		    System.out.println("ERROR AL PROBAR LA COMUNICACION.");
		    e.printStackTrace();
		}
	}
		
}
