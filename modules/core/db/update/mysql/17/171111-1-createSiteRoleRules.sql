create table PAXBASE_SITE_ROLE_RULES (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    --
    SITE_ID varchar(32),
    ROLE varchar(255),
    REQUIRED_FROM_NO_OF_POB integer,
    --
    primary key (ID)
);
