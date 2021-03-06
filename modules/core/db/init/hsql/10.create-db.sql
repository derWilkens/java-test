-- begin PAXBASE_COMPANY
create table PAXBASE_COMPANY (
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
    COMPANY_NAME varchar(100) not null,
    CONTACT_PERSON varchar(100),
    EMAIL varchar(100) not null,
    --
    primary key (ID)
)^
-- end PAXBASE_COMPANY
-- begin PAXBASE_TRANSFER
create table PAXBASE_TRANSFER (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    CLIENT integer not null,
    --
    TRANSFER_ORDER_NO integer not null,
    CREW_CHANGE_ID varchar(36) not null,
    OPERATED_BY_ID varchar(36),
    MODE_OF_TRANSFER_ID varchar(36),
    --
    primary key (ID)
)^
-- end PAXBASE_TRANSFER
-- begin PAXBASE_CREW_CHANGE
create table PAXBASE_CREW_CHANGE (
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
    FLIGHT_DATE date not null,
    --
    primary key (ID)
)^
-- end PAXBASE_CREW_CHANGE
-- begin PAXBASE_SITE
create table PAXBASE_SITE (
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
    NAME varchar(50) not null,
    ITEM_DESIGNATION varchar(4),
    PARENT_SITE_ID varchar(32),
    SHORT_ITEM_DESIGNATION varchar(4),
    SITE_TYPE_ID varchar(32),
    --
    primary key (ID)
)^
-- end PAXBASE_SITE
-- begin PAXBASE_WAYPOINT
create table PAXBASE_WAYPOINT (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    CLIENT integer not null,
    --
    TAKE_OFF time not null,
    TRANSFER_DURATION time,
    PREV_WAYPOINT_ID varchar(36),
    NEXT_WAYPOINT_ID varchar(36),
    TRANSFER_ID varchar(36) not null,
    SITE_ID varchar(36),
    ORDER_NO integer not null,
    --
    primary key (ID)
)^-- end PAXBASE_WAYPOINT

-- begin PAXBASE_PAYLOAD
create table PAXBASE_PAYLOAD (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    --
    CRAFT_TYPE_ID varchar(32) not null,
    SITE_A_ID varchar(32) not null,
    SITE_B_ID varchar(32) not null,
    PAYLOAD integer not null,
    --
    primary key (ID)
)^
-- end PAXBASE_PAYLOAD
-- begin PAXBASE_DUTY_PERIOD
create table PAXBASE_DUTY_PERIOD (
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
    BEGIN_DATE date not null,
    END_DATE date,
    USER_ID varchar(32),
    SITE_ID varchar(32),
    OUTBOUND_TRANSFER_ID varchar(32),
    INBOUND_TRANSFER_ID varchar(32),
    CONTRACTOR_ID varchar(32),
    --
    primary key (ID)
)^
-- end PAXBASE_DUTY_PERIOD
-- begin PAXBASE_CRAFT_TYPE
create table PAXBASE_CRAFT_TYPE (
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
    NAME varchar(50) not null,
    SEATS integer not null,
    --
    primary key (ID)
)^
-- end PAXBASE_CRAFT_TYPE
-- begin SEC_USER
alter table SEC_USER add column WEIGHT integer ^
alter table SEC_USER add column WEIGHT_CHANGE_DATE date ^
alter table SEC_USER add column COMPANY_ID varchar(32) ^
alter table SEC_USER add column CLIENT integer ^
alter table SEC_USER add column DTYPE varchar(100) ^
update SEC_USER set DTYPE = 'sec$User' where DTYPE is null ^
ALTER TABLE SEC_USER ALTER COLUMN LOGIN set NULL ^  
ALTER TABLE SEC_USER ALTER COLUMN LOGIN_LC set NULL ^ 
ALTER TABLE SEC_USER ALTER COLUMN GROUP_ID set NULL ^ 
-- end SEC_USER
-- begin PAXBASE_MODE_OF_TRANSFER
create table PAXBASE_MODE_OF_TRANSFER (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    --
    MODE_ varchar(50),
    --
    primary key (ID)
)^
-- end PAXBASE_MODE_OF_TRANSFER
-- begin PAXBASE_SITE_TYPE
create table PAXBASE_SITE_TYPE (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    --
    TYPE_ varchar(50),
    --
    primary key (ID)
)^
-- end PAXBASE_SITE_TYPE
-- begin PAXBASE_TRANSFER_WAYPOINT_LINK
create table PAXBASE_TRANSFER_WAYPOINT_LINK (
    WAYPOINT_ID varchar(32),
    TRANSFER_ID varchar(32),
    primary key (WAYPOINT_ID, TRANSFER_ID)
)^
-- end PAXBASE_TRANSFER_WAYPOINT_LINK
