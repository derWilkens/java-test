package local.paxbase.unitests;

import static org.junit.Assert.*;

import java.util.List;


import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.TypedQuery;
import com.haulmont.cuba.security.entity.User;


public class CrewChangeTest {

	private User user;
	

	@ClassRule
	public static StandardTestContainer cont = StandardTestContainer.Common.INSTANCE;
	
	@Before
	public void setUp() throws Exception {
		
		user = cont.persistence().createTransaction().execute(em -> {
			User user = new User();
			user.setName("testCustomer");
			em.persist(user);
			return user;
		});
	}
    @After
    public void tearDown() throws Exception {
        cont.deleteRecord(user);
    }
    
    
    @Test
    public void test() {
        try (Transaction tx = cont.persistence().createTransaction()) {
            EntityManager em = cont.persistence().getEntityManager();
            TypedQuery<User> query = em.createQuery(
                "select u from sec$User u", User.class);
            List<User> list = query.getResultList();
            tx.commit();
            assertTrue(list.size() > 0);
        }
    }
}
