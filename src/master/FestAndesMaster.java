package master;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import dao.DAOBoleta;
import dao.DAOCategorias;
import dao.DAOCompanias;
import dao.DAOCondiciones;
import dao.DAOEspectaculos;
import dao.DAOFestival;
import dao.DAOFuncion;
import dao.DAOLugares;
import dao.DAOUsuarios;
import vos.Cliente;
import vos.CompaniaTeatro;
import vos.Espectaculo;
import vos.Festival;
import vos.Funcion;
import vos.Lugar;
import vos.Representante;
import vos.Usuario;

public class FestAndesMaster {

	private static FestAndesMaster instacia;

	private static final String CONNECTION_DATA_FILE_NAME_REMOTE = "/conexion.properties";

	private static String connectionDataPath;

	private DAOBoleta daoBoleta;
	
	private DAOFuncion daoFuncion;

	private DAOCompanias daoCompanias;
	
	private DAOCategorias daoCategorias;
	
	private DAOCondiciones daoCondiciones;
	
	private DAOEspectaculos daoEspectaculos;
	
	private DAOLugares daoLugares;
	
	private DAOUsuarios daoUsuarios;
	
	private DAOFestival daoFestival;
	
	public static FestAndesMaster darInstancia(String contextPathP) {
		connectionDataPath = contextPathP + CONNECTION_DATA_FILE_NAME_REMOTE;
		instacia = instacia == null ? new FestAndesMaster() : instacia;
		return instacia;
	}

	private FestAndesMaster() 
	{
		daoBoleta = new DAOBoleta(connectionDataPath);
		daoFuncion = new DAOFuncion(connectionDataPath);
		daoCompanias = new DAOCompanias(connectionDataPath);
		daoCategorias = new DAOCategorias(connectionDataPath);
		daoCondiciones = new DAOCondiciones(connectionDataPath);
		daoEspectaculos = new DAOEspectaculos(connectionDataPath);
		daoLugares	= new DAOLugares(connectionDataPath);
		daoUsuarios = new DAOUsuarios(connectionDataPath);
	}

public ArrayList<Funcion> darFunciones() throws Exception {
		daoFuncion = daoFuncion == null ? new DAOFuncion(connectionDataPath) : daoFuncion;
		return daoFuncion.darFunciones();
	}
public Funcion darFuncionesPk(int id,String fecha) throws Exception {
	daoFuncion = daoFuncion == null ? new DAOFuncion(connectionDataPath) : daoFuncion;
	return daoFuncion.buscarFuncionPK(fecha, id);
}

	public ArrayList<CompaniaTeatro> darCompanias() throws Exception {
		daoCompanias = daoCompanias == null ? new DAOCompanias(connectionDataPath) : daoCompanias;
		return daoCompanias.darCompanias();
	}
	
	public CompaniaTeatro darCompaniasPk(int id) throws Exception {
		daoCompanias = daoCompanias == null ? new DAOCompanias(connectionDataPath) : daoCompanias;
		return daoCompanias.buscarCompaniasPorPK(id);
	}
	
	public ArrayList<Espectaculo> darEspectaculos() throws Exception {
		daoEspectaculos = daoEspectaculos == null ? new DAOEspectaculos(connectionDataPath) : daoEspectaculos;
		return daoEspectaculos.darEspectaculos();
	}

	public Espectaculo darEspectaculosPk(int id) throws Exception {
		daoEspectaculos = daoEspectaculos == null ? new DAOEspectaculos(connectionDataPath) : daoEspectaculos;
		return daoEspectaculos.buscarEspectaculoPorPK(id);
	}
	
	public ArrayList<Festival> darFestivales() throws Exception {
		daoFestival = daoFestival == null ? new DAOFestival(connectionDataPath) : daoFestival;
		return daoFestival.darFestivales();	
	}
	
	public Festival darFestivalesPk(String nombre) throws Exception {
		daoFestival = daoFestival == null ? new DAOFestival(connectionDataPath) : daoFestival;
		return daoFestival.buscarFestivalPorPK(nombre);
	}

	public ArrayList<Lugar> darLugares() throws Exception {
		daoLugares = daoLugares == null ? new DAOLugares(connectionDataPath) : daoLugares;
		return daoLugares.darLugares();
	}
	public Lugar darLugaresPk(int id) throws Exception {
		daoLugares = daoLugares == null ? new DAOLugares(connectionDataPath) : daoLugares;
		return daoLugares.buscarLugarPorPK(id);
	}

	public ArrayList<Cliente> darClientes() throws Exception {
		daoUsuarios = daoUsuarios == null ? new DAOUsuarios(connectionDataPath) : daoUsuarios;
		return daoUsuarios.darClientes();
	}
	
	public ArrayList<Representante> darRepresentantes() throws Exception {
		daoUsuarios = daoUsuarios == null ? new DAOUsuarios(connectionDataPath) : daoUsuarios;
		return daoUsuarios.darRepresentantes();
	}
	
	public Usuario darUsuariosPk(int id) throws Exception {
		daoUsuarios = daoUsuarios == null ? new DAOUsuarios(connectionDataPath) : daoUsuarios;
		return daoUsuarios.buscarUsuarioPorPK(id);
	}

	public void RF7(int id, String prefe) throws Exception 
	{
		daoUsuarios = daoUsuarios == null ? new DAOUsuarios(connectionDataPath) : daoUsuarios;
		daoUsuarios.cambiarPreferencias(id, prefe);
	}

	public String RF8(int id, int espectaculo, String fecha, String tipo) throws Exception {
		daoBoleta = daoBoleta== null ? new DAOBoleta(connectionDataPath): daoBoleta;
		return daoBoleta.añadirBoleta(id, espectaculo,fecha, tipo);	
	}

	public void RF9(int espectaculo, String fecha) throws Exception{
		daoFuncion = daoFuncion == null ? new DAOFuncion(connectionDataPath) : daoFuncion;
		daoFuncion.marcarRealizada(espectaculo, fecha);
	}

	public String RFC3(int espectaculo, String fecha) throws Exception {
		daoFuncion = daoFuncion == null ? new DAOFuncion(connectionDataPath) : daoFuncion;
		return daoFuncion.buscarReporte(espectaculo, fecha);
	}

	public ArrayList<Cliente> quiz() throws Exception {
		daoUsuarios = daoUsuarios == null ? new DAOUsuarios(connectionDataPath) : daoUsuarios;
		return daoUsuarios.darClientesMayorBoletas();
	}

	public String RF11(int id, String espectaculos, String fechas, String tipos, String fest) throws NumberFormatException, Exception 
	{
		daoBoleta = daoBoleta== null ? new DAOBoleta(connectionDataPath): daoBoleta;
		return daoBoleta.añadirAbono(id,espectaculos, fechas, tipos, fest);	
	}

	public String RF12(int id, int espectaculo, String fecha) throws Exception {
		daoBoleta = daoBoleta== null ? new DAOBoleta(connectionDataPath): daoBoleta;
		return daoBoleta.devolverBoleta(id,espectaculo, fecha, true);	
	}

	public String RF14(int espectaculo, String fecha) throws Exception {
		daoFuncion = daoFuncion == null ? new DAOFuncion(connectionDataPath) : daoFuncion;
		return daoFuncion.cancelarFuncion(espectaculo, fecha);
	}
}
