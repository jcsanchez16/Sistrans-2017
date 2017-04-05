package rest;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import master.FestAndesMaster;
import vos.Cliente;
import vos.Lugar;
import vos.Representante;
import vos.Usuario;
@Path("usuario")
public class UsuarioServices 
{
	@Context
	private ServletContext context;

	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}
	@GET
	@Path("/listU")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response GetClientes() {
		FestAndesMaster master = FestAndesMaster.darInstancia(getPath());
		ArrayList<Cliente> a = null;
		try {
			a = master.darClientes();
		} catch (Exception e) {
			ArrayList<String> temp = new ArrayList<String>();
			temp.add(e.getMessage());
			return Response.status(500).entity(temp).build();
		}
		return Response.status(200).entity(a).build();
	}
	@GET
	@Path("/listR")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response GetRepresentantes() {
		FestAndesMaster master = FestAndesMaster.darInstancia(getPath());
		ArrayList<Representante> a = null;
		try {
			a = master.darRepresentantes();
		} catch (Exception e) {
			ArrayList<String> temp = new ArrayList<String>();
			temp.add(e.getMessage());
			return Response.status(500).entity(temp).build();
		}
		return Response.status(200).entity(a).build();
	}
	@GET
	@Path("/get")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response GetUsuario(@QueryParam("id") int id) {
		FestAndesMaster master = FestAndesMaster.darInstancia(getPath());
	Usuario a = null;
		try {
			a = master.darUsuariosPk(id);
		} catch (Exception e) {
			String temp = null;
			temp=e.getMessage();
			return Response.status(500).entity(temp).build();
		}
		return Response.status(200).entity(a).build();
	}
}
