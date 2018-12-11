import java.util.Stack;
import java.lang.StringBuilder;
import java.util.HashMap;
import java.lang.Character;

public class ShuntingYard {
	
	private enum Operator {

		ADD(1), SUBTRACT(2), MULTIPLY(3), DIVIDE(4);
		final int precedence;

		Operator(int p) {
			precedence = p;
		}
	}

	private static HashMap<String, Operator> operators =
		new HashMap<String, Operator>() {
			{
				put("+", Operator.ADD);
				put("-", Operator.SUBTRACT);
				put("*", Operator.MULTIPLY);
				put("/", Operator.DIVIDE);
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
	 * Detecta si la expresión contiene llamadas a min(), max() y a sum()
	 */
	private static boolean hasBuiltInFunctionIn(String expr) {
		return false;
	}

	/**
	 * Aplica el algoritmo shunting-yard a la expresión dada para retornar la
	 * misma expresión en notación polaca reversa.
	 */
	public static String toPostfix(String expr) {
		StringBuilder out = new StringBuilder();
		Stack stack = new Stack();

		if (hasBuiltInFunctionIn(expr)) {

		}
		else {
			char[] tokens = expr.toCharArray();
			String token  = "";

			for (int i = 0; i < expr.length(); i++) {

				token = Character.toString(tokens[i]);

				// If the incoming symbol is a left parenthesis, push it on the
				// stack.
				if (token.equals("(")) {
					stack.push(token);
				}
				// If the incoming symbol is a right parenthesis: discard the
				// right parenthesis, pop and print the stack symbols until you
				// see a left parenthesis. Pop the left parenthesis and discard it.
				else if (token.equals(")")) {
					while (!stack.peek().equals("(")) {
						out.append(stack.pop());
					}
					stack.pop();
				}
				else if (operators.containsKey(token)) {
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
						while (hasLowerOrEqualPrec(token, (String)stack.peek())) {
							stack.pop();
						}
						stack.push(token);
					}
				}
				// If the incoming symbols is an operand, print it.
				else {
					out.append(token);
				}
			}

			// At the end of the expression, pop and print all operators on the
			// stack. (No parentheses should remain.)
			while (!stack.empty()) {
				out.append(stack.pop());
			}
		}

		return out.toString();
	}
}