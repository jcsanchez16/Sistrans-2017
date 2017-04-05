package vos;

public class Usuario {
	
	public final static String CLIENTE = "Cliente";
	
	public final static String REPRESENTANTE = "Representante";
	
	private String nombre;
	
	private int id;
	
	private String correo;
	
	private String rol;

	public Usuario(String nombre, int id, String correo, int rol) 
	{
		this.nombre = nombre;
		this.id = id;
		this.correo = correo;
		this.rol = rol ==1 ? CLIENTE : REPRESENTANTE;		
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

}
