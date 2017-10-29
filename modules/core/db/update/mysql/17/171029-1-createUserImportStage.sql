create table PAXBASE_USER_IMPORT_STAGE (
    ID varchar(32),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    DTYPE varchar(31),
    --
    FIRSTNAME varchar(20),
    LASTNAME varchar(50),
    EMAIL varchar(50),
    DEPARTMENT varchar(20),
    POSITION_ varchar(20),
    IMPORT_LOG longtext,
    --
    primary key (ID)
);
