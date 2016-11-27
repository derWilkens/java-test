-- begin PAXBASE_OFFSHORE_USER
create table PAXBASE_OFFSHORE_USER (
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
    WEIGHT integer,
    WEIGHT_CHANGE_DATE date,
    USER_ID varchar(32),
    COMPANY_ID varchar(32),
    --
    primary key (ID)
)^
-- end PAXBASE_OFFSHORE_USER
-- begin PAXBASE_APP_USER
create table PAXBASE_APP_USER (
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
    primary key (ID)
)^
-- end PAXBASE_APP_USER
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
    TRANSFER_ORDER_NO varchar(50),
    CREW_CHANGE_ID varchar(32) not null,
    MODE_OF_TRANSPORT integer not null,
    OPERATED_BY_ID varchar(32),
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
    SITE_TYPE varchar(50) not null,
    PARENT_SITE_ID varchar(32),
    SHORT_ITEM_DESIGNATION varchar(4),
    --
    primary key (ID)
)^
-- end PAXBASE_SITE
-- begin PAXBASE_WAYPOINT
create table PAXBASE_WAYPOINT (
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
    TAKE_OFF datetime(3) not null,
    TRANSFER_DURATION integer,
    PREV_WAYPOINT_ID varchar(32),
    NEXT_WAYPOINT_ID varchar(32),
    TRANSFER_ID varchar(32),
    SITE_ID varchar(32),
    --
    primary key (ID)
)^
-- end PAXBASE_WAYPOINT

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
    USER_ID varchar(32) not null,
    SITE_ID varchar(32),
    OUTBOUND_TRANSFER_ID varchar(32),
    INBOUND_TRANSFER_ID varchar(32),
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
