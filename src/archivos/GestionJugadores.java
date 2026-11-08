package archivos;
import archivos.GestionEstado;
//IMPORTAMOS LAS CLASES NECESARIAS PARA TRABAJAR CON ARCHIVOS
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List; // PERMITE TRABAJAR CON LISTAS
import logica.Carton;
import java.util.ArrayList;


public class GestionJugadores 
{
	// INDICAMOS LA RUTA DE LA CARPETA COMPARTIDA
	private Path carpetaCompartida = Paths.get("C:\\Users\\DAM1\\Desktop\\ALMINGO\\prueba-red");
	// INDICAMOS LA RUTA DEL ARCHIVO DE JUGADORES
	private Path archivoJugadores = carpetaCompartida.resolve("jugadores.txt");
	

	// METODO PARA REGISTRAR UN NUEVO JUGADOR
	public void registrarJugador(String nombre)
	{
	    // COMPROBAMOS SI EL ESTADO DE LA PARTIDA ESTA INICIALIZADO
	    GestionEstado gestionEstado = new GestionEstado();
	    gestionEstado.inicializarEstadoSiNecesario();

	    // LEEMOS LOS JUGADORES REGISTRADOS
	    try
	    {
	        List<String> jugadores = Files.readAllLines(archivoJugadores);

	        // RECORREMOS LOS JUGADORES REGISTRADOS
	        for (int i = 0; i < jugadores.size(); i++)
	        {
	            // SEPARAMOS LOS DATOS DEL JUGADOR
	            String[] datos = jugadores.get(i).split(";");

	            // COMPROBAMOS SI EL JUGADOR YA EXISTE
	            if (datos[1].equals(nombre))
	            {
	                // ELIMINAMOS LOS DATOS DEL CARTON ANTERIOR
	                datos[2] = "";

	                // REINICIAMOS LAS MARCAS DEL CARTON
	                datos[3] = "";

	                // ACTIVAMOS AL JUGADOR
	                datos[4] = "ACTIVO";

	                // REINICIAMOS LOS ESTADOS DE LINEA Y BINGO
	                datos[5] = "NO";
	                datos[6] = "NO";

	                // ACTUALIZAMOS LOS DATOS DEL JUGADOR
	                jugadores.set(i, String.join(";", datos));

	                // GUARDAMOS LOS CAMBIOS
	                Files.write(archivoJugadores, jugadores);

	                System.out.println("Jugador registrado correctamente.");
	                return;
	            }
	        }

	     // BUSCAMOS EL PRIMER IDENTIFICADOR DISPONIBLE
	        int numeroJugador = 1;
	        String idJugador = "jugador" + numeroJugador;

	        // COMPROBAMOS SI EL IDENTIFICADOR YA EXISTE
	        while (true)
	        {
	            boolean existeId = false;

	            for (String jugador : jugadores)
	            {
	                String[] datos = jugador.split(";");

	                if (datos[0].equals(idJugador))
	                {
	                    existeId = true;
	                    break;
	                }
	            }

	            // SI EL IDENTIFICADOR ESTA LIBRE, TERMINAMOS
	            if (!existeId)
	            {
	                break;
	            }

	            // PROBAMOS CON EL SIGUIENTE NUMERO
	            numeroJugador++;
	            idJugador = "jugador" + numeroJugador;
	        }

	        // PREPARAMOS LOS DATOS DEL NUEVO JUGADOR
	        String nuevoJugador = idJugador + ";" + nombre + ";;;ACTIVO;NO;NO";

	        // AÑADIMOS EL NUEVO JUGADOR
	        jugadores.add(nuevoJugador);

	        // GUARDAMOS LA LISTA ACTUALIZADA
	        Files.write(archivoJugadores, jugadores);

	        System.out.println("Jugador registrado correctamente.");

	    }
	    catch (IOException e)
	    {
	        System.out.println("Error al registrar el jugador.");
	    }
	}

	
	// METODO PARA CONSULTAR LOS JUGADORES REGISTRADOS
	public void consultarJugadores() 
	{
		

	    // LEEMOS TODAS LAS LINEAS DEL ARCHIVO DE JUGADORES
	    try {
	        List<String> jugadores = Files.readAllLines(archivoJugadores);
	
	        // COMPROBAMOS SI HAY JUGADORES REGISTRADOS
	        if (jugadores.isEmpty()) {
	            System.out.println("Todavia no hay jugadores registrados.");
	        } else {
	
	            // MOSTRAMOS LOS JUGADORES POR CONSOLA
	            for (String jugador : jugadores) {
	                System.out.println(jugador);
	            }
	        }
	
	    } catch (IOException e) 
		    {
		        System.out.println("Error al consultar los jugadores.");
		    }


	}
	
