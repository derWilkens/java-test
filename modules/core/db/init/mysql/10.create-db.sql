-- begin SAAS_APP_USER
create table SAAS_APP_USER (
    ID varchar(36),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    CLIENT integer not null,
    --
    primary key (ID)
)^
-- end SAAS_APP_USER
-- begin SAAS_COMPANY
create table SAAS_COMPANY (
    ID varchar(36),
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
-- end SAAS_COMPANY
-- begin SAAS_TRANSFER
create table SAAS_TRANSFER (
    ID varchar(36),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    CLIENT integer not null,
    --
    SAP_ORDER_NO varchar(50),
    CREW_CHANGE_ID varchar(32) not null,
    MODE_OF_TRANSPORT integer not null,
    OPERATED_BY_ID varchar(36),
    --
    primary key (ID)
)^
-- end SAAS_TRANSFER
-- begin SAAS_CREW_CHANGE
create table SAAS_CREW_CHANGE (
    ID varchar(36),
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
-- end SAAS_CREW_CHANGE
-- begin SAAS_SITE
create table SAAS_SITE (
    ID varchar(36),
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
    SAP_DESIGNATION varchar(4),
    SITE_TYPE varchar(50) not null,
    PARENT_SITE_ID varchar(36),
    --
    primary key (ID)
)^
-- end SAAS_SITE
-- begin SAAS_WAYPOINT
create table SAAS_WAYPOINT (
    ID varchar(36),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    CLIENT integer not null,
    --
    TAKE_OFF datetime(3) not null,
    TRANSFER_DURATION integer,
    PREV_WAYPOINT_ID varchar(36),
    NEXT_WAYPOINT_ID varchar(36),
    TRANSFER_ID varchar(36),
    SITE_ID varchar(36),
    --
    primary key (ID)
)^
-- end SAAS_WAYPOINT
-- begin SAAS_CRAFT_TYPES
create table SAAS_CRAFT_TYPES (
    ID varchar(36),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    --
    primary key (ID)
)^
-- end SAAS_CRAFT_TYPES

-- begin SAAS_OFFSHORE_USER
create table SAAS_OFFSHORE_USER (
    ID varchar(36),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    CLIENT integer not null,
    --
    WEIGHT integer,
    WEIGHT_CHANGE_DATE date,
    USER_ID varchar(36),
    --
    primary key (ID)
)^
-- end SAAS_OFFSHORE_USER
