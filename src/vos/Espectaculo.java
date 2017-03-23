package vos;

import java.util.ArrayList;

/**
 * Clase que crea las aerolineas asociadas a los aeropuertos de vuelandes
 * @author anaca
 *
 */
public class Espectaculo
{
	private int id;
	
	private String nombre;
	
	private int duracion;
	
	private ArrayList<String> requerimientos;
	
	private String idioma;
	
	private double costoProduccion;
	
	private String descripcion;
	
	private ArrayList<String> categorias;
	
	private boolean tipoParticipacion;
	
	private String traduccion;
	
	private String publico;
	
	private ArrayList<Funcion> funciones;
	
	private ArrayList<Integer> companias;
	
	public Espectaculo(int id, String nombre, int duracion, ArrayList<String> requerimientos, String idioma, double costoProduccion, String descripcion, 
			ArrayList<String> categorias, boolean tipoParticipacion, String traduccion, String publico, ArrayList<Funcion> funciones, ArrayList<Integer> companias)
	{
		this.id=id;
		this.nombre = nombre;
		this.duracion = duracion;
		this.requerimientos = requerimientos==null? new ArrayList<String>():requerimientos;
		this.idioma = idioma;
		this.costoProduccion = costoProduccion;
		this.descripcion = descripcion;
		this.categorias = categorias == null? new ArrayList<String>():categorias;
		this.tipoParticipacion = tipoParticipacion;
		this.traduccion = traduccion;
		this.publico = publico;
		this.funciones = funciones==null? new ArrayList<Funcion>():funciones;
		this.companias = companias == null? new ArrayList<Integer>():companias;
	}	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public ArrayList<String> getRequerimientos() {
		return requerimientos;
	}

	public void setRequerimientos(ArrayList<String> requerimientos) {
		this.requerimientos = requerimientos;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public double getCostoProduccion() {
		return costoProduccion;
	}

	public void setCostoProduccion(double costoProduccion) {
		this.costoProduccion = costoProduccion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public ArrayList<String> getCategorias() {
		return categorias;
	}

	public void setCategorias(ArrayList<String> categorias) {
		this.categorias = categorias;
	}

	public boolean isTipoParticipacion() {
		return tipoParticipacion;
	}

	public void setTipoParticipacion(boolean tipoParticipacion) {
		this.tipoParticipacion = tipoParticipacion;
	}

	public String getTraduccion() {
		return traduccion;
	}

	public void setTraduccion(String traduccion) {
		this.traduccion = traduccion;
	}

	public String getPublico() {
		return publico;
	}

	public void setPublico(String publico) {
		this.publico = publico;
	}

	public ArrayList<Funcion> getFunciones() {
		return funciones;
	}

	public void setFunciones(ArrayList<Funcion> funciones) {
		this.funciones = funciones;
	}

	public ArrayList<Integer> getCompanias() {
		return companias;
	}

	public void setCompanias(ArrayList<Integer> companias) {
		this.companias = companias;
	}

	public static void main(String[] args)
	{
		Espectaculo espe = new Espectaculo(1,"obra", 12, null, "espanol", 132, "bonita obra", null, true, "no", "infantil", null, null);
		System.out.println();
	}
	
}
