package vos;

public class Usuario {
	
	public final static String CLIENTE = "Cliente";
	
	public final static String REPRESENTANTE = "Representante";
	
	private String nombre;
	
	private int id;
	
	private String correo;
	
	private String rol;
	
	private int companiaTeatro;

	public Usuario(String nombre, int id, String correo, String rol, int companiaTeatro) 
	{
		this.nombre = nombre;
		this.id = id;
		this.correo = correo;
		this.rol = rol;		
		this.companiaTeatro = rol.equals(REPRESENTANTE)?companiaTeatro:null;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public int getCompaniaTeatro() {
		return companiaTeatro;
	}

	public void setCompaniaTeatro(int companiaTeatro) {
		this.companiaTeatro = companiaTeatro;
	}

}
