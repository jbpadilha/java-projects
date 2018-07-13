package java;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerJPA {
	
	private EntityManagerFactory factory = Persistence.createEntityManagerFactory("avenuecode-sqlite-jpa");	
	private EntityManager em = factory.createEntityManager();
	
	public EntityManager getEntityManager(){
		return em;
	}

	
}
