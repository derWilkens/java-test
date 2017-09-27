create table PAXBASE_DEPARTMENT (
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
    NAME varchar(255),
    PARENT_DEPARTMENT_ID varchar(32),
    LEADER_ID varchar(32),
    DEPUTY_LEADER_ID varchar(32),
    --
    primary key (ID)
);
