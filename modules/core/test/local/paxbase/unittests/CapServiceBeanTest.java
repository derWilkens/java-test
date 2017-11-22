package local.paxbase.unittests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.UserSessionSource;

import local.paxbase.entity.UserPreferencesContext;
import local.paxbase.entity.cap.Certificate;
import local.paxbase.entity.cap.coredata.QualificationType;
import local.paxbase.entity.cap.coredata.Role;
import local.paxbase.entity.cap.coredata.RoleQualificationType;
import local.paxbase.entity.coredata.AppUser;
import local.paxbase.entity.coredata.Site;
import local.paxbase.entity.period.AttendencePeriod;
import local.paxbase.entity.period.DutyPeriod;
import local.paxbase.service.CapService;
import local.paxbase.service.UserpreferencesService;

public class CapServiceBeanTest extends StandardTestContainer {
	@ClassRule
	public static StandardTestContainer cont = new StandardTestContainer();

	private CapService service;
	
	private DataManager dataManager;
	private Persistence persistence;
	private UserpreferencesService preferenceService;
	private Metadata metadata;

	private Site site;
	private List<DutyPeriod> periodList;
	private DutyPeriod dutyPeriod;
	
	private Logger log = LoggerFactory.getLogger(TimelineDTOServiceTest.class);

	@Before
	public void setUp() throws Exception {
		dataManager = AppBeans.get(DataManager.class);
		service = AppBeans.get(CapService.NAME);
		preferenceService = AppBeans.get(UserpreferencesService.NAME);
		persistence = cont.persistence();
		AppBeans.get(UserSessionSource.class).getUserSession().setAttribute("client_id", 1);
		try (Transaction tx = cont.persistence().createTransaction()) {

			AppUser personOnDuty = persistence.createTransaction().execute(em -> {
				AppUser u = new AppUser();
				u.setLastname("Lastname");
				em.persist(u);
				return u;
			});
			
			Role emlRole = persistence.createTransaction().execute(em -> {
				Role r = new Role();
				r.setClient(1);
				r.setName("EML-Rolle");
				em.persist(r);
				return r;
			});
			Role defaultRole = persistence.createTransaction().execute(em -> {
				Role r = new Role();
				r.setClient(1);
				r.setName("EML-Rolle");
				em.persist(r);
				return r;
			});
			QualificationType eml1 = persistence.createTransaction().execute(em -> {
				QualificationType q = new QualificationType();
				q.setClient(1);
				em.persist(q);
				return q;
			});
			QualificationType eml2 = persistence.createTransaction().execute(em -> {
				QualificationType q = new QualificationType();
				q.setClient(1);
				em.persist(q);
				return q;
			});
			QualificationType defaultQt1 = persistence.createTransaction().execute(em -> {
				QualificationType q = new QualificationType();
				q.setClient(1);
				em.persist(q);
				return q;
			});
			RoleQualificationType rqt1 = persistence.createTransaction().execute(em->{
				RoleQualificationType r = new RoleQualificationType();
				r.setClient(1);
				r.setQualificationType(eml1);
				r.setRole(emlRole);
				em.persist(r);
				return r;
			});
			RoleQualificationType rqt2 = persistence.createTransaction().execute(em->{
				RoleQualificationType r = new RoleQualificationType();
				r.setClient(1);
				r.setQualificationType(eml2);
				r.setRole(emlRole);
				em.persist(r);
				return r;
			});			
			RoleQualificationType rqt3 = persistence.createTransaction().execute(em->{
				RoleQualificationType r = new RoleQualificationType();
				r.setClient(1);
				r.setQualificationType(defaultQt1);
				r.setRole(defaultRole);
				em.persist(r);
				return r;
			});				
			Certificate certValid1 = persistence.createTransaction().execute(em->{
				Certificate c = new Certificate();
				c.setClient(1);
				c.setAppUser(personOnDuty);
				c.setCertificateType(eml1);
				c.setExpirationDate(new Date());
				em.persist(c);
				return c;
			});
			Certificate certValid2 = persistence.createTransaction().execute(em->{
				Certificate c = new Certificate();
				c.setClient(1);
				c.setAppUser(personOnDuty);
				c.setCertificateType(eml2);
				c.setExpirationDate(new Date());
				em.persist(c);
				return c;
			});
			Certificate certDefault = persistence.createTransaction().execute(em->{
				Certificate c = new Certificate();
				c.setClient(1);
				c.setAppUser(personOnDuty);
				c.setCertificateType(defaultQt1);
				c.setExpirationDate(new Date());
				em.persist(c);
				return c;
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

			periodList = new ArrayList<DutyPeriod>();
			periodList.add(persistence.createTransaction().execute(em -> {
				AttendencePeriod p = new AttendencePeriod();
				p.setClient(1);
				p.setStart(new Date());
				p.setEnd(new Date());
				p.getOperationPeriod().setSite(site);
				p.setPersonOnDuty(personOnDuty);
				em.persist(p);
				dutyPeriod = p;
				return p;
			}));
			tx.commit();
		}
	}
	@Test
	public void test() {
		assertEquals(2, service.getAvailableUserRoles(dutyPeriod).size());
	}

}
