/*
 * Copyright (c) 2016 saas
 */
package local.paxbase.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.haulmont.cuba.core.entity.StandardEntity;

/**
 * @author christian
 */
@Table(name = "SAAS_CRAFT_TYPES")
@Entity(name = "saas$CraftTypes")
public class CraftTypes extends StandardEntity {
    private static final long serialVersionUID = 5779475331537662563L;

}