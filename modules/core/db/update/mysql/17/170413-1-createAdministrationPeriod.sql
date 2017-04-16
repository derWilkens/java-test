create table PAXBASE_ADMINISTRATION_PERIOD (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    START_ datetime(3),
    END_ datetime(3),
    TYPE_ID varchar(32),
    --
    PERSON_ON_DUTY_ID varchar(32),
    SITE_ID varchar(32),
    --
    primary key (ID)
);
