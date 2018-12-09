import java.lang.Math;

public class Evaluador {

	/**
	 * MIN
	 * La función MIN toma 2 números enteros como argumentos y retorna el
	 * menor entre los dos
	 */
	public static int min(int x, int y) {
		return Math.min(x, y);
	}

	/**
	 * MAX
	 * La función MAX es idéntica a la función MIN a diferencia
	 * que retorna el mayor entre los dos argumentos
	 */
	public static int max(int x, int y) {
		return Math.max(x, y);
	}

	/**
	 * SUM
	 * La función SUM recibe como parámetro un entero n y retorna la suma de
	 * los números enteros desde 1 hasta n si este es positivo. Si n es negativo,
	 * se retorna la suma desde −1 hasta n.
	 */
	public static int sum(int n) {
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

	/**
	 * Construye un árbol de sintaxis abstracta a partir de una expresión en
	 * notación polaca reversa.
	 */
	public static void buildExprGraphForTheLulz(String reversedPolishExpr) {

	}

	/**
	 * Aplica el algoritmo shunting-yard a la expresión dada para retornar la
	 * misma expresión en notación polaca reversa.
	 */
	private static String convertToReversedPolish(String expr) {
		return "";
	}

	/**
	 * Evalua una expresión en notación polaca reversa.
	 */
	public static int eval(String reversedPolishExpr) {
		return 0;
	}


	/**
	 * Importa un archivo que contiene expresiones y en caso de que estas
	 * expresiones sean válidas, imprime los resultados en la cónsola.
	 * <br><br>
	 * También llama a la función que crea el grafo asociado a cada expresión.
	 * Nótese que dicha llamada se hace únicamente para cumplir con la
	 * especificación del proyecto, sin embargo, su ejecución es irrelevante
	 * para los efectos de las funcionalidades que ofrece la aplicación.
	 */
	public static void importAndEval(String file) {

		String[] expressions = getExpressionsFrom(file);

		int result;
		for (String expr : expressions) {
			String reversedPolishExpr = convertToReversedPolish(expr);
			result = eval(reversedPolishExpr);
			buildExprGraphForTheLulz(reversedPolishExpr);
			System.out.print(result);
		}
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println(
				"Debe proporcionar únicamente el nombre del archivo de entrada"+
				" como argumento"
			);
			System.exit(0);
		}
		importAndEval(args[0]);
	}
}