package local.paxbase.unittests;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.UserSessionSource;

import local.paxbase.entity.UserPreference;
import local.paxbase.entity.UserPreferencesContext;
import local.paxbase.entity.coredata.AppUser;
import local.paxbase.entity.coredata.Department;
import local.paxbase.entity.coredata.Site;
import local.paxbase.entity.dto.TimelineDTO;
import local.paxbase.entity.period.AttendencePeriod;
import local.paxbase.entity.period.OperationPeriod;
import local.paxbase.service.TimelineService;
import local.paxbase.service.UserpreferencesService;
public class TimelineDTOServiceTest extends StandardTestContainer {
	@ClassRule
	public static StandardTestContainer cont = new StandardTestContainer();

	private TimelineService service;
	private DataManager dataManager;
	private Persistence persistence;
	private UserpreferencesService preferenceService;
	private Metadata metadata;

	private Site site;
	private List<AttendencePeriod> periodList;
	
	private Logger log = LoggerFactory.getLogger(TimelineDTOServiceTest.class);
	
	private Department department;
	private AppUser personOnDuty;
	private OperationPeriod op;
	@Before
	public void setUp() throws Exception {
		dataManager = AppBeans.get(DataManager.class);
		service = AppBeans.get(TimelineService.NAME);
		preferenceService = AppBeans.get(UserpreferencesService.NAME);
		persistence = cont.persistence();

		AppBeans.get(UserSessionSource.class).getUserSession().setAttribute("client_id", 1);
		try (Transaction tx = cont.persistence().createTransaction()) {
			department = persistence.createTransaction().execute(em -> {
				Department d = new Department();
				d.setAcronym("AB");
				em.persist(d);
				return d;
			});
			personOnDuty = persistence.createTransaction().execute(em -> {
				AppUser u = new AppUser();
				u.setLastname("Lastname");
				u.setDepartment(department);
				em.persist(u);
				return u;
			});

			site = persistence.createTransaction().execute(em -> {
				Site site = new Site();
				site.setClient(1);
				site.setSiteName("Testsite");
				site.setItemDesignation("TEST");
				em.persist(site);
				return site;
			});

			preferenceService.createPreference(UserPreferencesContext.EmlDisplaySite, site.getUuid(), null);
			op = persistence.createTransaction().execute(em ->{
				OperationPeriod o = new OperationPeriod();
				o.setSite(site);
				o.setStart(new Date());
				o.setEnd(new Date());
				
				return o;
			});
			periodList = new ArrayList<AttendencePeriod>();
			periodList.add(persistence.createTransaction().execute(em -> {
				AttendencePeriod p = new AttendencePeriod();
				p.setOperationPeriod(op);
				p.setClient(1);
				p.setStart(new Date());
				p.setEnd(new Date());
				p.getOperationPeriod().setSite(site);
				p.setPersonOnDuty(personOnDuty);
				em.persist(p);
				return p;
			}));
			tx.commit();
		}

		DataManager dataManager = AppBeans.get(DataManager.class);
		
		//die evtl. vorhanden UserPref für EML display aufräumen
	    LoadContext<UserPreference> loadContext = LoadContext.create(UserPreference.class)
	            .setQuery(LoadContext.createQuery("select p from paxbase$UserPreference p where p.contextId = :context")
	                .setParameter("context", UserPreferencesContext.EmlDisplaySite))
	            .setView("userPreference-view");
	    List<UserPreference> upList = dataManager.loadList(loadContext);
	    log.info("UPref vorhanden:"+upList.size());
	    
	    for (UserPreference userPreference : upList) {
	    	if(userPreference.getContextId().equals(UserPreferencesContext.EmlDisplaySite)){
			dataManager.remove(userPreference);
			log.info("Remove UserPref: " + userPreference.getEntityUuid());
	    	}
		}

		metadata = cont.metadata();
		UserPreference pref = metadata.create(UserPreference.class);
		pref.setClient(1);
		pref.setContextId(UserPreferencesContext.EmlDisplaySite);
		pref.setEntityUuid(site.getId());

		// Save the customer to the database
		dataManager.commit(pref);
		

		/*
		 * Transactions DataManager always starts a new transaction and commits
		 * it on operation completion, thus returning entities in the detached
		 * state.
		 */
		

	}

	@After
	public void tearDown() throws Exception {
		
		for(AttendencePeriod period:periodList){
			dataManager.remove(period);
		}
		dataManager.remove(op);
		dataManager.remove(site);
		dataManager.remove(personOnDuty);
		dataManager.remove(department);
	}

	@Test
	public void testGetDto() {
		// fail("Not yet implemented");
		try (Transaction tx = persistence.createTransaction()) {
			UserpreferencesService prefService = AppBeans.get(UserpreferencesService.NAME);
			prefService.getPreference(UserPreferencesContext.EmlDisplaySite, null);
			//service.getEmlDto();
			TimelineDTO rotoplanDto = service.getRotoplanDto();
			assertEquals(1, rotoplanDto.getGroupList().size());
			assertEquals("", rotoplanDto.getGroupList().get(0).getContent());
			tx.commit();
		}
	}

}
