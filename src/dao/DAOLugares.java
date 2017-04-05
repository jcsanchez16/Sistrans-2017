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

public class DAOLugares {

	private Connection conexion;

	private String user;

	private String password;

	private String url;

	private String driver;
	
	private String conectionData;
	
	private DAOFuncion funciones;
	
	private DAOCondiciones condiciones;

	public DAOLugares(String conectionDat) {
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

	public ArrayList<Lugar> darLugares() throws Exception {
		PreparedStatement prepStmt = null;
		ArrayList<Lugar> lugares = new ArrayList<Lugar>();

		try {
			establecerConexion();
			String sql = "SELECT * FROM LUGARES";
			prepStmt = conexion.prepareStatement(sql);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next()) {
				int idl = Integer.parseInt(rs.getString("ID"));
				String nombre = rs.getString("NOMBRE");
				int ab = Integer.parseInt(rs.getString("ABIERTO"));
				boolean abierto = ab == 1? false:true;
				int capacidadVip = Integer.parseInt(rs.getString("CAPACIDAD_VIP"));
				int capacidadGeneral = Integer.parseInt(rs.getString("CAPACIDAD_GENERAL"));
				int capacidadPlatea = Integer.parseInt(rs.getString("CAPACIDAD_PLATEA"));
				int in = Integer.parseInt(rs.getString("INCAPACITADOS"));
				boolean incapacitados = in == 1? false:true;
				int horaApertura = Integer.parseInt(rs.getString("HORA_APERTURA"));
				int horaCierre = Integer.parseInt(rs.getString("HORA_CIERRE"));
				ArrayList<String> condicionesTecnicas = condiciones.buscarCondicionesPorLugar(idl);
				String tipoSilleteria= rs.getString("TIPO_SILLETERIA");
				int pro = Integer.parseInt(rs.getString("PROTECCION_LLUVIA"));
				boolean proteccionLluvia = pro == 1? false:true;
				int num = Integer.parseInt(rs.getString("NUMERADA"));
				boolean numerada = pro == 1? false:true;;
				ArrayList<Funcion> funciones= this.funciones.buscarFuncionesPorLugar(idl);
				lugares.add( new Lugar(idl, nombre, abierto, capacidadVip, capacidadGeneral, capacidadPlatea, incapacitados, horaApertura, horaCierre, condicionesTecnicas, tipoSilleteria, proteccionLluvia, numerada, funciones));
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
		return lugares;
	}
	public Lugar buscarLugarPorPK(int id) throws Exception {
		
		PreparedStatement prepStmt = null;
		Lugar l = null;

		try {
			establecerConexion();
			String sql = "SELECT * FROM LUGARES WHERE ID ='" + id + "'";
			prepStmt = conexion.prepareStatement(sql);
			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) {
				int idl = Integer.parseInt(rs.getString("ID"));
				String nombre = rs.getString("NOMBRE");
				int ab = Integer.parseInt(rs.getString("ABIERTO"));
				boolean abierto = ab == 1? false:true;
				int capacidadVip = Integer.parseInt(rs.getString("CAPACIDAD_VIP"));
				int capacidadGeneral = Integer.parseInt(rs.getString("CAPACIDAD_GENERAL"));
				int capacidadPlatea = Integer.parseInt(rs.getString("CAPACIDAD_PLATEA"));
				int in = Integer.parseInt(rs.getString("INCAPACITADOS"));
				boolean incapacitados = in == 1? false:true;
				int horaApertura = Integer.parseInt(rs.getString("HORA_APERTURA"));
				int horaCierre = Integer.parseInt(rs.getString("HORA_CIERRE"));
				ArrayList<String> condicionesTecnicas = condiciones.buscarCondicionesPorLugar(id);
				String tipoSilleteria= rs.getString("TIPO_SILLETERIA");
				int pro = Integer.parseInt(rs.getString("PROTECCION_LLUVIA"));
				boolean proteccionLluvia = pro == 1? false:true;
				int num = Integer.parseInt(rs.getString("NUMERADA"));
				boolean numerada = pro == 1? false:true;;
				ArrayList<Funcion> funciones= this.funciones.buscarFuncionesPorLugar(id);
				l = new Lugar(idl, nombre, abierto, capacidadVip, capacidadGeneral, capacidadPlatea, incapacitados, horaApertura, horaCierre, condicionesTecnicas, tipoSilleteria, proteccionLluvia, numerada, funciones);
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
		return l;
	}
}