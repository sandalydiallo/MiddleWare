package pack;

import javax.ws.rs.GET;
import javax.ws.rs.*;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.util.*;

@Path("/getActionAndObject")
public class AnalyseurRequete {

	@GET
	@Path("{val}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Infos> getResultAnalyse(@PathParam("val") String val) 
	{
		String[] res = val.split(" ",2);
		Infos i = new Infos();
		if(res.length > 0)
			i.setAction(res[0]);

		if (res.length == 2)
			i.setMusic(res[1]);

		List<Infos> list = Arrays.asList(i);
		return list;
	}
}