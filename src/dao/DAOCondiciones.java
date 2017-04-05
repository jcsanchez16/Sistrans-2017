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

public class DAOCondiciones {

	private Connection conexion;

	private String user;

	private String password;

	private String url;

	private String driver;
	
	private String conectionData;

	public DAOCondiciones(String conectionDat) {
		initConnectionData(conectionDat);
		conectionData = conectionDat;
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

	public ArrayList<String> darCondiciones() throws Exception {
		PreparedStatement prepStmt = null;
		ArrayList<String> condiciones = new ArrayList<String>();

		try {
			establecerConexion();
			String sql = "SELECT * FROM CONDICIONES_TECNICAS";
			prepStmt = conexion.prepareStatement(sql);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next()) {
				condiciones.add(rs.getString("NOMBRE"));
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
		}
		return condiciones;
	}
	public ArrayList<String> buscarCondicionesPorLugar(int idl) throws Exception {
		
		PreparedStatement prepStmt = null;
		ArrayList<String> condiciones = null;

		try {
			establecerConexion();
			String sql = "SELECT * FROM LUGAR_CONDICIONES WHERE ID_LUGAR ='" + idl + "'";
			prepStmt = conexion.prepareStatement(sql);
			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) {
				condiciones.add(rs.getString("NOMBRE_CONDICION"));			
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
		}
		return condiciones;
	}
public ArrayList<String> buscarCondicionesPorEspectaculo(int idl) throws Exception {
		
		PreparedStatement prepStmt = null;
		ArrayList<String> condiciones = new ArrayList <String>();

		try {
			establecerConexion();
			String sql = "SELECT * FROM ESPECTACULO_CONDICIONES WHERE ID_ESPECTACULO ='" + idl + "'";
			prepStmt = conexion.prepareStatement(sql);
			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) {
				condiciones.add(rs.getString("NOMBRE_CONDICION"));			
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
		}
		return condiciones;
	}
}