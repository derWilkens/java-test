package local.paxbase.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import local.paxbase.entity.coredata.StandardClientEntity;
import com.haulmont.cuba.core.entity.annotation.Listeners;
import java.util.UUID;

@Listeners("paxbase_UserPreferenceListener")
@Table(name = "PAXBASE_USER_PREFERENCE")
@Entity(name = "paxbase$UserPreference")
public class UserPreference extends StandardClientEntity {
    private static final long serialVersionUID = -7830169924564467941L;

    @Column(name = "USER_ID", nullable = false)
    protected UUID userId;

    @Column(name = "ENTITY_UUID")
    protected UUID entityUuid;

    @Column(name = "USER_VALUE")
    protected String userValue;

    @Column(name = "CONTEXT_ID")
    protected Integer contextId;

    public void setContextId(UserPreferencesContext contextId) {
        this.contextId = contextId == null ? null : contextId.getId();
    }

    public UserPreferencesContext getContextId() {
        return contextId == null ? null : UserPreferencesContext.fromId(contextId);
    }


    public void setUserValue(String userValue) {
        this.userValue = userValue;
    }

    public String getUserValue() {
        return userValue;
    }


    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }


    public UUID getEntityUuid() {
        return entityUuid;
    }

    public void setEntityUuid(UUID entityUuid) {
        this.entityUuid = entityUuid;
    }





}