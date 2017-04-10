package local.paxbase.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum PeriodSubClass implements EnumClass<String> {

    Campaign("1"),
    ServicePeriod("2");

    private String id;

    PeriodSubClass(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static PeriodSubClass fromId(String id) {
        for (PeriodSubClass at : PeriodSubClass.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}