	// METODO PARA CONTAR LOS JUGADORES REGISTRADOS
	public int contarJugadores() 
	{
		// LEEMOS TODAS LAS LINEAS DEL ARCHIVO DE JUGADORES
		try 
		{
		    List<String> jugadores = Files.readAllLines(archivoJugadores);
		 // DEVOLVEMOS EL NUMERO DE JUGADORES REGISTRADOS
		    return jugadores.size();

		} catch (IOException e) {

		    // MOSTRAMOS UN MENSAJE SI HAY UN ERROR
		    System.out.println("Error al contar los jugadores.");
		 // DEVOLVEMOS CERO SI NO PODEMOS LEER EL ARCHIVO
		    return 0;
		}
		

	}
	// METODO PARA COMPROBAR SI UN JUGADOR ESTA REGISTRADO
	public boolean existeJugador(String nombre) 
	{
		// LEEMOS LOS JUGADORES REGISTRADOS
		try 
		{
		    List<String> jugadores = Files.readAllLines(archivoJugadores);
		 // RECORREMOS LOS JUGADORES REGISTRADOS
		    for (String jugador : jugadores) 
		    {

		        // SEPARAMOS LOS DATOS DEL JUGADOR
		        String[] datos = jugador.split(";");
		     // COMPROBAMOS SI EL NOMBRE COINCIDE CON EL JUGADOR REGISTRADO
		        if (datos[1].equals(nombre)) 
		        {
		            return true;
		        }

		    }

		} catch (IOException e) {
		    System.out.println("Error al consultar los jugadores.");
		    return false;
		}
		return false;
	}
	
	// METODO PARA GUARDAR LOS CARTONES DE LOS JUGADORES
	public void guardarCartones(Carton[] cartones) 
	{
		// LEEMOS LOS DATOS ACTUALES DE LOS JUGADORES
		List<String> jugadores = new ArrayList<>();
		// LEEMOS LOS DATOS ACTUALES DEL ARCHIVO
		try {
		    jugadores = Files.readAllLines(archivoJugadores);

		} catch (IOException e) {
		    System.out.println("Error al leer los jugadores.");
		    return;
		}
		// COMPROBAMOS QUE HAY UN CARTON PARA CADA JUGADOR
		if (cartones.length != jugadores.size()) 
		{
		    System.out.println("Error: la cantidad de cartones no coincide con los jugadores.");
		    return;
		}
		
		// RECORREMOS LOS CARTONES GENERADOS
		for (int i = 0; i < cartones.length; i++) {

		    // SEPARAMOS LOS DATOS DEL JUGADOR
		    String[] datos = jugadores.get(i).split(";");
		 // CREAMOS UNA CADENA PARA GUARDAR LOS NUMEROS DEL CARTON
		    String numerosCarton = "";
		 // RECORREMOS LAS FILAS DEL CARTON
		    for (int fila = 0; fila < Carton.FILAS; fila++) {

		        // RECORREMOS LAS COLUMNAS DEL CARTON
		        for (int col = 0; col < Carton.COLS; col++) {

		            // OBTENEMOS EL NUMERO DE LA POSICION ACTUAL
		            int numero = cartones[i].obtenerNumero(fila, col);
		         // AÑADIMOS EL NUMERO A LA CADENA
		            if (numerosCarton.isEmpty()) {
		                numerosCarton = numerosCarton + numero;
		            } else {
		                numerosCarton = numerosCarton + "," + numero;
		            }

		        }
		    }
		 // GUARDAMOS LOS NUMEROS DEL CARTON EN LOS DATOS DEL JUGADOR
		    datos[2] = numerosCarton;
		 // INICIALIZAMOS LAS 12 CASILLAS COMO NO MARCADAS
		    datos[3] = "000000000000";
		    
		    jugadores.set(i, String.join(";", datos));

		}
		
		// GUARDAMOS LOS CAMBIOS EN EL ARCHIVO DE JUGADORES
		try {
		    Files.write(archivoJugadores, jugadores);

		} catch (IOException e) {
		    System.out.println("Error al guardar los cartones.");
		    return;
		}
	}
	
