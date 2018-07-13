package java.service;

import java.EntityManagerJPA;
import java.model.ProductModel;

import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

@Path("/products")
public class ProductService {
	
		private EntityManagerJPA JPAEM = new EntityManagerJPA();
		private EntityManager objEM = JPAEM.getEntityManager();

		@POST
		@Path("/cadastrar")
		@Consumes("application/json")
		public Response cadastrar(ProductModel objProduct){
			try {
				objEM.getTransaction().begin();
				objEM.persist(objProduct);
				objEM.getTransaction().commit();
				objEM.close();
				return Response.status(200).entity("Sucess. The product has stored.").build();
			} catch (Exception e) {
				throw new WebApplicationException(500);
			}
		}

}