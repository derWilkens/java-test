create table PAXBASE_OPERATION_PERIOD (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    CLIENT integer not null,
    START_ datetime(3),
    END_ datetime(3),
    FUNCTION_CATEGORY_ID varchar(32),
    REMARK varchar(255),
    SITE_ID varchar(32),
    --
    PARENT_PERIOD_ID varchar(32),
    --
    primary key (ID)
);