	// METODO PARA GUARDAR LAS CASILLAS MARCADAS DE UN JUGADOR
	public void guardarMarcas(String nombre, Carton carton)
	{
		// LEEMOS LOS JUGADORES DEL ARCHIVO
		try
		{
		    List<String> jugadores = Files.readAllLines(archivoJugadores);
		 // RECORREMOS LOS JUGADORES REGISTRADOS
		    for (int i = 0; i < jugadores.size(); i++)
		    {
		        // SEPARAMOS LOS DATOS DEL JUGADOR ACTUAL
		        String[] datos = jugadores.get(i).split(";");

		        // COMPROBAMOS SI ES EL JUGADOR QUE BUSCAMOS
		        if (datos[1].equals(nombre))
		        {
		        	// CREAMOS UNA CADENA PARA GUARDAR LAS MARCAS
		        	String marcas = "";
		        	// RECORREMOS LAS FILAS DEL CARTON
		        	for (int fila = 0; fila < Carton.FILAS; fila++)
		        	{
		        	    // RECORREMOS LAS COLUMNAS DEL CARTON
		        	    for (int col = 0; col < Carton.COLS; col++)
		        	    {
		        	    	// COMPROBAMOS SI LA CASILLA ESTA MARCADA
		        	    	if (carton.estaMarcado(fila, col))
		        	    	{
		        	    	    // GUARDAMOS UN 1 SI ESTA MARCADA
		        	    	    marcas = marcas + "1";
		        	    	}
		        	    	else
		        	    	{
		        	    	    // GUARDAMOS UN 0 SI NO ESTA MARCADA
		        	    	    marcas = marcas + "0";
		        	    	}

		        	    }
		        	}
		        	// GUARDAMOS LAS MARCAS EN LOS DATOS DEL JUGADOR
		        	datos[3] = marcas;
		        	// ACTUALIZAMOS LOS DATOS DEL JUGADOR EN LA LISTA
		        	jugadores.set(i, String.join(";", datos));
		        	break;
		        }
		    }
		    
		 // GUARDAMOS LOS CAMBIOS EN EL ARCHIVO
		    Files.write(archivoJugadores, jugadores);

		} catch (IOException e) {
		    System.out.println("Error al guardar las marcas.");
		    return;
		}
		

	}
	
	// METODO PARA RECUPERAR EL CARTON DE UN JUGADOR
	public Carton obtenerCarton(String nombre)
	{
		// LEEMOS TODOS LOS JUGADORES DEL ARCHIVO
		try
		{
		    List<String> jugadores = Files.readAllLines(archivoJugadores);
		 // RECORREMOS LA LISTA DE JUGADORES
		    for (int i = 0; i < jugadores.size(); i++)
		    {
		        // OBTENEMOS LA LINEA DEL JUGADOR ACTUAL
		        String linea = jugadores.get(i);

		        // SEPARAMOS LOS DATOS UTILIZANDO EL ;
		        String[] datos = linea.split(";");
		     // COMPROBAMOS SI EL NOMBRE COINCIDE CON EL JUGADOR BUSCADO
		        if (datos[1].equals(nombre))
		        {
		        	// SEPARAMOS LOS NUMEROS DEL CARTON
		        	String[] numeros = datos[2].split(",");
		        	// RECUPERAMOS LAS MARCAS DEL CARTON
		        	String marcas = datos[3];
		        	// CREAMOS UN NUEVO OBJETO CARTON
		        	Carton carton = new Carton();

		        	// CONTADOR PARA RECORRER LOS NUMEROS DEL ARCHIVO
		        	int contador = 0;
		        	// RECORREMOS LAS FILAS DEL CARTON
		        	for (int fila = 0; fila < Carton.FILAS; fila++)
		        	{
		        	    // RECORREMOS LAS COLUMNAS DEL CARTON
		        	    for (int col = 0; col < Carton.COLS; col++)
		        	    {
		        	    	// CONVERTIMOS EL NUMERO DE TEXTO A ENTERO
		        	    	int numero = Integer.parseInt(numeros[contador]);

		        	    	// GUARDAMOS EL NUMERO EN LA POSICION CORRESPONDIENTE
		        	    	carton.guardarNumero(fila, col, numero);
		        	    	// COMPROBAMOS SI LA CASILLA ESTABA MARCADA
		        	    	if (marcas.charAt(contador) == '1')
		        	    	{
		        	    	    // RESTAURAMOS LA MARCA DE LA CASILLA
		        	    	    carton.restaurarMarca(fila, col);
		        	    	}

		        	    	// PASAMOS AL SIGUIENTE NUMERO
		        	    	contador++;
		        	    }
		        	}
		        	// DEVOLVEMOS EL CARTON RECUPERADO
		        	return carton;
		        }
		    }

		}
		catch (IOException e)
		{
		    System.out.println("Error al recuperar el carton del jugador.");
		}
		// DEVOLVEMOS NULL SI NO ENCONTRAMOS AL JUGADOR O HAY UN ERROR
		return null;

	}
	
	// METODO PARA ELIMINAR UN JUGADOR
	public void eliminarJugador(String nombre)
	{
	    try
	    {
	        // LEEMOS LOS JUGADORES REGISTRADOS
	        List<String> jugadores = Files.readAllLines(archivoJugadores);

	        // RECORREMOS LOS JUGADORES
	        for (int i = 0; i < jugadores.size(); i++)
	        {
	            String[] datos = jugadores.get(i).split(";");

	            // COMPROBAMOS SI EL NOMBRE COINCIDE
	            if (datos[1].equals(nombre))
	            {
	                // ELIMINAMOS EL REGISTRO DEL JUGADOR
	                jugadores.remove(i);

	                // GUARDAMOS LOS CAMBIOS
	                Files.write(archivoJugadores, jugadores);

	                System.out.println("Jugador eliminado correctamente.");
	                return;
	            }
	        }

	        System.out.println("No se ha encontrado al jugador.");

	    }
	    catch (IOException e)
	    {
	        System.out.println("Error al eliminar el jugador.");
	    }
	}
}
