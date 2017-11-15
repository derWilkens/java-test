package local.paxbase.unitests;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.entity.KeyValueEntity;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.ValueLoadContext;

import local.paxbase.service.TimelineService;

public class TimelineDTOServiceTest extends StandardTestContainer {
	@ClassRule
	public static StandardTestContainer cont = new StandardTestContainer();
	
	
	private TimelineService service;
	private DataManager dataManager;
    private Persistence persistence;

	@Before
	public void setUp() throws Exception {
		dataManager = AppBeans.get(DataManager.class);
		service = AppBeans.get(TimelineService.NAME);
        persistence = cont.persistence();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetDto() {
		//fail("Not yet implemented");
		try (Transaction tx = persistence.createTransaction()) {
		service.getEmlDto();
		 tx.commit();
		}
	}

	@Test
	public void testGetUserPreferences() {
		//service.getUserPreferences("Campaign");
	}

	@Test
	public void testGetPeriodTypes() {
		//fail("Not yet implemented");
	}

}
