package rest;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import master.FestAndesMaster;
import vos.Cliente;
import vos.ListaEspectaculo;
import vos.Lugar;
import vos.Representante;
import vos.Usuario;
@Path("requerimientos")
public class RequerimientosServices 
{
	@Context
	private ServletContext context;

	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}
	@PUT
	@Path("/RF7")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response RF7(@QueryParam("id") int id, @QueryParam("prefe") String prefe) {
		FestAndesMaster master = FestAndesMaster.darInstancia(getPath());
		String a = "No se pudo cambiar la preferencia";
		try {
			 master.RF7(id, prefe);
			 a = "Preferencia cambiada satisfactoriamente";
		} catch (Exception e) {
			String temp = null;
			temp=e.getMessage();
			return Response.status(500).entity(temp).build();
		}
		return Response.status(200).entity(a).build();
	}
	@POST
	@Path("/RF8")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response RF8(@QueryParam("id") int id, @QueryParam("espectaculo") int espectaculo, @QueryParam("fecha") String fecha,@QueryParam("tipo") String tipo) {
		FestAndesMaster master = FestAndesMaster.darInstancia(getPath());
		String a = "fallo del metodo";
		try {
			 a =master.RF8(id, espectaculo,fecha, tipo);
		} catch (Exception e) {
			String temp = null;
			temp=e.getMessage();
			return Response.status(500).entity(temp).build();
		}
		return Response.status(200).entity(a).build();
	}
	@PUT
	@Path("/RF9")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response RF9(@QueryParam("espectaculo") int espectaculo, @QueryParam("fecha") String fecha) {
		FestAndesMaster master = FestAndesMaster.darInstancia(getPath());
		String a = "No se marco como realizada";
		try {
			 master.RF9(espectaculo, fecha);
			 a = "Se marco como realizada";
		} catch (Exception e) {
			String temp = null;
			temp=e.getMessage();
			return Response.status(500).entity(temp).build();
		}
		return Response.status(200).entity(a).build();
	}
	@PUT
	@Path("/RF11")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response RF11(@QueryParam("id") int id,@QueryParam("espectaculos") String espectaculos, @QueryParam("fechas") String fechas,@QueryParam("tipos") String tipos,@QueryParam("festival") String fest) {
		FestAndesMaster master = FestAndesMaster.darInstancia(getPath());
		String a = "No se pudo comprar el abonamiento";
		try {
			 a =  master.RF11(id,espectaculos, fechas, tipos, fest);
		} catch (Exception e) {
			String temp = null;
			temp=e.getMessage();
			return Response.status(500).entity(temp).build();
		}
		return Response.status(200).entity(a).build();
	}
	@PUT
	@Path("/RF12")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response RF12(@QueryParam("id") int id,@QueryParam("espectaculo") int espectaculo, @QueryParam("fecha") String fecha) {
		FestAndesMaster master = FestAndesMaster.darInstancia(getPath());
		String a = "No se devolvio la boleta";
		try {
			 a = master.RF12(id,espectaculo, fecha);
		} catch (Exception e) {
			String temp = null;
			temp=e.getMessage();
			return Response.status(500).entity(temp).build();
		}
		return Response.status(200).entity(a).build();
	}
	@PUT
	@Path("/RF14")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response RF14(@QueryParam("espectaculo") int espectaculo, @QueryParam("fecha") String fecha) {
		FestAndesMaster master = FestAndesMaster.darInstancia(getPath());
		String a = "No se cancelo la funcion";
		try {
			 a = master.RF14(espectaculo, fecha);
		} catch (Exception e) {
			String temp = null;
			temp=e.getMessage();
			return Response.status(500).entity(temp).build();
		}
		return Response.status(200).entity(a).build();
	}
	
	@GET
	@Path("/RFC3")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response RFC3(@QueryParam("espectaculo") int espectaculo, @QueryParam("fecha") String fecha) {
		FestAndesMaster master = FestAndesMaster.darInstancia(getPath());
		String a = null;
		try {
			String[] b = master.RFC3(espectaculo, fecha).split("-");
			String[] p = b[1].split("/");
			String[] g = b[2].split("/");
			String[] v = b[3].split("/");
			
			a= "Se vendieron "+ b[0]+"boletas. De localidad Platea fueron "+ p[0]+ " dando ganancia de "+ (Integer.parseInt(p[0])*Integer.parseInt(p[1]))+"De localidad General fueron "+ g[0]+ " dando ganancia de "+ (Integer.parseInt(g[0])*Integer.parseInt(g[1]))+ " De localidad VIP fueron "+ v[0]+ " dando ganancia de "+ (Integer.parseInt(v[0])*Integer.parseInt(v[1]));
		} catch (Exception e) {
			String temp = null;
			temp=e.getMessage();
			return Response.status(500).entity(temp).build();
		}
		return Response.status(200).entity(a).build();
	}
	@GET
	@Path("/quiz")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response quiz() {
		FestAndesMaster master = FestAndesMaster.darInstancia(getPath());
		ArrayList<Cliente> a = null;
		try {
			a = master.quiz();

		} catch (Exception e) {
			String temp = null;
			temp=e.getMessage();
			return Response.status(500).entity(temp).build();
		}
		return Response.status(200).entity(a).build();
	}
	@GET
	@Path("/RFC13")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response RFC13() {
		FestAndesMaster master = FestAndesMaster.darInstancia(getPath());
		ListaEspectaculo a = null;
		try {
			a = master.RFC13();	
		} catch (Exception e) {
			String temp = null;
			e.printStackTrace();
		
			temp=e.getMessage();
			return Response.status(500).entity(temp).build();
		}
		return Response.status(200).entity(a).build();
	}
}
