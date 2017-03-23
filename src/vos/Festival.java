package vos;

import java.util.ArrayList;
import java.util.Date;

public class Festival 
{
	private String nombre;
	
	private Date fechaInicio;
	
	private Date fechaFinal;
	
	private ArrayList<Funcion> funciones;

	public Festival(String nombre, Date fechaInicio, Date fechaFinal, ArrayList<Funcion> funciones) {
		super();
		this.nombre = nombre;
		this.fechaInicio = fechaInicio;
		this.fechaFinal = fechaFinal;
		this.funciones = funciones==null? new ArrayList<Funcion>():funciones;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public ArrayList<Funcion> getFunciones() {
		return funciones;
	}

	public void setFunciones(ArrayList<Funcion> funciones) {
		this.funciones = funciones;
	}

}
