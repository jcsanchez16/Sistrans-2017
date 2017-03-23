package vos;

import java.util.Date;

public class Boleta 
{
	private int usuario;
	
	private int espectaculo;
	
	private Date fecha;
	
	private String localidad;

	public Boleta(int usuario, int espectaculo, Date fecha, String localidad) {
		super();
		this.usuario = usuario;
		this.espectaculo = espectaculo;
		this.fecha = fecha;
		this.localidad = localidad;
	}

	public int getUsuario() {
		return usuario;
	}

	public void setUsuario(int usuario) {
		this.usuario = usuario;
	}

	public int getEspectaculo() {
		return espectaculo;
	}

	public void setEspectaculo(int espectaculo) {
		this.espectaculo = espectaculo;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

}
