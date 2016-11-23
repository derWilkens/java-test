/*
 * Copyright (c) 2016 saas
 */
package local.paxbase.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;

/**
 * @author christian
 */
public enum SiteTypeEnum implements EnumClass<String> {

    Airport("1"),
    Platform_w_Helideck("2"),
    Platform_wo_Helideck("3"),
    OWF_w_Helideck("4"),
    Port("5");

    private String id;

    SiteTypeEnum(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static SiteTypeEnum fromId(String id) {
        for (SiteTypeEnum at : SiteTypeEnum.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}