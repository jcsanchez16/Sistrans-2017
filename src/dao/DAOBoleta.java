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

public class DAOBoleta {

	private Connection conexion;

	private String user;

	private String password;

	private String url;

	private String driver;
	
	private String conectionData;

	public DAOBoleta(String conectionDat) {
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

	
	public ArrayList<Boleta> buscarBoletasPorCliente(int idC) throws Exception {
		
		PreparedStatement prepStmt = null;
		ArrayList<Boleta> b = new ArrayList<Boleta>();

		try {
			establecerConexion();
			String sql = "SELECT * FROM BOLETAS WHERE ID_USUARIO ='" + idC + "'";
			prepStmt = conexion.prepareStatement(sql);
			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) {
				int idE = Integer.parseInt(rs.getString("ID_ESPECTACULO_FUNCION"));
				String loc = rs.getString("LOCALIDAD");
				int idU = Integer.parseInt(rs.getString("ID_USUARIO"));
				Date fec = Date.valueOf(rs.getString("FECHA_FUNCION"));
				b.add(new Boleta(idU, idE, fec, loc));
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
		return b;
	}
public ArrayList<Boleta> buscarBoletasPorFuncion(int idE, String fecha) throws Exception {
		
		PreparedStatement prepStmt = null;
		ArrayList<Boleta> b = new ArrayList<Boleta>();

		try {
			establecerConexion();
			String sql = "SELECT * FROM BOLETAS WHERE ID_ESPECTACULO_FUNCION ='" + idE + "' AND FECHA_FUNCION ='"+fecha+"'";
			prepStmt = conexion.prepareStatement(sql);
			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) {
				int idEs = Integer.parseInt(rs.getString("ID_ESPECTACULO_FUNCION"));
				String loc = rs.getString("LOCALIDAD");
				int idU = Integer.parseInt(rs.getString("ID_USUARIO"));
				Date fec = Date.valueOf(rs.getString("FECHA_FUNCION"));
				b.add(new Boleta(idU, idEs, fec, loc));
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
		return b;
	}
}