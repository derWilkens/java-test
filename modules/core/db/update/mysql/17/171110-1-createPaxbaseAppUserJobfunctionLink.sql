create table PAXBASE_APP_USER_JOBFUNCTION_LINK (
    JOBFUNCTION_ID varchar(32),
    APP_USER_ID varchar(32),
    primary key (JOBFUNCTION_ID, APP_USER_ID)
);
