package vos;

import java.util.ArrayList;
import java.util.Date;

public class Funcion 
{
	private Date fecha;
	
	private boolean realizada;
	
	private String festival;
	
	private int lugar;
	
	private int espectaculo;
	
	private ArrayList<Integer> clientes;

	public Funcion(Date fecha, boolean realizada, String festival, int lugar, int espectaculo,
			ArrayList<Integer> clientes) {
		super();
		this.fecha = fecha;
		this.realizada = realizada;
		this.festival = festival;
		this.lugar = lugar;
		this.espectaculo = espectaculo;
		this.clientes = clientes;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public boolean isRealizada() {
		return realizada;
	}

	public void setRealizada(boolean realizada) {
		this.realizada = realizada;
	}

	public String getFestival() {
		return festival;
	}

	public void setFestival(String festival) {
		this.festival = festival;
	}

	public int getLugar() {
		return lugar;
	}

	public void setLugar(int lugar) {
		this.lugar = lugar;
	}

	public int getEspectaculo() {
		return espectaculo;
	}

	public void setEspectaculo(int espectaculo) {
		this.espectaculo = espectaculo;
	}

	public ArrayList<Integer> getClientes() {
		return clientes;
	}

	public void setClientes(ArrayList<Integer> clientes) {
		this.clientes = clientes;
	}

}
