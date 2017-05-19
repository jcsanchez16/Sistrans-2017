package dao;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
				Date fec = Date.valueOf(rs.getString("FECHA_FUNCION").substring(0, 10));
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
	public ArrayList<Boleta> buscarBoletasPorFuncion(int idE, Date fecha) throws Exception {
		
		PreparedStatement prepStmt = null;
		ArrayList<Boleta> b = new ArrayList<Boleta>();
		

		try {
			establecerConexion();
			String sql = "SELECT * FROM BOLETAS WHERE ID_ESPECTACULO_FUNCION ='" + idE + "' AND FECHA_FUNCION ='"+fecha.getDate()+"-"+(fecha.getMonth()+1)+"-"+(fecha.getYear()-100)+"'";
			prepStmt = conexion.prepareStatement(sql);
			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) {
				int idEs = Integer.parseInt(rs.getString("ID_ESPECTACULO_FUNCION"));
				String loc = rs.getString("LOCALIDAD");
				int idU = Integer.parseInt(rs.getString("ID_USUARIO"));
				Date fec = Date.valueOf(rs.getString("FECHA_FUNCION").substring(0, 10));
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
	
public int buscarBoletasPorFuncionTipo(int idE, String fecha, String tipo) throws Exception {
		
		PreparedStatement prepStmt = null;
		int contador = 0;
		

		try {
			establecerConexion();
			String sql = "SELECT * FROM BOLETAS WHERE ID_ESPECTACULO_FUNCION ='" + idE + "' AND FECHA_FUNCION ='"+fecha+"' AND LOCALIDAD='"+ tipo+"'";
			prepStmt = conexion.prepareStatement(sql);
			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) {
				contador++;
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
		return contador;
	}

	public String añadirBoleta(int id, int espectaculo, String fecha, String tipo) throws Exception {
		PreparedStatement prepStmt = null;	
		String esp = null;
		try {
			DAOFuncion funciones = new DAOFuncion(conectionData);
			DAOLugares lugares = new DAOLugares(conectionData);
			int capacidad = 0;
			if(tipo.equals("Platea"))
				capacidad = lugares.buscarLugarPorPK(funciones.buscarFuncionPK(fecha, espectaculo).getLugar()).getCapacidadPlatea();
			else if(tipo.equals("VIP"))
				capacidad = lugares.buscarLugarPorPK(funciones.buscarFuncionPK(fecha, espectaculo).getLugar()).getCapacidadVip();
			else if(tipo.equals("General"))
				capacidad = lugares.buscarLugarPorPK(funciones.buscarFuncionPK(fecha, espectaculo).getLugar()).getCapacidadGeneral();
			if(buscarBoletasPorFuncionTipo(espectaculo, fecha, tipo)+1>capacidad)
				esp= "No hay capacidad suficiente";
			else
			{
				establecerConexion();
				String sql = "INSERT INTO BOLETAS (ID_USUARIO, ID_ESPECTACULO_FUNCION, FECHA_FUNCION, LOCALIDAD) VALUES ('"+id+"', '"+espectaculo+"', '"+fecha+"', '"+tipo +"')";
				prepStmt = conexion.prepareStatement(sql);
				prepStmt.execute();
				esp = "se realizo la reserva";
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
		}
		return esp;
	}

	public String añadirAbono(int id, String espectaculos, String fechas, String tipos, String fest) throws NumberFormatException, Exception {
		
		String [] espec = espectaculos.split("/");
		String [] fec = fechas .split("/");
		String [] tip = tipos.split("/");
		String resp = null;
		PreparedStatement prepStmt = null;
		try {
			establecerConexion();
			conexion.setAutoCommit(false);
			Savepoint save =conexion.setSavepoint();
			String sql = "SELECT * FROM FESTIVALES WHERE NOMBRE ='" + fest + "''";
			prepStmt = conexion.prepareStatement(sql);
			ResultSet rs = prepStmt.executeQuery();
			Date fechaI = null;
			while (rs.next()) {
				fechaI = new Date(Date.valueOf(rs.getString("FECHA_INICIO").substring(0, 10)).getTime()-1814400000);
			}
			if (fechaI.before(new java.util.Date()))
				resp = "Fecha limite para compra de abono superada";
			else
			{
				boolean bien  = true;
				for(int i  = 0 ; i< espec.length && bien ; i ++)
				{
					resp =añadirBoleta(id, Integer.parseInt(espec[i]), fec[i], tip[i]);
					if (!resp.equals("se realizo la reserva"))
					{
						conexion.rollback(save);					
						bien = false ;
					}				
				}
				if(!bien)
					resp = "fallo la compra";
			}
		

		} catch (SQLException e) {
			System.err.println("SQLException in executing:");
			e.printStackTrace();
			throw e;
		} finally {
			conexion.setAutoCommit(true);
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
		return resp;
	}

	public String devolverBoleta(int id, int espectaculo, String fecha, boolean b) throws Exception {
		PreparedStatement prepStmt = null;	
		String esp = null;
		try {
			DAOFuncion funciones = new DAOFuncion(conectionData);
			Date fechaI = new Date(funciones.buscarFuncionPK(fecha, espectaculo).getFecha().getTime()-432000000);		
			if (fechaI.before(new java.util.Date())&& b)
				esp = "Fecha limite para devolucion boleta superada";
			else
			{
				establecerConexion();
				String sql = "DELETE FROM BOLETAS WHERE  ID_ESPECTACULO_FUNCION ='" + espectaculo + "' AND FECHA_FUNCION ='"+fecha+"' AND ID_USUARIO ='" + id + "'";
				prepStmt = conexion.prepareStatement(sql);
				prepStmt.execute();
				esp = "se elimino la reserva";
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
		}
		return esp;
	}
}