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
import vos.Espectaculo;
import vos.Lugar;
@Path("espectaculo")
public class EspectaculoServices 
{
	@Context
	private ServletContext context;

	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}
	@GET
	@Path("/list")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response GetEspectaculos() {
		FestAndesMaster master = FestAndesMaster.darInstancia(getPath());
		ArrayList<Espectaculo> a = null;
		try {
			a = master.darEspectaculos();
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
	public Response GetEspectaculos(@QueryParam("id") int id) {
		FestAndesMaster master = FestAndesMaster.darInstancia(getPath());
	Espectaculo a = null;
		try {
			a = master.darEspectaculosPk(id);
		} catch (Exception e) {
			String temp = null;
			temp=e.getMessage();
			return Response.status(500).entity(temp).build();
		}
		return Response.status(200).entity(a).build();
	}
	}
