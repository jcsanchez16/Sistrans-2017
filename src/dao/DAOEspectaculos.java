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
import vos.CompaniaTeatro;
import vos.Espectaculo;
import vos.Funcion;
import vos.Lugar;

public class DAOEspectaculos {

	private Connection conexion;

	private String user;

	private String password;

	private String url;

	private String driver;
	
	private String conectionData;
	
	private DAOCondiciones condiciones;
	
	private DAOFuncion funciones;
	
	private DAOCategorias categorias;
	
	public DAOCompanias companias;

	public DAOEspectaculos(String conectionDat) 
	{
		initConnectionData(conectionDat);
		conectionData = conectionDat;
		condiciones = new DAOCondiciones(conectionData);
		funciones = new DAOFuncion(conectionData);
		categorias = new DAOCategorias(conectionData);
		companias = new DAOCompanias(conectionData)	;
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

	public ArrayList<Espectaculo> darEspectaculos() throws Exception {
		PreparedStatement prepStmt = null;
		ArrayList<Espectaculo> espectaculos = new ArrayList<Espectaculo>();

		try {
			establecerConexion();
			String sql = "SELECT * FROM ESPECRES";
			prepStmt = conexion.prepareStatement(sql);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next()) {
				
				int id = Integer.parseInt(rs.getString("ID"));
				String nombre = rs.getString("NOMBRE");
				int duracion = Integer.parseInt(rs.getString("DURACION"));
				ArrayList<String> requerimientos = condiciones.buscarCondicionesPorEspectaculo(id);
				String idioma = rs.getString("IDIOMA");
				double costoProduccion = Double.parseDouble(rs.getString("COSTO_PRODUCCION"));
				String descripcion = rs.getString("DESCRIPCION");
				ArrayList<CompaniaTeatro> companias = this.companias.buscarCompaniasPorEspectaculo(id);
				String publico = rs.getString("PUBLICO");
				String traduccion = rs.getString("TRADUCCION");
				int tip = Integer.parseInt(rs.getString("TIPO_PARTICIPACION"));
				boolean tipoParticipacion = tip == 1 ? false:true;
				ArrayList<String> categorias = this.categorias.buscarCategoriasPorEspectaculo(id);
				ArrayList<Funcion> funciones = this.funciones.buscarFuncionesPorEspectaculo(id);
				espectaculos.add(new Espectaculo(id, nombre, duracion, requerimientos, idioma, costoProduccion, descripcion, categorias, tipoParticipacion, traduccion, publico, funciones, companias));
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
		return espectaculos;
	}
	public Espectaculo buscarEspectaculoPorPK(int ide) throws Exception {
		
		PreparedStatement prepStmt = null;
		Espectaculo es = null;

		try {
			establecerConexion();
			String sql = "SELECT * FROM ESPECTACULOS WHERE ID ='" + ide + "'";
			prepStmt = conexion.prepareStatement(sql);
			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) {
				int id = Integer.parseInt(rs.getString("ID"));
				String nombre = rs.getString("NOMBRE");
				int duracion = Integer.parseInt(rs.getString("DURACION"));
				ArrayList<String> requerimientos = condiciones.buscarCondicionesPorEspectaculo(id);
				String idioma = rs.getString("IDIOMA");
				double costoProduccion = Double.parseDouble(rs.getString("COSTO_PRODUCCION"));
				String descripcion = rs.getString("DESCRIPCION");
				ArrayList<CompaniaTeatro> companias = this.companias.buscarCompaniasPorEspectaculo(id);
				String publico = rs.getString("PUBLICO");
				String traduccion = rs.getString("TRADUCCION");
				int tip = Integer.parseInt(rs.getString("TIPO_PARTICIPACION"));
				boolean tipoParticipacion = tip == 1 ? false:true;
				ArrayList<String> categorias = this.categorias.buscarCategoriasPorEspectaculo(id);
				ArrayList<Funcion> funciones = this.funciones.buscarFuncionesPorEspectaculo(id);
				es=new Espectaculo(id, nombre, duracion, requerimientos, idioma, costoProduccion, descripcion, categorias, tipoParticipacion, traduccion, publico, funciones, companias);
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
		return es;
	}
	public ArrayList<Integer> buscarEspectaculosPorCompania(int idc) throws Exception {
		PreparedStatement prepStmt = null;
		ArrayList<Integer> espectaculos = new ArrayList<Integer>();

		try {
			establecerConexion();
			String sql = "SELECT * FROM COMPANIA_ESPECTACULOS WHERE ID_COMPANIA = '"+ idc +"'";
			prepStmt = conexion.prepareStatement(sql);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next()) {
				espectaculos.add( Integer.parseInt(rs.getString("ID_ESPECTACULO")));
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
		return espectaculos;
	}
	
}