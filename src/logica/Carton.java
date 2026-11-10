package logica;


public class Carton 
{
	public static final int FILAS=3;
	public static final int COLS=5;

	int[][] carton= new int [FILAS][COLS];
	boolean[][] numMarcado=new boolean [FILAS][COLS];

	public void llenarCarton() {

	    for (int fila = 0; fila < FILAS; fila++) {

	        for (int col = 0; col < COLS; col++) {

	            boolean repetido;

	            // CALCULAMOS EL RANGO DE NUMEROS DE CADA COLUMNA
	            int minimo = (col == 0) ? 1 : col * 10;
	            int maximo = (col == 0) ? 9 : col * 10 + 9;

	            do {

	                repetido = false;

	                int numero = (int)(Math.random() * (maximo - minimo + 1) + minimo);

	                // COMPROBAMOS QUE EL NUMERO NO ESTE REPETIDO
	                for (int f = 0; f <= fila; f++) {

	                    int limiteColumnas;

	                    if (f < fila) {
	                        limiteColumnas = COLS;
	                    } else {
	                        limiteColumnas = col;
	                    }

	                    for (int c = 0; c < limiteColumnas; c++) {

	                        if (carton[f][c] == numero) {
	                            repetido = true;
	                        }
	                    }
	                }

	                if (!repetido) {
	                    carton[fila][col] = numero;
	                }

	            } while (repetido);
	        }
	    }

	    // ORDENAMOS LOS NUMEROS DE CADA COLUMNA DE MENOR A MAYOR
	    for (int col = 0; col < COLS; col++) {

	        for (int fila = 0; fila < FILAS - 1; fila++) {

	            for (int f = fila + 1; f < FILAS; f++) {

	                if (carton[fila][col] > carton[f][col]) {

	                    int auxiliar = carton[fila][col];
	                    carton[fila][col] = carton[f][col];
	                    carton[f][col] = auxiliar;
	                }
	            }
	        }
	    }
	}

	public void mostrarCarton() 
	{
	
	    for (int fila = 0; fila < FILAS; fila++) {
	
	        for (int col = 0; col < COLS; col++) {
	
	            if (numMarcado[fila][col]) {
	
	                // MOSTRAMOS EL NUMERO MARCADO EN ROJO
	                System.out.print("\u001B[31m" + carton[fila][col] + "\u001B[0m ");
	
	            } else {
	
	                System.out.print(carton[fila][col] + " ");
	
	            }
	        }
	
	        System.out.println();
	    }
	}

	
	public boolean marcarNum(int numero)	
	{
		for (int fila=0;fila<FILAS;fila++) 
		{
			for (int col=0;col<COLS;col++) 
			{
				if (carton[fila][col] == numero) 
				{
				    // COMPROBAMOS SI EL NUMERO YA ESTABA MARCADO
				    if (numMarcado[fila][col])
				    {
				        return false;
				    }

				    // MARCAMOS EL NUMERO
				    numMarcado[fila][col] = true;
				    return true;
				}
			}
		}
		return false;
		
	}
	
	public boolean comprobarLinea() 
	{
		for (int fila = 0; fila < FILAS; fila++) {

			boolean filaCompleta = true;

			for (int col = 0; col < COLS; col++) 
			{

	            if (!numMarcado[fila][col]) 
	            {
	                filaCompleta = false;
	                break;
	            }
	        }

	        if (filaCompleta) 
	        {
	            return true;
	        }
	    }

	    return false;
	}
	
	public boolean comprobarBingo() 
	{
		for (int fila = 0; fila < FILAS; fila++) 
		{
	        for (int col = 0; col < COLS; col++) 
	        {
	        	if (!numMarcado[fila][col]) 
	        	{
	        	    return false;
	        	}
	        }
	    }

	    return true;
	}
	// DEVOLVEMOS EL NUMERO ALMACENADO EN UNA POSICION DEL CARTON
	public int obtenerNumero(int fila, int col) {
	    return carton[fila][col];
	}
	
	// METODO PARA COMPROBAR SI DOS CARTONES CUMPLEN LA DIFERENCIA MINIMA
	public boolean cumpleDiferencia(Carton otroCarton) 
	{
		// CONTAMOS LOS NUMEROS QUE NO APARECEN EN EL OTRO CARTON
		int diferentes = 0;
		/// RECORREMOS LAS FILAS DEL PRIMER CARTON
		for (int fila = 0; fila < FILAS; fila++) 
		{

		    // RECORREMOS LAS COLUMNAS DEL PRIMER CARTON
		    for (int col = 0; col < COLS; col++) 
		    {

		        // OBTENEMOS EL NUMERO DE LA CASILLA ACTUAL
		        int numero = carton[fila][col];
		        
		        // INDICAMOS QUE TODAVIA NO HEMOS ENCONTRADO EL NUMERO
		        boolean encontrado = false;

		        // RECORREMOS LAS FILAS DEL SEGUNDO CARTON
		        for (int f = 0; f < FILAS; f++) 
		        {

		            // RECORREMOS LAS COLUMNAS DEL SEGUNDO CARTON
		            for (int c = 0; c < COLS; c++) 
		            {
		            	// COMPROBAMOS SI EL NUMERO APARECE EN EL SEGUNDO CARTON
		            	if (otroCarton.carton[f][c] == numero) {

		            	    // INDICAMOS QUE HEMOS ENCONTRADO EL NUMERO
		            	    encontrado = true;

		            	}
		            }
		        }
		     // COMPROBAMOS SI EL NUMERO NO APARECE EN EL SEGUNDO CARTON
		        if (!encontrado) 
		        {

		            // AUMENTAMOS EL CONTADOR DE NUMEROS DIFERENTES
		            diferentes++;
		        }
		    }
		 
		}
		// COMPROBAMOS SI HAY AL MENOS 4 NUMEROS DIFERENTES
	    return diferentes >= 4;

	}
	// METODO PARA GUARDAR UN NUMERO EN UNA POSICION DEL CARTON
	public void guardarNumero(int fila, int col, int numero)
	{
	    // GUARDAMOS EL NUMERO EN LA POSICION INDICADA
	    carton[fila][col] = numero;
	}
	
	// COMPROBAMOS SI UNA CASILLA ESTA MARCADA
	public boolean estaMarcado(int fila, int col)
	{
	    return numMarcado[fila][col];
	    
	}
	// RESTAURAMOS EL ESTADO DE UNA CASILLA
	public void restaurarMarca(int fila, int col)
	{
	    // INDICAMOS QUE LA CASILLA ESTA MARCADA
	    numMarcado[fila][col] = true;
	}
}


