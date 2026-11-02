package logica;

public class Sorteo {
	
	// DECLARAMOS UN ARRAY PARA CONTROLAR LOS NUMEROS QUE YA HAN SALIDO
	private boolean[] numerosSalidos = new boolean[46];
	// CONTADOR PARA SABER CUANTOS NUMEROS SE HAN EXTRAIDO
	private int cantidadExtraidos = 0;
	
	public int extraerNumero() 
	{
		if (cantidadExtraidos == 45) {
		    return -1;
		}
		
		int numero;

		do 
		{
		    numero = (int) (Math.random() * 45 + 1);
		} while (numerosSalidos[numero]);
		
		numerosSalidos[numero] = true;
		cantidadExtraidos++;
		
		return numero;

	}

}
