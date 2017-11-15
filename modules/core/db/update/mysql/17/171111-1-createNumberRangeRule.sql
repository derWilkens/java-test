create table PAXBASE_NUMBER_RANGE_RULE (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    --
    AMOUNT_FROM integer,
    AMOUNT_TO integer,
    REQUIRED_NUMBER integer,
    SITE_ROLE_RULES_ID varchar(32),
    --
    primary key (ID)
);
