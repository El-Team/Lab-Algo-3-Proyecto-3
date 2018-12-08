import java.lang.Math;

public class Evaluador {

	/**
	 * MIN
	 * La función MIN toma 2 números enteros como argumentos y retorna el
	 * menor entre los dos
	 */
	public int min(int x, int y) {
		return Math.min(x, y);
	}

	/**
	 * MAX
	 * La función MAX es idéntica a la función MIN a diferencia
	 * que retorna el mayor entre los dos argumentos
	 */
	public int max(int x, int y) {
		return Math.max(x, y);
	}

	/**
	 * SUM
	 * La función SUM recibe como parámetro un entero n y retorna la suma de
	 * los números enteros desde 1 hasta n si este es positivo. Si n es negativo,
	 * se retorna la suma desde −1 hasta n.
	 */
	public int sum(int n) {
		int result = 0;
		if (n > 0) {
			for (int i = 1; i < n; i++) {
				result += i;
			}
		}
		else if (n < 0) {
			for (int i = -1; i > n; i--) {
				result += i;
			}
		}
		return result;
	}


	public static void main(String[] args) {
		Evaluador e = new Evaluador();
	}
}