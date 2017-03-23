package vos;

import java.util.ArrayList;

import vos.Funcion;

public class Lugar 
{
	private int id;
	
	private String nombre;
	
	private boolean abierto;
	 
	private int capacidadVip;
	
	private int capacidadPlatea;
	
	private int capacidadGeneral;
	
	private boolean incapacitados;
	
	private int horaApertura;
	
	private int horaCierre;
	
	private ArrayList<String> condicionesTecnicas;
	
	private String tipoSilleteria;
	
	private boolean proteccionLluvia;
	
	private boolean numerada;
	
	private ArrayList<Funcion> funciones;

	public Lugar(int id, String nombre, boolean abierto, int capacidadVip, int capacidadGeneral, int capacidadPlatea, boolean incapacitados, int horaApertura,
			int horaCierre, ArrayList<String> condicionesTecnicas, String tipoSilleteria, boolean proteccionLluvia,
			boolean numerada, ArrayList<Funcion> funciones) 
	{
		this.id = id;
		this.nombre = nombre;
		this.abierto = abierto;
		this.capacidadVip = capacidadVip;
		this.capacidadPlatea = capacidadPlatea;
		this.capacidadGeneral = capacidadGeneral;
		this.incapacitados = incapacitados;
		this.horaApertura = horaApertura;
		this.horaCierre = horaCierre;
		this.condicionesTecnicas = condicionesTecnicas == null? new ArrayList<String>():condicionesTecnicas;
		this.tipoSilleteria = tipoSilleteria;
		this.proteccionLluvia = proteccionLluvia;
		this.numerada = numerada;
		this.funciones = funciones==null? new ArrayList<Funcion>():funciones;
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

	public boolean isAbierto() {
		return abierto;
	}

	public void setAbierto(boolean abierto) {
		this.abierto = abierto;
	}

	public int getCapacidadVip() {
		return capacidadVip;
	}

	public void setCapacidadVip(int capacidadVip) {
		this.capacidadVip = capacidadVip;
	}

	public int getCapacidadPlatea() {
		return capacidadPlatea;
	}

	public void setCapacidadPlatea(int capacidadPlatea) {
		this.capacidadPlatea = capacidadPlatea;
	}

	public int getCapacidadGeneral() {
		return capacidadGeneral;
	}

	public void setCapacidadGeneral(int capacidadGeneral) {
		this.capacidadGeneral = capacidadGeneral;
	}

	public boolean isIncapacitados() {
		return incapacitados;
	}

	public void setIncapacitados(boolean incapacitados) {
		this.incapacitados = incapacitados;
	}

	public int getHoraApertura() {
		return horaApertura;
	}

	public void setHoraApertura(int horaApertura) {
		this.horaApertura = horaApertura;
	}

	public int getHoraCierre() {
		return horaCierre;
	}

	public void setHoraCierre(int horaCierre) {
		this.horaCierre = horaCierre;
	}

	public ArrayList<String> getCondicionesTecnicas() {
		return condicionesTecnicas;
	}

	public void setCondicionesTecnicas(ArrayList<String> condicionesTecnicas) {
		this.condicionesTecnicas = condicionesTecnicas;
	}

	public String getTipoSilleteria() {
		return tipoSilleteria;
	}

	public void setTipoSilleteria(String tipoSilleteria) {
		this.tipoSilleteria = tipoSilleteria;
	}

	public boolean isProteccionLluvia() {
		return proteccionLluvia;
	}

	public void setProteccionLluvia(boolean proteccionLluvia) {
		this.proteccionLluvia = proteccionLluvia;
	}

	public boolean isNumerada() {
		return numerada;
	}

	public void setNumerada(boolean numerada) {
		this.numerada = numerada;
	}

	public ArrayList<Funcion> getFunciones() {
		return funciones;
	}

	public void setFunciones(ArrayList<Funcion> funciones) {
		this.funciones = funciones;
	}	

}
