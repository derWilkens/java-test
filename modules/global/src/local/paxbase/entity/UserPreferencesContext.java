package local.paxbase.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum UserPreferencesContext implements EnumClass<Integer> {

    CampaignBrowse(10),
    Rotaplan(20),
    SiteColor(30),
    SiteBackgroundColor(35),
    SiteRotaplan(40),
    SiteCampaign(50),
    SiteEml(55),
    RotaplanDisplayCampaigns(60),
    RotaplanDepartments(70),
    RotaplanUsers(80),
    RotaplanStandardDuties(90),
    EmlDisplaySite(100);
	

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