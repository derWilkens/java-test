/*
 * Copyright (c) 2016 saas
 */
package local.paxbase.entity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 * @author christian
 */
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "PAXBASE_APP_USER")
@Entity(name = "paxbase$AppUser")
public class AppUser extends StandardClientEntity {
    private static final long serialVersionUID = 6123712345961441544L;

}