create table PAXBASE_CAMPAIGN (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    --
    CAMPAIGN_NO varchar(10),
    BEGIN_DATE date,
    END_DATE date,
    SITE_ID varchar(32),
    --
    primary key (ID)
);
