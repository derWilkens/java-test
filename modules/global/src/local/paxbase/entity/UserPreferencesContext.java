package local.paxbase.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum UserPreferencesContext implements EnumClass<Integer> {

    CampaignBrowse(10),
    Rotaplan(20),
    SiteColors(30);

    private Integer id;

    UserPreferencesContext(Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static UserPreferencesContext fromId(Integer id) {
        for (UserPreferencesContext at : UserPreferencesContext.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}