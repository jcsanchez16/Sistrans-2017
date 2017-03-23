package master;

import java.util.ArrayList;
import java.util.Date;

import dao.DAOBoleta;
import dao.DAOFuncion;
import vos.Cliente;
import vos.CompaniaTeatro;
import vos.Espectaculo;
import vos.Festival;
import vos.Funcion;
import vos.Lugar;
import vos.Usuario;

public class FestAndesMaster {

	private static FestAndesMaster instacia;

	private static final String CONNECTION_DATA_FILE_NAME_REMOTE = "/conexion.properties";

	private static String connectionDataPath;

	private DAOBoleta daoBoleta;
	
	private DAOFuncion daoFuncion;
	
	public static FestAndesMaster darInstancia(String contextPathP) {
		connectionDataPath = contextPathP + CONNECTION_DATA_FILE_NAME_REMOTE;
		instacia = instacia == null ? new FestAndesMaster() : instacia;
		return instacia;
	}

	private FestAndesMaster() 
	{
		daoBoleta = new DAOBoleta(connectionDataPath);
		daoFuncion = new DAOFuncion(connectionDataPath);
	}

public ArrayList<Funcion> darFunciones() throws Exception {
		daoFuncion = daoFuncion == null ? new DAOFuncion(connectionDataPath) : daoFuncion;
		return daoFuncion.darFunciones();
	}
	public Funcion buscarAeropuertoPK( String fecha, int id )throws Exception
	{
		daoFuncion = daoFuncion == null ? new DAOFuncion(connectionDataPath) : daoFuncion;
		return daoFuncion.buscarFuncionPK(fecha, id);
	}

	public ArrayList<CompaniaTeatro> darCompanias() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Espectaculo> darEspectaculos() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Festival> darFestivales() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Lugar> darLugares() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Usuario> darUsuarios() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
