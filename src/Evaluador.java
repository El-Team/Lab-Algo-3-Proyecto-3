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
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.ClassCastException;
import java.lang.StringBuilder;

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
	private static final String operators = "+-*/SMN";

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
	 * Ejecuta una operación binaria "op", usando como argumentos las últimas
	 * dos entradas numéricas en "stack" y luego almacena el resultado en la
	 * misma pila.
	 */
	private static void evaluateBinaryOperation(String op, Stack stack) {

		stack.pop();
		Integer arg2 = null;
		Integer arg1 = null;
		
		try {
			arg2 = Integer.parseInt((String)stack.peek());
		}
		catch (ClassCastException e) {
			arg2 = (int)stack.peek();
		}
		stack.pop();

		try {
			arg1 = Integer.parseInt((String)stack.peek());
		}
		catch (ClassCastException e) {
			arg1 = (int)stack.peek();
		}
		stack.pop();

		switch (op) {
			case "+": 
				stack.push(arg1 + arg2);
				break;
			case "-": 
				stack.push(arg1 - arg2);
				break;
			case "*": 
				stack.push(arg1 * arg2);
				break;
			case "/": 
				stack.push(arg1 / arg2);
				break;
		}
	}


	/**
	 * Evalua una expresión en notación polaca reversa.
	 */
	public static int eval(String reversedPolishExpr) {

		Integer result = null;
		Stack<String>  stack  = new Stack<String>();
		char[] tokens = reversedPolishExpr.toCharArray();
		String token  = null;

		System.out.println("----------------------------------");
		System.out.println(reversedPolishExpr);
		System.out.println("----------------------------------");

		for (int i = 0; i < reversedPolishExpr.length(); i++) {

			token = Character.toString(tokens[i]);

			if (!token.equals(" ")) {

				if (operators.indexOf(token) != -1) {

					stack.push(token);
					String op = (String)stack.peek();

					switch (op) {
						case "+":
							evaluateBinaryOperation(op, stack);
							break;
						case "-":
							evaluateBinaryOperation(op, stack);
							break;
						case "*":
							evaluateBinaryOperation(op, stack);
							break;
						case "/":
							evaluateBinaryOperation(op, stack);
							break;
						case "S":
							stack.pop();
							//stack.push(Integer.toString(
							//	sum((int)stack.pop()))
							//);
							break;
						case "M":
							stack.pop();
							//stack.push(Integer.toString(
							//	max((int)stack.pop(), (int)stack.pop()))
							//);
							break;
						case "N":
							stack.pop();
							//stack.push(Integer.toString(
							//	min((int)stack.pop(), (int)stack.pop()))
							//);
							break;
					}
				}
				else {

					StringBuilder operand = new StringBuilder(token);
					while (
						i + 1 < reversedPolishExpr.length() &&
						operators.indexOf(reversedPolishExpr.substring(i+1, i+2)) == -1 &&
						!reversedPolishExpr.substring(i+1, i+2).equals(" ")
					) {
						operand.append(Character.toString(tokens[i+1]));
						i++;
					}

					stack.push(operand.toString());
				}		
			}
		}

		try {
			result = Integer.parseInt((String)stack.peek());
		}
		catch (ClassCastException e) {
			Object o = stack.peek();
			result = (int)o;
		}

		return result;
	}

	/**
	 * Construye un árbol de sintaxis abstracta para una operación binaria y lo
	 * agrega al grafo que se pasa como argumento.
	 * <br><br>
	 * Notas:<br>
	 * 1. El arreglo se recorre en reversa porque al desempilar, los operandos
	 * se obtienen en orden inverso.
	 */
	private static void addBinaryOperationSubtreeTo(
		GrafoNoDirigido graph,
		HashMap<String, Integer> counters,
		Stack stack
	) {
		String operationId = "v" + Integer.toString(counters.get("vCounter"));
		ArrayList<String> operationArgs = new ArrayList(2);

		// Nodo padre (el operador)
		graph.agregarVertice(
			graph,
			operationId,
			(String)stack.pop(),
			0
		);
		counters.put("vCounter", counters.get("vCounter") + 1);
		
		// Nodos hijos (los operandos)
		for (int i = 0; i < 2; i++) {
			if (!((String)stack.peek()).startsWith("v")) {
				graph.agregarVertice(
					graph,
					"v" + Integer.toString(counters.get("vCounter")),
					(String)stack.pop(),
					0
				);
				operationArgs.add(
					i,
					"v" + Integer.toString(counters.get("vCounter"))
				);
				counters.put("vCounter", counters.get("vCounter") + 1);
			}
			else {
				operationArgs.add(
					i,
					(String)stack.pop()
				);
			}
		}

		// Aristas
		for (int j = 2; j > 0; j--) { // Nota 1
			graph.agregarArista(
				graph,
				"e" + Integer.toString(counters.get("eCounter")),
				"",
				0,
				operationId,
				operationArgs.get(j)
			);
			counters.put("eCounter", counters.get("eCounter") + 1);
		}

		// Actualizar pila
		stack.push(operationId);
	}

	/**
	 * Construye un árbol de sintaxis abstracta para una operación unaria y lo
	 * agrega al grafo que se pasa como argumento.
	 */
	private static void addUnaryOperationSubtreeTo(
		GrafoNoDirigido graph,
		HashMap<String, Integer> counters,
		Stack stack
	) {
		String operationId = "v" + Integer.toString(counters.get("vCounter"));
		String operationArg = null;

		// Nodo padre (el operador)
		graph.agregarVertice(
			graph,
			operationId,
			(String)stack.pop(),
			0
		);
		counters.put("vCounter", counters.get("vCounter") + 1);
		
		// Nodo hijo (el operando)
		if (!((String)stack.peek()).startsWith("v")) {
			graph.agregarVertice(
				graph,
				"v" + Integer.toString(counters.get("vCounter")),
				(String)stack.pop(),
				0
			);
			operationArg = "v" + Integer.toString(counters.get("vCounter"));
			counters.put("vCounter", counters.get("vCounter") + 1);
		}
		else {
			operationArg = (String)stack.pop();
		}

		// Aristas
		graph.agregarArista(
			graph,
			"e" + Integer.toString(counters.get("eCounter")),
			"",
			0,
			operationId,
			operationArg
		);
		counters.put("eCounter", counters.get("eCounter") + 1);

		// Actualizar pila
		stack.push(operationId);
	}

	/**
	 * Construye un árbol de sintaxis abstracta a partir de una expresión en
	 * notación polaca reversa.
	 */
	public static void buildExprGraphForTheLulz(String reversedPolishExpr) {
		GrafoNoDirigido graph = new GrafoNoDirigido();
		Stack stack  = new Stack();
		char[] tokens = reversedPolishExpr.toCharArray();
		String token = null;
		HashMap<String, Integer> counters = new HashMap<String, Integer>() {
			{
				put("vCounter", 0);
				put("eCounter", 0);
			}
		};

		for (int i = 0; i < reversedPolishExpr.length(); i++) {
			token = Character.toString(tokens[i]);
			stack.push(token);
			if (
				stack.peek().equals("+") ||
				stack.peek().equals("-") ||
				stack.peek().equals("*") ||
				stack.peek().equals("/") ||
				stack.peek().equals("M") ||
				stack.peek().equals("N")
			) {
				addBinaryOperationSubtreeTo(graph, counters, stack);
			}
			else if (stack.peek().equals("S")) {
				addUnaryOperationSubtreeTo(graph, counters, stack);
			}
		}
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
			System.out.println("Expression: " + reversedPolishExpr);
			result = eval(reversedPolishExpr);
			//buildExprGraphForTheLulz(reversedPolishExpr);
			System.out.print("Result: " + result);
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
