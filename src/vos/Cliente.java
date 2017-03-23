package vos;

public class Cliente extends Usuario
{
	private int edad;
	
	private String preferenciaGenero;
	
	private int preferenciaSitio;

	public Cliente(String nombre, int id, String correo, String rol, int companiaTeatro, int edad,
			String preferenciaGenero, int preferenciaSitio) {
		super(nombre, id, correo, rol, companiaTeatro);
		this.edad = edad;
		this.preferenciaGenero = preferenciaGenero;
		this.preferenciaSitio = preferenciaSitio;
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
