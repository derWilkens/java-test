create table PAXBASE_CERTIFICATE (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    --
    ISSUING_DATE date,
    EXPIRATION_DATE date,
    VERFIED_BY_ID varchar(32),
    QUALIFICATION_TYPE_ID varchar(32),
    OFFSHORE_USER_ID varchar(32),
    FILE_DATA_ID varchar(32),
    CERTIFICATE_TYPE_ID varchar(32),
    --
    primary key (ID)
);
