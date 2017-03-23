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
	
	private ArrayList<Boleta> boletas;
	
	private double precioVip;
	
	private double precioPlatea;
	
	private double precioGeneral;

	public Funcion(Date fecha, boolean realizada, String festival, int lugar, int espectaculo,
			ArrayList<Boleta> boletas, double precioVip, double precioPlatea, double precioGeneral) 
	{
		this.fecha = fecha;
		this.realizada = realizada;
		this.festival = festival;
		this.lugar = lugar;
		this.espectaculo = espectaculo;
		this.boletas = boletas;
		this.precioVip =precioVip;
		this.precioPlatea = precioPlatea;
		this.precioGeneral = precioGeneral;
	}

	public double getPrecioVip() {
		return precioVip;
	}

	public void setPrecioVip(double precioVip) {
		this.precioVip = precioVip;
	}

	public double getPrecioPlatea() {
		return precioPlatea;
	}

	public void setPrecioPlatea(double precioPlatea) {
		this.precioPlatea = precioPlatea;
	}

	public double getPrecioGeneral() {
		return precioGeneral;
	}

	public void setPrecioGeneral(double precioGeneral) {
		this.precioGeneral = precioGeneral;
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

	public ArrayList<Boleta> getClientes() {
		return boletas;
	}

	public void setClientes(ArrayList<Boleta> boletas) {
		this.boletas = boletas;
	}

}
