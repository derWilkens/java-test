create table PAXBASE_PERIOD_TYPE (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    --
    TYPE_ varchar(20),
    TYPE_GROUP varchar(20),
    --
    primary key (ID)
);
