/**
 * Biblioteca de métodos de uso común.
 */
import java.io.File;

public class Utilidades {

	/**
	 * Verifica que el archivo exista
	 */
	public static boolean isValidPath(String filename) {
		File tmpFile = new File(filename);
		if (tmpFile.exists()) {
			return true;
		}
		return false;
	}
}