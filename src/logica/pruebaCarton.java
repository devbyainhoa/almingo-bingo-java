package logica;

public class pruebaCarton {

	public static void main(String[] args) 
	{
		Carton cartonJug = new Carton();
		
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
