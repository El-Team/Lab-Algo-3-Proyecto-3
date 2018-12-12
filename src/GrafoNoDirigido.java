/**
 * Este TAD es un subtipo de la inerfaz {@link Grafo}. Como su nombre lo indica,
 * representa a un grafo no dirigido.
 */
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.io.IOException;
import java.lang.Integer;
import java.util.NoSuchElementException;

public class GrafoNoDirigido implements Grafo {

	/**
	 * Número de vértices
	 */
	private int vertexCount;
	/**
	 * Número de aristas
	 */
	private int edgeCount;
	/**
	 * Diccionario que contiene a los vértices
	 */
	private LinkedHashMap<String, Vertice> vertices;
	/**
	 * Diccionario que contiene a las aristas
	 */
	private LinkedHashMap<String, Lado> edges;

	/**
	 * Crea un nuevo GrafoNoDirigido
	 */
	public GrafoNoDirigido() {
		this.vertexCount = 0;
		this.edgeCount = 0;
		this.vertices = new LinkedHashMap();
		this.edges = new LinkedHashMap();
	}

	/**
	 * Getters y setters
	 */
	public int getVertexCount() {
		return this.vertexCount;
	}
	public int getEdgeCount() {
		return this.edgeCount;
	}
	public LinkedHashMap<String, Vertice> getVertices() {
		return this.vertices;
	}
	public LinkedHashMap<String, Lado> getEdges() {
		return this.edges;
	}
	public void setVertexCount(int n) {
		this.vertexCount = n;
	}
	public void setEdgeCount(int m) {
		this.edgeCount = m;
	}
	public void setVertices(LinkedHashMap<String, Vertice> vertices) {
		this.vertices = vertices;
	}
	public void setEdges(LinkedHashMap<String, Lado> edges) {
		this.edges = edges;
	}

	/**
	 * {@inheritDoc}
	 */
	public int numeroDeVertices(Grafo g) {
		GrafoNoDirigido castedGraph = (GrafoNoDirigido)g;
		return castedGraph.getVertexCount();
	}

