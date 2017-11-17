create table PAXBASE_APP_USER (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    DTYPE varchar(100),
    --
    CLIENT integer,
    LASTNAME varchar(50),
    FIRSTNAME varchar(50),
    EMAIL varchar(100),
    COMPANY_ID varchar(32),
    DEPARTMENT_ID varchar(32),
    --
    -- from paxbase$OffshoreUser
    WEIGHT integer,
    WEIGHT_CHANGE_DATE date,
    --
    primary key (ID)
);
