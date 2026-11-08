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
	
	// METODO PARA OBTENER EL RESULTADO DE LA PARTIDA
	public String obtenerResultado()
	{
	    try
	    {
	        // LEEMOS LOS DATOS DEL ARCHIVO DE ESTADO
	        List<String> estado = Files.readAllLines(archivoEstado);

	        // RECORREMOS LAS LINEAS DEL ARCHIVO
	        for (String linea : estado)
	        {
	            // COMPROBAMOS SI ES LA LINEA DEL RESULTADO
	            if (linea.startsWith("RESULTADO="))
	            {
	                // DEVOLVEMOS EL RESULTADO
	                return linea.substring("RESULTADO=".length());
	            }
	        }
	    }
	    catch (IOException e)
	    {
	        System.out.println("Error al recuperar el resultado.");
	    }

	    return "";
	}

	// METODO PARA COMPROBAR SI EXISTE EL ARCHIVO DE ESTADO
	public void inicializarEstadoSiNecesario()
	{
	    try
	    {
	        // COMPROBAMOS SI EL ARCHIVO NO EXISTE O ESTA VACIO
	        if (!Files.exists(archivoEstado) || Files.size(archivoEstado) == 0)
	        {
	            // INICIALIZAMOS EL ESTADO DE LA PARTIDA
	            actualizarEstado();
	        }

	    }
	    catch (IOException e)
	    {
	        System.out.println("Error al comprobar el archivo de estado.");
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
	            	String numeros = linea.substring("NUMEROS_SALIDOS=".length());
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

	    // INICIALIZAMOS EL RESULTADO DE LA PARTIDA
	    estado.add("RESULTADO=");

	    // INICIALIZAMOS LAS SOLICITUDES DE LOS JUGADORES
	    estado.add("SOLICITUD_LINEA=");
	    estado.add("SOLICITUD_BINGO=");

	    // INDICAMOS QUE TODAVIA NO SE HA CANTADO LINEA
	    estado.add("LINEA_CANTADA=NO");

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
	
	// METODO PARA FINALIZAR LA PARTIDA Y GUARDAR EL RESULTADO
	public void finalizarPartida(String resultado)
	{
	    try
	    {
	        // LEEMOS LOS DATOS ACTUALES DEL ARCHIVO
	        List<String> estado = Files.readAllLines(archivoEstado);

	        // CAMBIAMOS EL ESTADO DE LA PARTIDA
	        estado.set(0, "ESTADO=FINALIZADA");

	        // GUARDAMOS EL RESULTADO DE LA PARTIDA
	        estado.set(3, "RESULTADO=" + resultado);

	        // GUARDAMOS LOS CAMBIOS EN EL ARCHIVO
	        Files.write(archivoEstado, estado);

	    }
	    catch (IOException e)
	    {
	        System.out.println("Error al finalizar la partida.");
	    }
	}
	
	// METODO PARA REGISTRAR UNA SOLICITUD DE LINEA
	public void solicitarLinea(String nombre)
	{
	    try
	    {
	        // LEEMOS LOS DATOS DEL ARCHIVO DE ESTADO
	        List<String> estado = Files.readAllLines(archivoEstado);

	        // GUARDAMOS EL NOMBRE DEL JUGADOR QUE CANTA LINEA
	        estado.set(4, "SOLICITUD_LINEA=" + nombre);

	        // GUARDAMOS LOS CAMBIOS
	        Files.write(archivoEstado, estado);

	    }
	    catch (IOException e)
	    {
	        System.out.println("Error al registrar la solicitud de linea.");
	    }
	}
	
	// METODO PARA CONSULTAR LA SOLICITUD DE LINEA
	public String obtenerSolicitudLinea()
	{
	    try
	    {
	        // LEEMOS LOS DATOS DEL ARCHIVO DE ESTADO
	        List<String> estado = Files.readAllLines(archivoEstado);

	        // DEVOLVEMOS EL NOMBRE DEL JUGADOR
	        return estado.get(4).substring("SOLICITUD_LINEA=".length());

	    }
	    catch (IOException e)
	    {
	        System.out.println("Error al consultar la solicitud de linea.");
	        return "";
	    }
	}
	// METODO PARA ELIMINAR LA SOLICITUD DE LINEA
	public void limpiarSolicitudLinea()
	{
	    try
	    {
	        // LEEMOS LOS DATOS DEL ARCHIVO DE ESTADO
	        List<String> estado = Files.readAllLines(archivoEstado);

	        // BORRAMOS LA SOLICITUD
	        estado.set(4, "SOLICITUD_LINEA=");

	        // GUARDAMOS LOS CAMBIOS
	        Files.write(archivoEstado, estado);

	    }
	    catch (IOException e)
	    {
	        System.out.println("Error al limpiar la solicitud de linea.");
	    }
	}
	// METODO PARA REGISTRAR UNA SOLICITUD DE BINGO
	public void solicitarBingo(String nombre)
	{
	    try
	    {
	        // LEEMOS LOS DATOS DEL ARCHIVO DE ESTADO
	        List<String> estado = Files.readAllLines(archivoEstado);

	        // GUARDAMOS EL NOMBRE DEL JUGADOR QUE CANTA BINGO
	        estado.set(5, "SOLICITUD_BINGO=" + nombre);

	        // GUARDAMOS LOS CAMBIOS
	        Files.write(archivoEstado, estado);

	    }
	    catch (IOException e)
	    {
	        System.out.println("Error al registrar la solicitud de bingo.");
	    }
	}

	// METODO PARA CONSULTAR LA SOLICITUD DE BINGO
	public String obtenerSolicitudBingo()
	{
	    try
	    {
	        // LEEMOS LOS DATOS DEL ARCHIVO DE ESTADO
	        List<String> estado = Files.readAllLines(archivoEstado);

	        // DEVOLVEMOS EL NOMBRE DEL JUGADOR
	        return estado.get(5).substring("SOLICITUD_BINGO=".length());

	    }
	    catch (IOException e)
	    {
	        System.out.println("Error al consultar la solicitud de bingo.");
	        return "";
	    }
	}

	// METODO PARA ELIMINAR LA SOLICITUD DE BINGO
	public void limpiarSolicitudBingo()
	{
	    try
	    {
	        // LEEMOS LOS DATOS DEL ARCHIVO DE ESTADO
	        List<String> estado = Files.readAllLines(archivoEstado);

	        // BORRAMOS LA SOLICITUD
	        estado.set(5, "SOLICITUD_BINGO=");

	        // GUARDAMOS LOS CAMBIOS
	        Files.write(archivoEstado, estado);

	    }
	    catch (IOException e)
	    {
	        System.out.println("Error al limpiar la solicitud de bingo.");
	    }
	}
	
	// METODO PARA COMPROBAR SI YA SE HA CANTADO LINEA
	public boolean lineaCantada()
	{
	    try
	    {
	        // LEEMOS LOS DATOS DEL ARCHIVO DE ESTADO
	        List<String> estado = Files.readAllLines(archivoEstado);

	        // COMPROBAMOS SI LA LINEA YA HA SIDO CANTADA
	        return estado.get(6).equals("LINEA_CANTADA=SI");
	    }
	    catch (IOException e)
	    {
	        System.out.println("Error al consultar si se ha cantado linea.");
	        return false;
	    }
	}

	// METODO PARA REGISTRAR QUE YA SE HA CANTADO LINEA
	public void registrarLineaCantada()
	{
	    try
	    {
	        // LEEMOS LOS DATOS DEL ARCHIVO DE ESTADO
	        List<String> estado = Files.readAllLines(archivoEstado);

	        // INDICAMOS QUE LA LINEA YA HA SIDO CANTADA
	        estado.set(6, "LINEA_CANTADA=SI");

	        // GUARDAMOS LOS CAMBIOS
	        Files.write(archivoEstado, estado);
	    }
	    catch (IOException e)
	    {
	        System.out.println("Error al registrar la linea cantada.");
	    }
	}
}
