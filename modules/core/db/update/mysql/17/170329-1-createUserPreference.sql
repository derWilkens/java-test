create table PAXBASE_USER_PREFERENCE (
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
    USER_ID varchar(32) not null,
    CONTEXT varchar(40),
    ENTITY_UUID varchar(32),
    --
    primary key (ID)
);
