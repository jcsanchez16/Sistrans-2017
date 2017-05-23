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
import vos.Representante;
import vos.Usuario;

public class DAOCompanias {

	private Connection conexion;

	private String user;

	private String password;

	private String url;

	private String driver;
	
	private String conectionData;
	
	private DAOEspectaculos espectaculos;
	
	private DAOUsuarios usuarios;

	public DAOCompanias(String conectionDat) {
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

	public ArrayList<CompaniaTeatro> darCompanias() throws Exception {
		PreparedStatement prepStmt = null;
		ArrayList<CompaniaTeatro> companias = new ArrayList<CompaniaTeatro>();
		espectaculos= new DAOEspectaculos(conectionData);
		usuarios = new DAOUsuarios(conectionData);

		try {
			establecerConexion();
			String sql = "SELECT * FROM COMPANIAS_TEATROS";
			prepStmt = conexion.prepareStatement(sql);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next()) {
				int id = Integer.parseInt(rs.getString("ID"));
				String nombre = rs.getString("NOMBRE");
				ArrayList<Integer> espectaculos = this.espectaculos.buscarEspectaculosPorCompania(id);
				int repre = Integer.parseInt(rs.getString("ID_REPRESENTANTE"));
				Representante representante = (Representante) usuarios.buscarUsuarioPorPK(repre);
				java.sql.Date fechaSalida = Date.valueOf(rs.getString("FECHA_SALIDA").substring(0, 10));
				java.sql.Date fechaLlegada = Date.valueOf(rs.getString("FECHA_LLEGADA").substring(0, 10));
				String paginaWeb = rs.getString("PAGINA_WEB");
				String pais = rs.getString("PAIS");
				companias.add( new CompaniaTeatro(id, nombre, pais, paginaWeb, fechaLlegada, fechaSalida, representante, espectaculos));
			}

		} catch (Exception e) {
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
		return companias;
	}
	public CompaniaTeatro buscarCompaniasPorPK(int id) throws Exception {
		
		PreparedStatement prepStmt = null;
		CompaniaTeatro compania = null;

		try {
			establecerConexion();
			String sql = "SELECT * FROM COMPANIAS_TEATROS WHERE ID = '" + id +"'";
			prepStmt = conexion.prepareStatement(sql);
			ResultSet rs = prepStmt.executeQuery();
			espectaculos= new DAOEspectaculos(conectionData);
			usuarios = new DAOUsuarios(conectionData);
			while (rs.next()) {
				int idc = Integer.parseInt(rs.getString("ID"));
				String nombre = rs.getString("NOMBRE");
				ArrayList<Integer> espectaculos = this.espectaculos.buscarEspectaculosPorCompania(id);
				Representante representante = (Representante) usuarios.buscarUsuarioPorPK(Integer.parseInt(rs.getString("ID_REPRESENTANTE")));
				java.sql.Date fechaSalida = Date.valueOf(rs.getString("FECHA_SALIDA").substring(0, 10));
				java.sql.Date fechaLlegada = Date.valueOf(rs.getString("FECHA_LLEGADA").substring(0, 10));
				String paginaWeb = rs.getString("PAGINA_WEB");
				String pais = rs.getString("PAIS");
				compania = new CompaniaTeatro(idc, nombre, pais, paginaWeb, fechaLlegada, fechaSalida, representante, espectaculos);
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
		return compania;
	}
	public ArrayList<CompaniaTeatro> buscarCompaniasPorEspectaculo(int ide) throws Exception {
		PreparedStatement prepStmt = null;
		ArrayList<CompaniaTeatro> companias = new ArrayList<CompaniaTeatro>();

		try {
			establecerConexion();
			String sql = "SELECT * FROM COMPANIA_ESPECTACULOS WHERE ID_ESPECTACULO ='"+ide+"'";
			prepStmt = conexion.prepareStatement(sql);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next()) {
				int id = Integer.parseInt(rs.getString("ID_COMPANIA"));
				companias.add(buscarCompaniasPorPK(id));
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
		return companias;
	}
	public int buscarCompaniaPorRepresentante(int id) throws Exception {
		
		PreparedStatement prepStmt = null;
		int compania = -1;

		try {
			establecerConexion();
			String sql = "SELECT * FROM COMPANIAS_TEATROS WHERE ID_REPRESENTANTE = '" + id +"'";
			prepStmt = conexion.prepareStatement(sql);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next()) {
				compania = Integer.parseInt(rs.getString("ID"));
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
		return compania;
	}
}