	/**
	 * {@inheritDoc}
	 */
	public int numeroDeLados(Grafo g) {
		GrafoNoDirigido castedGraph = (GrafoNoDirigido)g;
		return castedGraph.getEdgeCount();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean agregarVertice(Grafo g, Vertice v) {
		GrafoNoDirigido castedGraph = (GrafoNoDirigido)g;
		try {
			castedGraph.getVertices().put(v.getId(), v);
		}
		catch(Error e) {
			return false;
		}
		castedGraph.setVertexCount(castedGraph.getVertexCount() + 1);
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean agregarVertice(Grafo g, String id, String dato, int p) {
		GrafoNoDirigido castedGraph = (GrafoNoDirigido)g;
		if (castedGraph.estaVertice(this, id)) {
			return false;
		}
		try {
			Vertice v = new Vertice(id, dato, p);
			castedGraph.getVertices().put(id, v);
		}
		catch(Error e) {
			return false;
		}
		castedGraph.setVertexCount(castedGraph.getVertexCount() + 1);
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	public Vertice obtenerVertice(Grafo g, String id) throws NoSuchElementException {
		GrafoNoDirigido castedGraph = (GrafoNoDirigido)g;
		Vertice v = castedGraph.getVertices().get(id);
		if (v == null) {
			throw new NoSuchElementException();
		}
		return v;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean estaVertice(Grafo g, String id) {
		GrafoNoDirigido castedGraph = (GrafoNoDirigido)g;
		return castedGraph.getVertices().containsKey(id);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean estaLado(Grafo g, String u, String v) {
		GrafoNoDirigido castedGraph = (GrafoNoDirigido)g;
		for (String edgeId : castedGraph.getEdges().keySet()) {
			Arista castedEdge = (Arista)castedGraph.getEdges().get(edgeId);
			if (
				castedEdge.getExtremo1().getId().equals(u) &&
				castedEdge.getExtremo2().getId().equals(v)
			) {
				return true;
			}
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean eliminarVertice(Grafo g, String id) {
		GrafoNoDirigido castedGraph = (GrafoNoDirigido)g;
		if (castedGraph.getVertices().remove(id) != null) {
			castedGraph.setVertexCount(castedGraph.getVertexCount() - 1);
			return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public ArrayList<Vertice> vertices(Grafo g) {
		GrafoNoDirigido castedGraph = (GrafoNoDirigido)g;
		ArrayList<Vertice> vertices = new ArrayList();
		for (String vertexId : castedGraph.getVertices().keySet()) {
			vertices.add((Vertice)(castedGraph.getVertices().get(vertexId)));
		}
		return vertices;
	}

	/**
	 * {@inheritDoc}
	 */
	public ArrayList<Lado> lados(Grafo g) {
		GrafoNoDirigido castedGraph = (GrafoNoDirigido)g;
		ArrayList<Lado> lados = new ArrayList();
		for (String edgeId : castedGraph.getEdges().keySet()) {
			lados.add((Lado)(castedGraph.getEdges().get(edgeId)));
		}
		return lados;
	}

	/**
	 * {@inheritDoc}
	 */
	public int grado(Grafo g, String id) throws NoSuchElementException {
		GrafoNoDirigido castedGraph = (GrafoNoDirigido)g;
		int grado = 0;
		if (!g.estaVertice(this, id)) {
			throw new NoSuchElementException();
		}
		for (String edgeId : castedGraph.getEdges().keySet()) {
			Arista castedEdge = (Arista)castedGraph.getEdges().get(edgeId);
			if (
				castedEdge.getExtremo1().getId().equals(id) ||
				castedEdge.getExtremo2().getId().equals(id)
			) {
				grado++;
			}
			// Caso bucles
			if (
				castedEdge.getExtremo1().getId().equals(id) &&
				castedEdge.getExtremo2().getId().equals(id)
			) {
				grado++;
			}
		}
		return grado;
	}

	/**
	 * {@inheritDoc}
	 */
	public ArrayList<Vertice> adyacentes(Grafo g, String id) throws NoSuchElementException {
		GrafoNoDirigido castedGraph = (GrafoNoDirigido)g;
		ArrayList<Vertice> adyacentes = new ArrayList();
		if (!g.estaVertice(this, id)) {
			throw new NoSuchElementException();
		}
		for (String edgeId : castedGraph.getEdges().keySet()) {
			Arista castedEdge = (Arista)castedGraph.getEdges().get(edgeId);
			if (castedEdge.getExtremo1().getId().equals(id)) {
				adyacentes.add(castedEdge.getExtremo2());
			}
			if (castedEdge.getExtremo2().getId().equals(id)) {
				adyacentes.add(castedEdge.getExtremo1());
			}
		}
		return adyacentes;
	}

	/**
	 * {@inheritDoc}
	 */
	public ArrayList<Lado> incidentes(Grafo g, String id) throws NoSuchElementException {
		GrafoNoDirigido castedGraph = (GrafoNoDirigido)g;
		ArrayList<Lado> incidentes = new ArrayList();
		if (!g.estaVertice(this, id)) {
			throw new NoSuchElementException();
		}
		for (String edgeId : castedGraph.getEdges().keySet()) {
			Arista castedEdge = (Arista)castedGraph.getEdges().get(edgeId);
			if (
				castedEdge.getExtremo1().getId().equals(id) ||
				castedEdge.getExtremo2().getId().equals(id)
			) {
				incidentes.add(castedEdge);
			}
		}
		return incidentes;
	}

	/**
	 * {@inheritDoc}
	 */
	public Grafo clone(Grafo g) {

		// Se preparan los datos de entrada

		GrafoNoDirigido castedGraph = (GrafoNoDirigido)g;

		ArrayList<Vertice> vertices = castedGraph.vertices(this);
		LinkedHashMap<String, Vertice> importedVertices =
			new LinkedHashMap<String, Vertice>();
		for (Vertice v : vertices) {
			importedVertices.put(v.getId(), v);
		}

		ArrayList<Lado> lados = castedGraph.lados(this);
		LinkedHashMap<String, Lado> importedEdges =
			new LinkedHashMap<String, Lado>();
		for (Lado l : lados) {
			importedEdges.put(l.getId(), l);
		}


		// Se asignan los datos al clon

		GrafoNoDirigido newGraph = new GrafoNoDirigido();
		newGraph.setVertexCount(castedGraph.numeroDeVertices(this));
		newGraph.setEdgeCount(castedGraph.numeroDeVertices(this));
		newGraph.setVertices(importedVertices);
		newGraph.setEdges(importedEdges);


		return newGraph;
	};

	/**
	 * {@inheritDoc}
	 */
	public String toString(Grafo g) {
		GrafoNoDirigido castedGraph = (GrafoNoDirigido)g;
		String graphStr =
			"Número de vértices: " + castedGraph.numeroDeVertices(this) + "\n" +
			"Número de lados: " + castedGraph.numeroDeLados(this) + "\n" +
			"Vertices:\n" + castedGraph.vertices(this).toString() + "\n" +
			"Lados:\n" + castedGraph.lados(this).toString();

		return graphStr;
	}

	/**
	 * Agrega una nueva arista al grafo si el identificador de la arista no lo posee ninguna arista en el grafo.
	 * Retorna true en caso en que la inserción se lleve a cabo, false en contrario.
	 */
	public boolean agregarArista(Grafo g, Arista a) {
		GrafoNoDirigido castedGraph = (GrafoNoDirigido)g;
		for (Lado l : castedGraph.lados(this)) {
			Arista castedEdge = (Arista)l;
			if (castedEdge.getId() == a.getId()) {
				return false;
			}
		}
		try {
			castedGraph.getEdges().put(a.getId(), a);
		}
		catch(Error e) {
			return false;
		}
		castedGraph.setEdgeCount(castedGraph.getEdgeCount() + 1);
		return true;
	}

	/**
	 * Si el identificador id no lo posee ninguna arista en el grafo, crea una nueva arista y la agrega en el
	 * grafo. Retorna true en caso en que la inserción se lleve a cabo, false en contrario.
	 */
	public boolean agregarArista(
			Grafo g,
			String id,
			String dato,
			int p,
			String u,
			String v
		) {

		GrafoNoDirigido castedGraph = (GrafoNoDirigido)g;
		try {
			if (
				!g.estaVertice(this, u) ||
				!g.estaVertice(this, v)
			) {
				throw new NoSuchElementException();
			}
			Vertice vi = g.obtenerVertice(this, u);
			Vertice vf = g.obtenerVertice(this, v);
			Arista a = new Arista(id, dato, p, vi, vf);
			castedGraph.getEdges().put(a.getId(), a);
		}
		catch(Error e) {
			return false;
		}
		castedGraph.setEdgeCount(castedGraph.getEdgeCount() + 1);
		return true;
	}

	/**
	 * Elimina la arista en el grafo que esté identificada con id. Se retorna true en caso que se haya
	 * eliminado la arista del grafo y false en caso de que no exista una arista con ese identificador en el
	 * grafo.
	 */
	public boolean eliminarArista(Grafo g, String id) {
		GrafoNoDirigido castedGraph = (GrafoNoDirigido)g;
		if (castedGraph.getEdges().remove(id) != null) {
			castedGraph.setEdgeCount(castedGraph.getEdgeCount() - 1);
			return true;
		}
		return false;
	}

	/**
	 * Devuelve la arista que tiene como identificador id. En caso de que no exista ninguna arista con ese
	 * identificador, se lanza la excepción NoSuchElementException.
	 */
	public Arista obtenerArista(Grafo g, String id) throws NoSuchElementException {
		GrafoNoDirigido castedGraph = (GrafoNoDirigido)g;
		Arista a = (Arista)castedGraph.getEdges().get(id);
		if (a == null) {
			throw new NoSuchElementException();
		}
		return a;
	}
}
