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
import vos.Lugar;
@Path("lugar")
public class LugarServices 
{
	@Context
	private ServletContext context;

	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}
	@GET
	@Path("/list")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response GetLugares() {
		FestAndesMaster master = FestAndesMaster.darInstancia(getPath());
		ArrayList<Lugar> a = null;
		try {
			a = master.darLugares();
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
	public Response GetLugares(@QueryParam("id") int id) {
		FestAndesMaster master = FestAndesMaster.darInstancia(getPath());
	Lugar a = null;
		try {
			a = master.darLugaresPk(id);
		} catch (Exception e) {
			String temp = null;
			temp=e.getMessage();
			return Response.status(500).entity(temp).build();
		}
		return Response.status(200).entity(a).build();
	}
	
	}
