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
import vos.Funcion;

public class DAOFuncion {

	private Connection conexion;

	private String user;

	private String password;

	private String url;

	private String driver;
	
	private ArrayList<Funcion> funciones;
	
	private DAOFuncionU funcionU;

	public DAOFuncion(String conectionData) {
		initConnectionData(conectionData);
		funciones = new ArrayList<Funcion>();
		funcionU = new DAOFuncionU(conectionData);
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
				String fec = rs.getString("FECHA");
				Date fecha = Date.valueOf(fec);
				int idL = Integer.parseInt(rs.getString("ID_LUGAR"));
				boolean rea = Integer.parseInt(rs.getString("REALIZADA"))==0? true:false;
				String fest = rs.getString("FESTIVAL");
				ArrayList<Integer> clie = funcionU.buscarUsuariosPorFuncion(fec,idE);
				funciones.add(new Funcion(fecha, rea, fest, idL, idE, clie) );
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
				String fec = rs.getString("FECHA");
				Date fech = Date.valueOf(fec);
				int idL = Integer.parseInt(rs.getString("ID_LUGAR"));
				boolean rea = Integer.parseInt(rs.getString("REALIZADA"))==0? true:false;
				String fest = rs.getString("FESTIVAL");
				ArrayList<Integer> clie = funcionU.buscarUsuariosPorFuncion(fec,idE);
				funcion=new Funcion(fech, rea, fest, idL, idE, clie) ;
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

	
	
	

}
