package archivos;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class GestionPreguntas {
	
	private static final Path RUTA_PREGUNTAS = Paths.get("\\\\192.168.0.119\\prueba-red\\preguntas.txt");

	private static final Path RUTA_PREGUNTA_ACTUAL = Paths.get("\\\\192.168.0.119\\prueba-red\\preguntaActual.txt");

	private static final Path RUTA_RESPUESTA = Paths.get("\\\\192.168.0.119\\prueba-red\\respuesta.txt");
	// LISTA DE PREGUNTAS DISPONIBLES DURANTE LA PARTIDA
	private List<String[]> preguntasDisponibles = new ArrayList<>();
	
	// LEE LAS PREGUNTAS DEL ARCHIVO Y LAS GUARDA EN UNA LISTA
	public List<String[]> cargarPreguntas() throws IOException 
	{

	    List<String[]> preguntas = new ArrayList<>();

	    List<String> lineas = Files.readAllLines(RUTA_PREGUNTAS);

	    for (String linea : lineas) {

	        // IGNORA LAS LINEAS VACIAS Y LA CABECERA
	        if (linea.trim().isEmpty() || linea.trim().startsWith("#")) {
	            continue;
	        }

	        // SEPARA LOS CAMPOS UTILIZANDO EL PUNTO Y COMA
	        String[] datos = linea.split(";", -1);

	        // COMPRUEBA QUE LA PREGUNTA TENGA LOS 6 CAMPOS
	        if (datos.length == 6) {
	            preguntas.add(datos);
	        }
	    }

	    return preguntas;
	}
	// CARGA TODAS LAS PREGUNTAS AL INICIAR UNA PARTIDA
	public void iniciarPartida() throws IOException {

	    preguntasDisponibles = cargarPreguntas();

	}
	// DEVUELVE UNA PREGUNTA ALEATORIA Y LA ELIMINA DE LA LISTA
	public String[] obtenerPreguntaAleatoria() {

	    if (preguntasDisponibles.isEmpty()) {
	        return null;
	    }

	    int posicion = (int) (Math.random() * preguntasDisponibles.size());

	    String[] pregunta = preguntasDisponibles.remove(posicion);

	    return pregunta;
	}
	// PUBLICA LA PREGUNTA QUE DEBE RESPONDER EL JUGADOR
	public void publicarPregunta(String nombre, String[] pregunta, int idSolicitud) throws IOException {
	    List<String> datos = new ArrayList<>();

	    datos.add("ID_SOLICITUD=" + idSolicitud);
	    datos.add("JUGADOR=" + nombre);
	    datos.add("ID=" + pregunta[0]);
	    datos.add("PREGUNTA=" + pregunta[1]);
	    datos.add("A=" + pregunta[2]);
	    datos.add("B=" + pregunta[3]);
	    datos.add("C=" + pregunta[4]);

	    Files.write(RUTA_PREGUNTA_ACTUAL, datos);
	}
	
	// PUBLICA EL RESULTADO DE LA VALIDACION
	public void publicarResultado(int idSolicitud, String resultado) throws IOException {

	    List<String> datos = new ArrayList<>();

	    datos.add("ID_SOLICITUD=" + idSolicitud);
	    datos.add("RESULTADO=" + resultado);

	    Files.write(RUTA_PREGUNTA_ACTUAL, datos);
	}
	// LEE EL RESULTADO DE LA VALIDACION
	public String leerResultado(int idSolicitud) throws IOException {

	    if (!Files.exists(RUTA_PREGUNTA_ACTUAL)) {
	        return "";
	    }

	    List<String> datos = Files.readAllLines(RUTA_PREGUNTA_ACTUAL);

	    String id = "";
	    String resultado = "";

	    for (String linea : datos) {

	        if (linea.startsWith("ID_SOLICITUD=")) {
	            id = linea.substring("ID_SOLICITUD=".length());
	        }

	        if (linea.startsWith("RESULTADO=")) {
	            resultado = linea.substring("RESULTADO=".length());
	        }
	    }

	    if (id.equals(String.valueOf(idSolicitud))) {
	        return resultado;
	    }

	    return "";
	}
	
	// LIMPIA LOS ARCHIVOS DE COMUNICACION AL TERMINAR LA VALIDACION
	public void limpiarArchivosComunicacion() throws IOException {

	    Files.write(RUTA_PREGUNTA_ACTUAL, new ArrayList<String>());

	    Files.write(RUTA_RESPUESTA, new ArrayList<String>());

	}
	// LEE LA PREGUNTA PUBLICADA POR EL CENTRAL
	public String[] leerPreguntaActual(String nombre) throws IOException {
	    if (!Files.exists(RUTA_PREGUNTA_ACTUAL)) {
	        return null;
	    }

	    List<String> datos = Files.readAllLines(RUTA_PREGUNTA_ACTUAL);

	    String id = "";
	    String jugador = "";
	    String idPregunta = "";
	    String enunciado = "";
	    String opcionA = "";
	    String opcionB = "";
	    String opcionC = "";

	    for (String linea : datos) {

	        if (linea.startsWith("ID_SOLICITUD=")) {
	            id = linea.substring("ID_SOLICITUD=".length());
	        }

	        if (linea.startsWith("JUGADOR=")) {
	            jugador = linea.substring("JUGADOR=".length());
	        }

	        if (linea.startsWith("ID=")) {
	            idPregunta = linea.substring("ID=".length());
	        }

	        if (linea.startsWith("PREGUNTA=")) {
	            enunciado = linea.substring("PREGUNTA=".length());
	        }

	        if (linea.startsWith("A=")) {
	            opcionA = linea.substring("A=".length());
	        }

	        if (linea.startsWith("B=")) {
	            opcionB = linea.substring("B=".length());
	        }

	        if (linea.startsWith("C=")) {
	            opcionC = linea.substring("C=".length());
	        }
	    }

	    if (jugador.equals(nombre) && !id.isEmpty()) {
	        return new String[] {id, idPregunta, enunciado, opcionA, opcionB, opcionC};
	    }

	    return null;
	}
	
	
	// COMPRUEBA QUE LAS PREGUNTAS SE LEEN CORRECTAMENTE
	public void probarLectura() {

	    try {
	        List<String[]> preguntas = cargarPreguntas();

	        System.out.println("PREGUNTAS CARGADAS: " + preguntas.size());

	        for (String[] pregunta : preguntas) {
	            System.out.println(pregunta[0] + " - " + pregunta[1]);
	        }

	    } catch (IOException e) {
	        System.out.println("ERROR AL LEER EL ARCHIVO DE PREGUNTAS.");
	        e.printStackTrace();
	    }
	}
	
	// LEE LA RESPUESTA DEL JUGADOR SI CORRESPONDE A LA SOLICITUD ACTUAL
	public String leerRespuesta(int idSolicitud) throws IOException {

	    if (!Files.exists(RUTA_RESPUESTA)) {
	        return "";
	    }

	    List<String> datos = Files.readAllLines(RUTA_RESPUESTA);

	    String id = "";
	    String respuesta = "";

	    for (String linea : datos) {

	        if (linea.startsWith("ID_SOLICITUD=")) {
	            id = linea.substring("ID_SOLICITUD=".length());
	        }

	        if (linea.startsWith("RESPUESTA=")) {
	            respuesta = linea.substring("RESPUESTA=".length());
	        }
	    }

	    if (id.equals(String.valueOf(idSolicitud))) {
	        return respuesta.trim().toUpperCase();
	    }

	    return "";
	}
	
	// LIMPIA LA RESPUESTA ANTES DE UNA NUEVA VALIDACION
	public void limpiarRespuesta() throws IOException {

	    Files.write(RUTA_RESPUESTA, new ArrayList<String>());

	}
	
	// COMPRUEBA SI LA RESPUESTA DEL JUGADOR ES CORRECTA
	public boolean comprobarRespuesta(String respuesta, String[] pregunta) {

	    return respuesta.equalsIgnoreCase(pregunta[5]);
	}
	// GUARDA LA RESPUESTA DEL JUGADOR EN EL ARCHIVO COMPARTIDO
	public void enviarRespuesta(int idSolicitud, String respuesta) throws IOException {

	    List<String> datos = new ArrayList<>();

	    datos.add("ID_SOLICITUD=" + idSolicitud);
	    datos.add("RESPUESTA=" + respuesta.toUpperCase());

	    Files.write(RUTA_RESPUESTA, datos);
	}
	
	// ESPERA LA RESPUESTA DEL JUGADOR DURANTE UN MAXIMO DE 30 SEGUNDOS
	public String esperarRespuesta(int idSolicitud) throws InterruptedException, IOException {

	    int tiempo = 0;

	    while (tiempo < 30) {

	        String respuesta = leerRespuesta(idSolicitud);

	        if (!respuesta.isEmpty()) {
	            return respuesta;
	        }

	        Thread.sleep(1000);
	        tiempo++;
	    }

	    return "";
	}

}
