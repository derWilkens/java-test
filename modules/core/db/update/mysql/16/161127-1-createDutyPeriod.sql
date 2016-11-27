create table PAXBASE_DUTY_PERIOD (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    CLIENT integer not null,
    --
    BEGIN_DATE date not null,
    END_DATE date,
    USER_ID varchar(32) not null,
    SITE_ID varchar(32),
    --
    primary key (ID)
);
