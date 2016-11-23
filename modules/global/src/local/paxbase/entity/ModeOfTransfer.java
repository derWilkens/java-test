/*
 * Copyright (c) 2016 saas
 */
package local.paxbase.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;

/**
 * @author christian
 */
public enum ModeOfTransfer implements EnumClass<Integer> {

    Helicopter(1),
    CTV(2),
    Other(3);

    private Integer id;

    ModeOfTransfer(Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static ModeOfTransfer fromId(Integer id) {
        for (ModeOfTransfer at : ModeOfTransfer.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}