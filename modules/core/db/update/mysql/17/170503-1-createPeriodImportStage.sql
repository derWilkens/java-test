create table PAXBASE_PERIOD_IMPORT_STAGE (
    ID varchar(32),
    UPDATE_TS datetime,
    UPDATED_BY varchar(50),
    CREATE_TS datetime,
    CREATED_BY varchar(50),
    DTYPE varchar(31),
    --
    ITEM_DESIGNATION varchar(10),
    CAMPAIGN_NUMBER varchar(10),
    START_DATE date,
    END_DATE date,
    SHUTDOWN_ boolean,
    --
    primary key (ID)
);
