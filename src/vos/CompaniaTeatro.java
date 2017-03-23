package vos;

import java.util.ArrayList;
import java.util.Date;

public class CompaniaTeatro 
{
	private int id;
	
	private String nombre;
	
	private String pais;
	
	private String paginaWeb;
	
	private Date fechaLlegada;
	
	private Date fechaSalida;
	
	private Usuario representante;
	
	private ArrayList<Espectaculo> espectaculos;

	public CompaniaTeatro(int id, String nombre, String pais, String paginaWeb, Date fechaLlegada, Date fechaSalida,
			Usuario representante, ArrayList<Espectaculo> espectaculos) 
	{
		this.id = id;
		this.nombre = nombre;
		this.pais = pais;
		this.paginaWeb = paginaWeb;
		this.fechaLlegada = fechaLlegada;
		this.fechaSalida = fechaSalida;
		this.representante = representante;
		this.espectaculos = espectaculos == null? new ArrayList<Espectaculo>():espectaculos;
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

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getPaginaWeb() {
		return paginaWeb;
	}

	public void setPaginaWeb(String paginaWeb) {
		this.paginaWeb = paginaWeb;
	}

	public Date getFechaLlegada() {
		return fechaLlegada;
	}

	public void setFechaLlegada(Date fechaLlegada) {
		this.fechaLlegada = fechaLlegada;
	}

	public Date getFechaSalida() {
		return fechaSalida;
	}

	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public Usuario getRepresentante() {
		return representante;
	}

	public void setRepresentante(Usuario representante) {
		this.representante = representante;
	}

	public ArrayList<Espectaculo> getEspectaculos() {
		return espectaculos;
	}

	public void setEspectaculos(ArrayList<Espectaculo> espectaculos) {
		this.espectaculos = espectaculos;
	}

}
