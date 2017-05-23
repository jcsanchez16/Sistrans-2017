package dao;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import vos.Boleta;
import vos.Cliente;
import vos.Funcion;
import vos.Lugar;
import vos.Representante;
import vos.Usuario;

public class DAOUsuarios {

	private Connection conexion;

	private String user;

	private String password;

	private String url;

	private String driver;

	private String conectionData;

	private DAOBoleta boletas;

	private DAOCompanias companias;

	public DAOUsuarios(String conectionDat) {
		initConnectionData(conectionDat);
		conectionData = conectionDat;
		boletas = new DAOBoleta(conectionData);
		companias = new DAOCompanias(conectionData);
	}

	private void initConnectionData(String conectionData) {
		try {
			File arch = new File(conectionData);
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream(arch);
			prop.load(in);
			in.close();
			this.url = prop.getProperty("url");
			this.user = prop.getProperty("usuario");
			this.password = prop.getProperty("clave");
			this.driver = prop.getProperty("driver");
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void establecerConexion() throws SQLException {
		System.out.println("Connecting to: " + url + " With user: " + user);
		conexion = DriverManager.getConnection(url, user, password);
	}

	public void closeConnection(Connection connection) throws SQLException {
		try {
			connection.close();
			connection = null;
		} catch (SQLException exception) {
			System.err.println("SQLException in closing Connection:");
			exception.printStackTrace();
			throw exception;
		}
	}

	public ArrayList<Cliente> darClientes() throws Exception {
		PreparedStatement prepStmt = null;
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();

		try {
			establecerConexion();
			String sql = "SELECT * FROM USUARIOS WHERE ROL = '1'";
			prepStmt = conexion.prepareStatement(sql);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next()) {
				int idc = Integer.parseInt(rs.getString("ID"));
				String nombre = rs.getString("NOMBRE");
				int rol = Integer.parseInt(rs.getString("ROL"));
				String correo = rs.getString("CORREO");
				int edad = Integer.parseInt(rs.getString("EDAD"));
				String preferenciaGenero = rs.getString("PREFERENCIA_GENERO");
				int preferenciaSitio = Integer.parseInt(rs.getString("PREFERENCIA_SITIO"));
				ArrayList<Boleta> boletas = this.boletas.buscarBoletasPorCliente(idc);
				clientes.add( new Cliente(nombre, idc, correo, rol, edad, preferenciaGenero, preferenciaSitio, boletas));
			}

		} catch (SQLException e) {
			System.err.println("SQLException in executing:");
			e.printStackTrace();
			throw e;
		} finally {
			if (prepStmt != null) {
				try {
					prepStmt.close();
				} catch (SQLException exception) {
					System.err.println("SQLException in closing Stmt:");
					exception.printStackTrace();
					throw exception;
				}
			}
			if (this.conexion != null)
				closeConnection(this.conexion);
			conexion.close();
		}
		return clientes;
	}
	public ArrayList<Representante> darRepresentantes() throws Exception {
		PreparedStatement prepStmt = null;
		ArrayList<Representante> representantes = new ArrayList<Representante>();

		try {
			establecerConexion();
			String sql = "SELECT * FROM USUARIOS WHERE ROL = '0'";
			prepStmt = conexion.prepareStatement(sql);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next()) {
				int idc = Integer.parseInt(rs.getString("ID"));
				String nombre = rs.getString("NOMBRE");
				int rol = Integer.parseInt(rs.getString("ROL"));
				String correo = rs.getString("CORREO");
				int companiaTeatro = this.companias.buscarCompaniaPorRepresentante(idc);
				representantes.add( new Representante(nombre, idc, correo, rol, companiaTeatro));
			}

		} catch (SQLException e) {
			System.err.println("SQLException in executing:");
			e.printStackTrace();
			throw e;
		} finally {
			if (prepStmt != null) {
				try {
					prepStmt.close();
				} catch (SQLException exception) {
					System.err.println("SQLException in closing Stmt:");
					exception.printStackTrace();
					throw exception;
				}
			}
			if (this.conexion != null)
				closeConnection(this.conexion);
			conexion.close();
		}
		return representantes;
	}
	public Usuario buscarUsuarioPorPK(int id) throws Exception {
		PreparedStatement prepStmt = null;
		Usuario usuario = null;

		try {
			establecerConexion();
			String sql = "SELECT * FROM USUARIOS WHERE ID = '"+id+"'";
			prepStmt = conexion.prepareStatement(sql);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next()) {
				int idc = Integer.parseInt(rs.getString("ID"));
				String nombre = rs.getString("NOMBRE");
				int rol = Integer.parseInt(rs.getString("ROL"));
				String correo = rs.getString("CORREO");
				if(rol == 0)
				{
					int companiaTeatro = this.companias.buscarCompaniaPorRepresentante(idc);
					usuario= new Representante(nombre, idc, correo, rol, companiaTeatro);
				}
				else
				{
					int edad = Integer.parseInt(rs.getString("EDAD"));
					String preferenciaGenero = rs.getString("PREFERENCIA_GENERO");
					int preferenciaSitio = Integer.parseInt(rs.getString("PREFERENCIA_SITIO"));
					ArrayList<Boleta> boletas = this.boletas.buscarBoletasPorCliente(idc);
					usuario = new Cliente(nombre, idc, correo, rol, edad, preferenciaGenero, preferenciaSitio, boletas);
				}
			}

		} catch (SQLException e) {
			System.err.println("SQLException in executing:");
			e.printStackTrace();
			throw e;
		} finally {
			if (prepStmt != null) {
				try {
					prepStmt.close();
				} catch (SQLException exception) {
					System.err.println("SQLException in closing Stmt:");
					exception.printStackTrace();
					throw exception;
				}
			}
			if (this.conexion != null)
				closeConnection(this.conexion);
			conexion.close();
		}
		return usuario;
	}

	public void cambiarPreferencias(int id, String prefe) throws SQLException {
		PreparedStatement prepStmt = null;
		try 
		{
			establecerConexion();
			String sql = "UPDATE USUARIOS SET PREFERENCIA_GENERO ='"+prefe+"' WHERE ID = '"+id+"'";
			prepStmt = conexion.prepareStatement(sql);
			prepStmt.execute();
		} 
		catch (SQLException e) {
			System.err.println("SQLException in executing:");
			e.printStackTrace();
			throw e;
		} 
		finally 
		{
			if (prepStmt != null) 
			{
				try {
					prepStmt.close();
				} catch (SQLException exception) {
					System.err.println("SQLException in closing Stmt:");
					exception.printStackTrace();
					throw exception;
				}
			}
			if (this.conexion != null)
				closeConnection(this.conexion);
			conexion.close();
		}
	}

	public ArrayList<Cliente> darClientesMayorBoletas() throws Exception 
	{
		ArrayList<Cliente> clientes = darClientes();
		ArrayList<Cliente> resp = new ArrayList<Cliente>();
		int mayor = 0;
		for (int i = 0;i<clientes.size();i++)
		{
			Cliente este = clientes.get(i);
			int boletas = este.getBoletas().size();
			if(boletas == mayor)
				resp.add(este);
			else if(boletas > mayor)
			{
				resp = new ArrayList<Cliente>();
				resp.add(este);
				mayor = boletas;
			}
		}
		return resp;
	}
}