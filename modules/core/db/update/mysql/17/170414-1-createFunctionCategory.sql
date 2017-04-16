create table PAXBASE_FUNCTION_CATEGORY (
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
    CATEGORY_NAME varchar(50),
    PARENT_TYPE_ID varchar(32),
    PERIOD_SUB_CLASS varchar(50),
    --
    primary key (ID)
);
