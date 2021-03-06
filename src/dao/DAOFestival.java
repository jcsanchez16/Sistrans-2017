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
import vos.Festival;
import vos.Funcion;
import vos.Lugar;

public class DAOFestival {

	private Connection conexion;

	private String user;

	private String password;

	private String url;

	private String driver;
	
	private String conectionData;
	
	private DAOFuncion funciones;
	
	private DAOCondiciones condiciones;

	public DAOFestival(String conectionDat) {
		initConnectionData(conectionDat);
		conectionData = conectionDat;
		condiciones = new DAOCondiciones(conectionData);
;		funciones = new DAOFuncion(conectionData);
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

	public ArrayList<Festival> darFestivales() throws Exception {
		PreparedStatement prepStmt = null;
		ArrayList<Festival> festivales = new ArrayList<Festival>();

		try {
			establecerConexion();
			String sql = "SELECT * FROM FESTIVALES";
			prepStmt = conexion.prepareStatement(sql);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next()) {
				String nombre = rs.getString("NOMBRE");
				Date fechaI = Date.valueOf(rs.getString("FECHA_INICIO").substring(0, 10));
				Date fechaF = Date.valueOf(rs.getString("FECHA_FINAL").substring(0, 10));
				ArrayList<Funcion> funci =funciones.buscarFuncionesPorFestival(nombre);
				festivales.add( new Festival(nombre, fechaI, fechaF, funci));
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
		return festivales;
	}
	public Festival buscarFestivalPorPK(String nom) throws Exception {
		
		PreparedStatement prepStmt = null;
		Festival l = null;

		try {
			establecerConexion();
			String sql = "SELECT * FROM FESTIVALES WHERE NOMBRE = '"+nom+"'";
			prepStmt = conexion.prepareStatement(sql);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next()) {
				String nombre = rs.getString("NOMBRE");
				Date fechaI = Date.valueOf(rs.getString("FECHA_INICIO").substring(0, 10));
				Date fechaF = Date.valueOf(rs.getString("FECHA_FINAL").substring(0, 10));
				ArrayList<Funcion> funci =funciones.buscarFuncionesPorFestival(nombre);
				l = new Festival(nombre, fechaI, fechaF, funci);
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
		return l;
	}
}