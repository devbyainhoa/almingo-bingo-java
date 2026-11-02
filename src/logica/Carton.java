package logica;


public class Carton 
{
	public static final int FILAS=3;
	public static final int COLS=4;

	int[][] carton= new int [FILAS][COLS];
	boolean[][] numMarcado=new boolean [FILAS][COLS];

	public void llenarCarton() {

		for (int fila = 0; fila < FILAS; fila++) 
		{

			for (int col = 0; col < COLS; col++) 
			{
				boolean repetido;

				do {
					repetido = false;

					int numero = (int)(Math.random() * 45 + 1);

					for (int f = 0; f <= fila; f++) {

						int limiteColumnas;

						if (f < fila) 
						{
							limiteColumnas = COLS;
						} else 
						{
							limiteColumnas = col;
						}

						for (int c = 0; c < limiteColumnas; c++) 
						{

							if (carton[f][c] == numero) 
							{
								repetido = true;
							}
						}
					}

					if (!repetido) 
					{
						carton[fila][col] = numero;
					}

				} while (repetido);
			}
		}
	}

	public void mostrarCarton () 
	{
		for (int fila=0;fila<FILAS;fila++) 
		{
			for (int col=0;col<COLS;col++) 
			{
				if (numMarcado[fila][col]) 
				{
					System.out.print(carton[fila][col] + " X ");				
				}  else 
				{
					System.out.print(carton[fila][col]+" ");				

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
}


