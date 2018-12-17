import java.util.Stack;
import java.lang.StringBuilder;
import java.util.HashMap;
import java.lang.Character;

public class ShuntingYard {
	
	private enum Operator {

		ADD(1), SUBTRACT(2), MULTIPLY(3), DIVIDE(4), MAX(5), MIN(6), SUM(7);
		final int precedence;

		Operator(int p) {
			precedence = p;
		}
	}

	private static HashMap<String, Operator> simpleOperators =
		new HashMap<String, Operator>() {
			{
				put("+", Operator.ADD);
				put("-", Operator.SUBTRACT);
				put("*", Operator.MULTIPLY);
				put("/", Operator.DIVIDE);
			}
		};

	private static HashMap<String, Operator> operators =
		new HashMap<String, Operator>() {
			{
				put("+", Operator.ADD);
				put("-", Operator.SUBTRACT);
				put("*", Operator.MULTIPLY);
				put("/", Operator.DIVIDE);
				put("M", Operator.MAX);
				put("N", Operator.MIN);
				put("S", Operator.SUM);
			}
		};

	/**
	 * Determina si la precedencia del operador entrante es mayor o igual a la
	 * del operador que se encuentra a la cabeza de la pila.
	 */
	private static boolean hasHigherOrEqualPrec(String incoming, String stackTop) {
		return (
			operators.containsKey(incoming) &&
			operators.get(incoming).precedence >= operators.get(stackTop).precedence
		);
	}

	/**
	 * Determina si la precedencia del operador entrante es menor o igual a la
	 * del operador que se encuentra a la cabeza de la pila.
	 */
	private static boolean hasLowerOrEqualPrec(String incoming, String stackTop) {
		return (
			operators.containsKey(incoming) &&
			operators.get(incoming).precedence <= operators.get(stackTop).precedence
		);
	}

	/**
	 * Aplica el algoritmo shunting-yard a la expresión dada para retornar la
	 * misma expresión en notación polaca reversa.
	 */
	public static String toPostfix(String expr) {

		StringBuilder out = new StringBuilder();
		Stack stack = new Stack();
		char[] tokens = expr.toCharArray();
		String token  = "";

		for (int i = 0; i < expr.length(); i++) {

			token = Character.toString(tokens[i]);

			// Paréntesis asociativo izquierdo
			if (token.equals("(")) {
				stack.push(token);
			}
			// Paréntesis asociativo derecho
			else if (token.equals(")")) {
				while (!stack.peek().equals("(")) {
					out.append(stack.pop());
				}
				stack.pop();
			}
			// Algún operador simple (+, -, *, /)
			else if (simpleOperators.containsKey(token)) {
				// If the incoming symbol is an operator and the stack is empty
				//   or contains a left parenthesis on top, push the incoming
				//   operator onto the stack.
				if (stack.empty() || stack.peek().equals("(")) {
					stack.push(token);
				}
				// If the incoming symbol is an operator and has either higher
				//   precedence than the operator on the top of the stack, or
				//   has the same precedence as the operator on the top of the
				//   stack and is right associative -- push it on the stack.
				else if (hasHigherOrEqualPrec(token, (String)stack.peek())) {
					stack.push(token);
				}
				// If the incoming symbol is an operator and has either lower
				//   precedence than the operator on the top of the stack, or
				//   has the same precedence as the operator on the top of the
				//   stack and is left associative -- continue to pop the stack
				//   until this is not true. Then, push the incoming operator.
				else {
					while (
						hasLowerOrEqualPrec(token, (String)stack.peek())
					) {
						out.append(stack.pop());
						out.append(" ");
						if (stack.empty()) {
							break;
						}
					}
					stack.push(token);
				}
			}
			// Funciones (SUM(), MIN(), MAX())
			else if (token.equals("S")) {

				stack.push(token);

				// Posicionar al inicio del argumento de SUM para parsearlo
				i = i + 4;
				StringBuilder sumArg = new StringBuilder();
				String sumArgToken = null;
				while (!sumArgToken.equals(")")) {
					sumArgToken = Character.toString(tokens[i]);
					sumArg.append(sumArgToken);
					i++;
				}

				// Agregar el argumento parseado a la salida y posicionar luego
				// de la llamada a SUM para seguir parseando
				out.append(toPostfix(sumArg.toString()));
				i++;
			}
			else if (token.equals("M")) {

				if (Character.toString(tokens[i+1]).equals("A")) {
					stack.push("M"); // MAX
				}
				else {
					stack.push("N"); // MIN
				}

				// Posicionar al inicio del primer argumento de MAX/MIN para
				// parsearlo
				i = i + 4;
				StringBuilder arg1 = new StringBuilder();
				String arg1Token = Character.toString(tokens[i]);
				while (!arg1Token.equals(",")) {
					arg1Token = Character.toString(tokens[i]);
					arg1.append(arg1Token);
					i++;
				}
				
				// Agregar el argumento parseado a la salida
				out.append(toPostfix(arg1.toString().substring(
					0, arg1.toString().length()-1
				)));

				// Posicionar al inicio del segundo argumento de MAX/MIN para
				// parsearlo
				StringBuilder arg2 = new StringBuilder();
				String arg2Token = Character.toString(tokens[i]);

				while (!arg2Token.equals(")")) {
					arg2Token = Character.toString(tokens[i]);
					arg2.append(arg2Token);
					i++;
				}
				
				// Agregar el argumento parseado a la salida y posicionar luego
				// de la llamada a MAX/MIN para seguir parseando
				out.append(toPostfix(arg2.toString().substring(
					0, arg2.toString().length()-1
				)));
				i++;
			}
			// Operandos enteros
			else {
				StringBuilder operand = new StringBuilder(token);
				while (
					i + 1 < expr.length() &&
					!operators.containsKey(Character.toString(tokens[i+1]))
				) {
					operand.append(tokens[i+1]);
					i++;
				}

				out.append(operand);
				out.append(" ");
			}
		}

		// Agregar los operadores que quedan en la pila a la salida
		while (!stack.empty()) {
			out.append(stack.pop());
			out.append(" ");
		}

		return out.toString();
	}
}
