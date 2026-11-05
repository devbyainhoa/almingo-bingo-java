package logica;

public class pruebaCarton {

	public static void main(String[] args) 
	{
		Carton cartonJug = new Carton();
		cartonJug.llenarCarton();
		
		// CREAMOS Y GENERAMOS UN SEGUNDO CARTON
		Carton cartonJug2 = new Carton();
		cartonJug2.llenarCarton();
		
		
		// MOSTRAMOS EL PRIMER CARTON
		System.out.println("PRIMER CARTON:");
		cartonJug.mostrarCarton();

		// MOSTRAMOS EL SEGUNDO CARTON
		System.out.println("SEGUNDO CARTON:");
		cartonJug2.mostrarCarton();
		
		// COMPROBAMOS SI LOS CARTONES CUMPLEN LA DIFERENCIA MINIMA
		boolean diferenciaCorrecta = cartonJug.cumpleDiferencia(cartonJug2);

		// MOSTRAMOS EL RESULTADO DE LA COMPARACION
		System.out.println("¿Cumplen la diferencia minima? " + diferenciaCorrecta);
		
		
		
		
		cartonJug.llenarCarton();
		for (int col = 0; col < Carton.COLS; col++) {
		    cartonJug.marcarNum(cartonJug.carton[0][col]);
		}
		for (int fila = 0; fila < Carton.FILAS; fila++) {
		    for (int col = 0; col < Carton.COLS; col++) {
		        cartonJug.marcarNum(cartonJug.carton[fila][col]);
		    }
		}
		cartonJug.mostrarCarton();
		

		boolean encontrado = cartonJug.marcarNum(45);

		System.out.println("¿Número encontrado? " + encontrado);
		
		System.out.println("¿Tiene línea? " + cartonJug.comprobarLinea());
		System.out.println("¿Tiene bingo? " + cartonJug.comprobarBingo());
	}

}
