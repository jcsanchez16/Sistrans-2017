/**-------------------------------------------------------------------
 * $Id$
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación
 *
 * Materia: Sistemas Transaccionales
 * Ejercicio: VideoAndes
 * Autor: Juan Felipe García - jf.garcia268@uniandes.edu.co
 * -------------------------------------------------------------------
 */
package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaEspectaculo {


	@JsonProperty(value="lista")
	private List<Espectaculo> lista;

	public ListaEspectaculo(@JsonProperty(value="lista") List<Espectaculo> lista) {
		super();
		this.lista = lista;
	}
 
	public List<Espectaculo> getLista() {
		return lista;
	}

	public void setLista(List<Espectaculo> lista) {
		this.lista = lista;
	} 

}
