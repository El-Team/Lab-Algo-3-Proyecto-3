/**
* Clase <code>Lado</code> representa un tipo de dato que posee
* el {@link Grafo}. Se identifica con {@link id} y su informacion
* principal se guarda en {@link dato}. Tambien tienen un {@link peso}
* asociado a ellos
*/

public abstract class Lado {

	/** Identificador asociado al lado **/
	protected String id;
	/** Dato que se quiere almacenar en el lado **/
	protected String dato;
	/** Peso asociado al lado**/
	protected int peso;

	/** Constructor del TAD lado: 
	* 		@param id 	representa el identificador
	* 		@param dato representa el dato a almacenar
	* 		@param p 	representa el peso del {@link Lado}
	*/
	public Lado(String id, String dato, int p) {
		this.id = id;
		this.dato = dato;
		this.peso = p;
	}

	/** Funcion para obtener el peso del Lado
	* 		@return 	Peso asociado al Lado
	*/
	public int getPeso() {
		return this.peso;
	}

	/** Funcion para obtener el identificador del Lado
	* 		@return 	Identificador del Lado
	*/
	public String getId() {
		return this.id;
	}

	/** Funcion para obtener el dato del Lado
	* 		@return 	dato almacenado en el Lado
	*/
	public String getDato() {
		return this.dato;
	}

	/** Funcion para obtener toda la informacion del Lado
	* 		@return 	<code>String</code> con toda la informacion del Lado
	*/
	public abstract String toString();

}