import javax.ws.rs.client.*;
import javax.ws.rs.core.*;
import java.net.URI;

import org.glassfish.jersey.client.ClientConfig;

public class ClientREST{
	
	public static void main(String[] args) {


		Client c = ClientBuilder.newClient(new ClientConfig());

		WebTarget target = c.target("http://localhost:8080/AnalyseurRequete");
		String rep = target.path("salut/diallo").request().get(String.class);
		System.out.println(rep);
		
	}
}