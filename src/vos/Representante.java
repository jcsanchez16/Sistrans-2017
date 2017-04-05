package vos;

import java.util.ArrayList;

public class Representante extends Usuario
{
	private int companiaTeatro;

	public Representante(String nombre, int id, String correo, int rol, int companiaTeatro) {
		super(nombre, id, correo, rol );
		this.companiaTeatro = companiaTeatro;
	}

	public int getCompaniaTeatro() {
		return companiaTeatro;
	}

	public void setCompaniaTeatro(int companiaTeatro) {
		this.companiaTeatro = companiaTeatro;
	}

	
}
