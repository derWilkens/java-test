package local.paxbase.listener;

import org.springframework.stereotype.Component;
import com.haulmont.cuba.core.listener.BeforeInsertEntityListener;
import com.haulmont.cuba.core.EntityManager;
import local.paxbase.entity.AdministrationPeriod;
import local.paxbase.entity.coredata.PeriodSubClass;
import local.paxbase.entity.coredata.PeriodType;

@Component("paxbase_AdministrationPeriodListener")
public class AdministrationPeriodListener implements BeforeInsertEntityListener<AdministrationPeriod> {


    @Override
    public void onBeforeInsert(AdministrationPeriod entity, EntityManager entityManager) {
    	
    	
    }


}