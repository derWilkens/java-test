package local.paxbase.listener;

import org.springframework.stereotype.Component;

import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.core.listener.BeforeInsertEntityListener;

import local.paxbase.entity.coredata.DutyPeriodTemplate;

@Component("paxbase_DutyPeriodTemplateListener")
public class DutyPeriodTemplateListener implements BeforeInsertEntityListener<DutyPeriodTemplate> {


    @Override
    public void onBeforeInsert(DutyPeriodTemplate entity, EntityManager entityManager) {
    	UserSessionSource session = AppBeans.get(UserSessionSource.class);
    	entity.setUser(session.getUserSession().getUser());
    }


}