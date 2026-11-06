package archivos;

//IMPORTAMOS LAS CLASES NECESARIAS PARA GESTIONAR ARCHIVOS
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;
//IMPORTAMOS ARRAYLIST PARA CREAR LISTAS
import java.util.ArrayList;

public class GestionEstado 
{
	// INDICAMOS LA RUTA DE LA CARPETA COMPARTIDA
	private Path carpetaCompartida = Paths.get("C:\\Users\\DAM1\\Desktop\\ALMINGO\\prueba-red");

	// INDICAMOS LA RUTA DEL ARCHIVO DE ESTADO
	private Path archivoEstado = carpetaCompartida.resolve("estado.txt");

	// METODO PARA CONSULTAR EL ESTADO DE LA PARTIDA
	public void consultarEstado() 
	{

	    // LEEMOS TODAS LAS LINEAS DEL ARCHIVO DE ESTADO
	    try {
	        List<String> estado = Files.readAllLines(archivoEstado);

	        // MOSTRAMOS EL CONTENIDO POR CONSOLA
	        for (String linea : estado) {
	            System.out.println(linea);
	        }

	    } catch (IOException e) {
	        System.out.println("Error al consultar el estado de la partida.");
	    }
	}
	
	// METODO PARA OBTENER EL ESTADO ACTUAL DE LA PARTIDA
	public String obtenerEstado()
	{
	    try
	    {
	        List<String> estado = Files.readAllLines(archivoEstado);
	        return estado.get(0).split("=")[1];
	    }
	    catch (IOException e)
	    {
	        System.out.println("Error al recuperar el estado.");
	        return "ERROR";
	    }
	}

	
	// METODO PARA OBTENER LOS NUMEROS EXTRAIDOS
	public List<Integer> obtenerNumerosSalidos()
	{
	    // CREAMOS UNA LISTA PARA GUARDAR LOS NUMEROS EXTRAIDOS
	    List<Integer> numerosSalidos = new ArrayList<>();

	    try
	    {
	        // LEEMOS LOS DATOS DEL ARCHIVO DE ESTADO
	        List<String> estado = Files.readAllLines(archivoEstado);

	        // RECORREMOS LAS LINEAS DEL ARCHIVO
	        for (String linea : estado)
	        {
	            // COMPROBAMOS SI ES LA LINEA DE LOS NUMEROS EXTRAIDOS
	            if (linea.startsWith("NUMEROS_SALIDOS="))
	            {
	                // OBTENEMOS LOS NUMEROS EXTRAIDOS
	                String numeros = linea.split("=")[1];

	                // COMPROBAMOS SI HAY NUMEROS EXTRAIDOS
	                if (!numeros.isEmpty())
	                {
	                    // SEPARAMOS LOS NUMEROS UTILIZANDO LAS COMAS
	                    String[] listaNumeros = numeros.split(",");

	                    // RECORREMOS LOS NUMEROS
	                    for (String numero : listaNumeros)
	                    {
	                        // CONVERTIMOS CADA NUMERO A ENTERO Y LO AÑADIMOS A LA LISTA
	                        numerosSalidos.add(Integer.parseInt(numero));
	                    }
	                }

	                // SALIMOS DEL BUCLE CUANDO ENCONTRAMOS LA LINEA
	                break;
	            }
	        }
	    }
	    catch (IOException e)
	    {
	        System.out.println("Error al recuperar los numeros extraidos.");
	    }

	    // DEVOLVEMOS LA LISTA DE NUMEROS
	    return numerosSalidos;
	}
	
	// METODO PARA ACTUALIZAR EL ESTADO DE LA PARTIDA
	public void actualizarEstado() 
	{
		// CREAMOS UNA LISTA PARA GUARDAR LOS DATOS DE LA PARTIDA
		List<String> estado = new ArrayList<>();

		// INDICAMOS QUE LA PARTIDA ESTA ESPERANDO A SER INICIADA
		estado.add("ESTADO=ESPERANDO");

		// INDICAMOS QUE TODAVIA NO HA SALIDO NINGUN NUMERO
		estado.add("NUMERO_ACTUAL=0");
		estado.add("NUMEROS_SALIDOS=");
		
		// INTENTAMOS GUARDAR LOS DATOS EN EL ARCHIVO
		try 
		{

		    // ESCRIBIMOS LA LISTA EN EL ARCHIVO DE ESTADO
		    Files.write(archivoEstado, estado);

		} catch (IOException e) 
		{
		    // MOSTRAMOS UN MENSAJE SI SE PRODUCE UN ERROR
		    System.out.println("Error al actualizar el estado de la partida.");
		}

	}
	
	// METODO PARA INICIAR LA PARTIDA
	public void iniciarPartida()
	{
	    // LEEMOS LOS DATOS ACTUALES DEL ESTADO
	    try
	    {
	        List<String> estado = Files.readAllLines(archivoEstado);

	        // CAMBIAMOS EL ESTADO DE LA PARTIDA
	        estado.set(0, "ESTADO=EN_CURSO");

	        // GUARDAMOS LOS CAMBIOS EN EL ARCHIVO
	        Files.write(archivoEstado, estado);

	    } catch (IOException e) {
	        System.out.println("Error al iniciar la partida.");
	    }
	}
	// METODO PARA ACTUALIZAR LOS NUMEROS DEL SORTEO
	public void actualizarNumero(int numero)
	{
	    try
	    {
	        // LEEMOS LOS DATOS ACTUALES DEL ARCHIVO
	        List<String> estado = Files.readAllLines(archivoEstado);

	        // ACTUALIZAMOS EL ULTIMO NUMERO EXTRAIDO
	        estado.set(1, "NUMERO_ACTUAL=" + numero);

	        // COMPROBAMOS SI ES EL PRIMER NUMERO DEL SORTEO
	        if (estado.get(2).equals("NUMEROS_SALIDOS="))
	        {
	            // GUARDAMOS EL PRIMER NUMERO EXTRAIDO
	            estado.set(2, "NUMEROS_SALIDOS=" + numero);

	        } else {

	            // AÑADIMOS EL NUEVO NUMERO AL HISTORIAL
	            estado.set(2, estado.get(2) + "," + numero);
	        }

	        // GUARDAMOS LOS CAMBIOS EN EL ARCHIVO
	        Files.write(archivoEstado, estado);

	    } catch (IOException e) {

	        // MOSTRAMOS UN MENSAJE EN CASO DE ERROR
	        System.out.println("Error al actualizar los numeros del sorteo.");
	    }
	}
}
