package vos;

import java.util.ArrayList;

public class Cliente extends Usuario
{
	private int edad;
	
	private String preferenciaGenero;
	
	private int preferenciaSitio;
	
	private ArrayList<Boleta> boletas;

	public Cliente(String nombre, int id, String correo, int rol, int edad,
			String preferenciaGenero, int preferenciaSitio, ArrayList<Boleta> boletas) {
		super(nombre, id, correo, rol);
		this.edad = edad;
		this.preferenciaGenero = preferenciaGenero;
		this.preferenciaSitio = preferenciaSitio;
		this.boletas = boletas;
	}

	public ArrayList<Boleta> getBoletas() {
		return boletas;
	}

	public void setBoletas(ArrayList<Boleta> boletas) {
		this.boletas = boletas;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getPreferenciaGenero() {
		return preferenciaGenero;
	}

	public void setPreferenciaGenero(String preferenciaGenero) {
		this.preferenciaGenero = preferenciaGenero;
	}

	public int getPreferenciaSitio() {
		return preferenciaSitio;
	}

	public void setPreferenciaSitio(int preferenciaSitio) {
		this.preferenciaSitio = preferenciaSitio;
	}

}
