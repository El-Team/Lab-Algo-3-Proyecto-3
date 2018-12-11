import java.lang.Math;
import java.io.FileNotFoundException;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.io.IOException;
import java.lang.Character;
import java.util.Stack;
import java.lang.StringBuilder;
import java.lang.Integer;

public class Evaluador {

	/**
	 * Lista de operadores donde:<br>
	 * +: representa adición<br>
	 * -: representa substracción<br>
	 * *: representa multiplicación<br>
	 * /: representa división<br>
	 * S: representa SUM()<br>
	 * M: representa MAX()<br>
	 * N: representa MIN()
	 */
	private final String operators = "+-*/SMN";

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
	 * Importa un archivo de texto que contiene expresiones.
	 */
	public static List<String> getExpressionsFrom(String file) {

		try {
			if (!Utilidades.isValidPath(file)) {
				throw new FileNotFoundException();
			}
		}
		catch(FileNotFoundException e) {
			System.out.println(
				"No fue posible importar el archivo, verifique que el nombre " +
				"es el correcto"
			);
		}

		List<String> lines = null;
		try {
			lines = Files.readAllLines(
				Paths.get(file),
				Charset.defaultCharset()
			);
		}
		catch(IOException e) {
			e.printStackTrace();
		}

		return lines;
	}
	
	/**
	 * Aplica el algoritmo shunting-yard a la expresión dada para retornar la
	 * misma expresión en notación polaca reversa.
	 */
	private static String convertToReversedPolish(String expr) {
		return ShuntingYard.toPostfix(expr);
	}

	/**
	 * Evalua una expresión en notación polaca reversa.
	 */
	public static int eval(String reversedPolishExpr) {
		Stack  stack  = new Stack();
		char[] tokens = reversedPolishExpr.toCharArray();
		String token  = null;

		for (int i = 0; i < reversedPolishExpr.length(); i++) {
			token = Character.toString(tokens[i]);
			stack.push(token);
			if (stack.peek().equals("+")) {
				stack.pop();
				stack.push((int)stack.pop() + (int)stack.pop());
			}
			else if (stack.peek().equals("-")) {
				stack.pop();
				stack.push((int)stack.pop() - (int)stack.pop());
			}
			else if (stack.peek().equals("*")) {
				stack.pop();
				stack.push((int)stack.pop() * (int)stack.pop());
			}
			else if (stack.peek().equals("/")) {
				stack.pop();
				int divisor = (int)stack.pop();
				stack.push((int)stack.pop() / divisor);
			}
			else if (stack.peek().equals("S")) {
				stack.pop();
				stack.push(Integer.toString(
					sum((int)stack.pop()))
				);
			}
			else if (stack.peek().equals("M")) {
				stack.pop();
				stack.push(Integer.toString(
					max((int)stack.pop(), (int)stack.pop()))
				);
			}
			else if (stack.peek().equals("N")) {
				stack.pop();
				stack.push(Integer.toString(
					min((int)stack.pop(), (int)stack.pop()))
				);
			}
		}

		return (int)stack.pop();
	}

	/**
	 * Construye un árbol de sintaxis abstracta a partir de una expresión en
	 * notación polaca reversa. Nótese que este proceso equivale a "devolver"
	 * el proceso de recorrido de un árbol binario en-orden (LNR).
	 */
	public static void buildExprGraphForTheLulz(String reversedPolishExpr) {

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

		List<String> expressions = getExpressionsFrom(file);

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
				"Debe proporcionar únicamente el nombre del archivo de " +
				"entrada como argumento"
			);
			System.exit(0);
		}
		importAndEval(args[0]);
	}
}
