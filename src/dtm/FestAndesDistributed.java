package dtm;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TopicConnectionFactory;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.rabbitmq.jms.admin.RMQConnectionFactory;
import com.rabbitmq.jms.admin.RMQDestination;

import jms.EspectaculosMDB;
import jms.NonReplyException;
import master.FestAndesMaster;
import vos.ListaEspectaculo;

public class FestAndesDistributed 
{
	private final static String QUEUE_NAME = "java:global/RMQAppQueue";
	private final static String MQ_CONNECTION_NAME = "java:global/RMQClient";
	
	private static FestAndesDistributed instance;
	
	private FestAndesMaster master;
	
	private QueueConnectionFactory queueFactory;
	
	private TopicConnectionFactory factory;
	
	private EspectaculosMDB EspectaculosMQ;
	
	private static String path;


	private FestAndesDistributed() throws Exception
	{
		InitialContext ctx = new InitialContext();
		factory = (RMQConnectionFactory) ctx.lookup(MQ_CONNECTION_NAME);
		EspectaculosMQ = new EspectaculosMDB(factory, ctx);
		
		EspectaculosMQ.start();
		
	}
	
	public void stop() throws JMSException
	{
		EspectaculosMQ.close();
	}
	
	/**
	 * Método que retorna el path de la carpeta WEB-INF/ConnectionData en el deploy actual dentro del servidor.
	 * @return path de la carpeta WEB-INF/ConnectionData en el deploy actual.
	 */
	public static void setPath(String p) {
		path = p;
	}
	
	public void setUpTransactionManager(FestAndesMaster master)
	{
	   this.master = master;
	}
	
	private static FestAndesDistributed getInst()
	{
		return instance;
	}
	
	public static FestAndesDistributed getInstance(FestAndesMaster master)
	{
		if(instance == null)
		{
			try 
			{
				instance = new FestAndesDistributed();
			} 
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		instance.setUpTransactionManager(master);
		return instance;
	}
	
	public static FestAndesDistributed getInstance()
	{
		if(instance == null)
		{
			FestAndesMaster master = FestAndesMaster.darInstancia(path);
			return getInstance(master);
		}
		if(instance.master != null)
		{
			return instance;
			
		}
		FestAndesMaster master =FestAndesMaster.darInstancia(path);
		return getInstance(master);
	}
	
	public ListaEspectaculo getLocalEspectaculos() throws Exception
	{
		return new ListaEspectaculo(master.darEspectaculos());
	}
	
	public ListaEspectaculo getRemoteEspectaculos() throws JsonGenerationException, JsonMappingException, JMSException, IOException, NonReplyException, InterruptedException, NoSuchAlgorithmException
	{
		return EspectaculosMQ.getRemoteEspectaculos();
	}
}
