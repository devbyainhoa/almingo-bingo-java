package logica;

public class Sorteo {
	
	// DECLARAMOS UN ARRAY PARA CONTROLAR LOS NUMEROS QUE YA HAN SALIDO
	private boolean[] numerosSalidos = new boolean[50];
	// CONTADOR PARA SABER CUANTOS NUMEROS SE HAN EXTRAIDO
	private int cantidadExtraidos = 0;
	
	public int extraerNumero() 
	{
		if (cantidadExtraidos == 49) {
		    return -1;
		}
		
		int numero;

		do 
		{
		    numero = (int) (Math.random() * 49 + 1);
		} while (numerosSalidos[numero]);
		
		numerosSalidos[numero] = true;
		cantidadExtraidos++;
		
		return numero;

	}

}
