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
import java.util.ArrayList;
import java.util.Properties;

import vos.Boleta;
import vos.Funcion;

public class DAOFuncion {

	private Connection conexion;

	private String user;

	private String password;

	private String url;

	private String driver;
	
	private ArrayList<Funcion> funciones;
	
	private DAOBoleta boleta;

	public DAOFuncion(String conectionData) {
		initConnectionData(conectionData);
		funciones = new ArrayList<Funcion>();
		boleta = new DAOBoleta(conectionData);
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

	public ArrayList<Funcion> darFunciones() throws Exception {
		PreparedStatement prepStmt = null;
		ArrayList<Funcion> funciones = new ArrayList<Funcion>();

		try {
			establecerConexion();
			String sql = "SELECT * FROM FUNCION";
			prepStmt = conexion.prepareStatement(sql);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next()) 	
			{
				int idE = Integer.parseInt(rs.getString("ID_ESPECTACULO"));
				String fec = rs.getString("FECHA").substring(0, 10);
				Date fecha = Date.valueOf(fec);
				int idL = Integer.parseInt(rs.getString("ID_LUGAR"));
				boolean rea = Integer.parseInt(rs.getString("REALIZADA"))==0? true:false;
				String fest = rs.getString("FESTIVAL");
				double pV = Double.parseDouble(rs.getString("PRECIO_VIP"));
				double pP = Double.parseDouble(rs.getString("PRECIO_PLATEA"));
				double pG = Double.parseDouble(rs.getString("PRECIO_GENERAL"));
				ArrayList<Boleta> bole = boleta.buscarBoletasPorFuncion(idE, fecha);
				funciones.add(new Funcion(fecha, rea, fest, idL, idE, bole, pV, pP,pG) );
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
		return funciones;
	}

	public Funcion buscarFuncionPK(String fecha, int id) throws Exception {
		PreparedStatement prepStmt = null;
		Funcion funcion = null;

		try {
			establecerConexion();
			String sql = "SELECT * FROM FUNCION WHERE ID_ESPECTACULO ='" +id + "' AND FECHA ='"+fecha+"'";
			prepStmt = conexion.prepareStatement(sql);
			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) 
			{
				int idE = Integer.parseInt(rs.getString("ID_ESPECTACULO"));
				String fec = rs.getString("FECHA").substring(0, 10);
				Date fechas = Date.valueOf(fec);
				int idL = Integer.parseInt(rs.getString("ID_LUGAR"));
				boolean rea = Integer.parseInt(rs.getString("REALIZADA"))==0? true:false;
				String fest = rs.getString("FESTIVAL");
				double pV = Double.parseDouble(rs.getString("PRECIO_VIP"));
				double pP = Double.parseDouble(rs.getString("PRECIO_PLATEA"));
				double pG = Double.parseDouble(rs.getString("PRECIO_GENERAL"));
				ArrayList<Boleta> bole = boleta.buscarBoletasPorFuncion(idE, fechas);
				funcion=new Funcion(fechas, rea, fest, idL, idE, bole, pV, pP,pG) ;
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
		return funcion;
	}

	public ArrayList<Funcion> buscarFuncionesPorLugar(int id) throws Exception {
		PreparedStatement prepStmt = null;
		ArrayList<Funcion> funciones = new ArrayList<Funcion>();

		try {
			establecerConexion();
			String sql = "SELECT * FROM FUNCION WHERE ID_LUGAR ='" +id + "'";
			prepStmt = conexion.prepareStatement(sql);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next()) 	
			{
				int idE = Integer.parseInt(rs.getString("ID_ESPECTACULO"));
				String fec = rs.getString("FECHA").substring(0, 10);
				Date fecha = Date.valueOf(fec);
				int idL = Integer.parseInt(rs.getString("ID_LUGAR"));
				boolean rea = Integer.parseInt(rs.getString("REALIZADA"))==0? true:false;
				String fest = rs.getString("FESTIVAL");
				double pV = Double.parseDouble(rs.getString("PRECIO_VIP"));
				double pP = Double.parseDouble(rs.getString("PRECIO_PLATEA"));
				double pG = Double.parseDouble(rs.getString("PRECIO_GENERAL"));
				ArrayList<Boleta> bole = boleta.buscarBoletasPorFuncion(idE, fecha);
				funciones.add(new Funcion(fecha, rea, fest, idL, idE, bole, pV, pP,pG) );
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
		return funciones;	
	}
	
	public ArrayList<Funcion> buscarFuncionesPorEspectaculo(int id) throws Exception {
		PreparedStatement prepStmt = null;
		ArrayList<Funcion> funciones = new ArrayList<Funcion>();

		try {
			establecerConexion();
			String sql = "SELECT * FROM FUNCION WHERE ID_ESPECTACULO ='" +id + "'";
			prepStmt = conexion.prepareStatement(sql);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next()) 	
			{
				int idE = Integer.parseInt(rs.getString("ID_ESPECTACULO"));
				String fec = rs.getString("FECHA").substring(0, 10);
				Date fecha = Date.valueOf(fec);
				int idL = Integer.parseInt(rs.getString("ID_LUGAR"));
				boolean rea = Integer.parseInt(rs.getString("REALIZADA"))==0? true:false;
				String fest = rs.getString("FESTIVAL");
				double pV = Double.parseDouble(rs.getString("PRECIO_VIP"));
				double pP = Double.parseDouble(rs.getString("PRECIO_PLATEA"));
				double pG = Double.parseDouble(rs.getString("PRECIO_GENERAL"));
				ArrayList<Boleta> bole = boleta.buscarBoletasPorFuncion(idE, fecha);
				funciones.add(new Funcion(fecha, rea, fest, idL, idE, bole, pV, pP,pG) );
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
		return funciones;	
	}
	public ArrayList<Funcion> buscarFuncionesPorFestival(String nombre) throws Exception {
		PreparedStatement prepStmt = null;
		ArrayList<Funcion> funciones = new ArrayList<Funcion>();

		try {
			establecerConexion();
			String sql = "SELECT * FROM FUNCION WHERE FESTIVAL ='" +nombre + "'";
			prepStmt = conexion.prepareStatement(sql);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next()) 	
			{
				int idE = Integer.parseInt(rs.getString("ID_ESPECTACULO"));
				String fec = rs.getString("FECHA").substring(0, 10);
				Date fecha = Date.valueOf(fec);
				int idL = Integer.parseInt(rs.getString("ID_LUGAR"));
				boolean rea = Integer.parseInt(rs.getString("REALIZADA"))==0? true:false;
				String fest = rs.getString("FESTIVAL");
				double pV = Double.parseDouble(rs.getString("PRECIO_VIP"));
				double pP = Double.parseDouble(rs.getString("PRECIO_PLATEA"));
				double pG = Double.parseDouble(rs.getString("PRECIO_GENERAL"));
				ArrayList<Boleta> bole = boleta.buscarBoletasPorFuncion(idE, fecha);
				funciones.add(new Funcion(fecha, rea, fest, idL, idE, bole, pV, pP,pG) );
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
		return funciones;	
	}

	public void marcarRealizada(int espectaculo, String fecha) throws Exception{
		PreparedStatement prepStmt = null;

		try {
			establecerConexion();
			String sql = "UPDATE FUNCION SET REALIZADA =1 WHERE  ID_ESPECTACULO = '"+espectaculo+"' AND FECHA ='" +fecha + "'";
			prepStmt = conexion.prepareStatement(sql);
			ResultSet rs = prepStmt.executeQuery();
			

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
	}

	public String buscarReporte(int espectaculo, String fecha) throws Exception {
		PreparedStatement prepStmt = null;
		String a = null;

		try {
			Funcion f =buscarFuncionPK(fecha, espectaculo);
			ArrayList<Boleta> boletas = f.getClientes();
			int cP = 0;
			int cV = 0;
			int cG = 0;
			for(int i = 0 ; i<boletas.size();i++ )
			{
				Boleta esta = boletas.get(i);
				if(esta.getLocalidad().equals("Platea"))
					cP++;
				else if(esta.getLocalidad().equals("General"))
					cG++;
				else if(esta.getLocalidad().equals("VIP"))
					cV++;				
			}
				
			a = boletas.size()+"-"+cP+"/"+f.getPrecioPlatea()+"-"+cG+"/"+ f.getPrecioGeneral()+"-"+cV+"/"+f.getPrecioVip();

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
		return a;
	}

	public String cancelarFuncion(int espectaculo, String fecha) throws Exception {
		String resp = null;
		PreparedStatement prepStmt = null;
		ArrayList<Boleta> boletas = boleta.buscarBoletasPorFuncion(espectaculo, Date.valueOf(fecha));
		try {
			establecerConexion();
			conexion.setAutoCommit(false);
			Savepoint save =conexion.setSavepoint();
			boolean bien  = true;
			for( int i = 0 ; i < boletas.size()&& bien;i++)
			{
				resp =boleta.devolverBoleta(boletas.get(i).getUsuario(), espectaculo, fecha, false);
				if (!resp.equals("se elimino la reserva"))
				{
					conexion.rollback(save);					
					bien = false ;
				}				
			}
			if(!bien)
				resp = "fallo la cancelacion";
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
		
	
}
