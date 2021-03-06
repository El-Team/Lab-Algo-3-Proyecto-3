/**
* Subtipo del TAD {@link Lado} que representa los Lados que componen
* al TAD {@link GrafoNoDirigido}.	
*/

public class Arista extends Lado {

	/** Vertice extremo 1 del Arista**/
	private Vertice u;
	/** Vertice extremo 2 del Arista**/
	private Vertice v;

	/** Constructor del TAD Arista: 
	* 		@param id 	representa el identificador
	* 		@param dato representa el dato a almacenar
	* 		@param p 	representa el peso del {@link Arista}
	* 		@param u 	{@link Vertice} extremo 1
	* 		@param v 	{@link Vertice} extremo 2
	*/
	public Arista(String id, String dato, int p, Vertice u, Vertice v) {
		super(id, dato, p);
		this.u = u;
		this.v = v;
	}

	/** Obtiene el {@link Vertice} del primer extremo del Arista
	* 		@return 	Vertice del primer extremo
	*/
	public Vertice getExtremo1() {
		return this.u;
	}

	/** Obtiene el {@link Vertice} del segundo extermo del Arista
	* 		@return 	Vertice del segundo extremo
	*/
	public Vertice getExtremo2() {
		return this.v;
	}

	/** {@inheritDoc} **/
	@Override
	public String toString() {
		return "Arista \"" + this.id + "\":\n" + 
			"Tipo de dato:	" + this.dato.getClass().getSimpleName() + "\n" +
			"Dato:	" + this.dato + "\n" +
			"Peso:	" + this.peso + "\n" +
			"Vertice 1:	" + this.u.toString() + "\n" +
			"Vertice 2:	" + this.v.toString() + "\n";
	}
}