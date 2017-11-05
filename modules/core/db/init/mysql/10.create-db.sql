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
    OPERATED_BY_ID varchar(32),
    MODE_OF_TRANSFER_ID varchar(32),
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
-- begin PAXBASE_SITEcreate table PAXBASE_SITE (
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
    SITE_NAME varchar(50) not null,
    ITEM_DESIGNATION varchar(7),
    PARENT_SITE_ID varchar(32),
    SHORT_ITEM_DESIGNATION varchar(4),
    SITE_TYPE_ID varchar(32),
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
    TAKE_OFF time(3) not null,
    TRANSFER_DURATION time(3),
    PREV_WAYPOINT_ID varchar(32),
    NEXT_WAYPOINT_ID varchar(32),
    TRANSFER_ID varchar(32),
    SITE_ID varchar(32),
    --
    primary key (ID)
)^-- end PAXBASE_WAYPOINT

-- begin PAXBASE_PAYLOADcreate table PAXBASE_PAYLOAD (
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
    CRAFT_TYPE_ID varchar(32) not null,
    SITE_A_ID varchar(32) not null,
    SITE_B_ID varchar(32) not null,
    PAYLOAD integer not null,
    --
    primary key (ID)
)^
-- end PAXBASE_PAYLOAD
-- begin PAXBASE_DUTY_PERIODcreate table PAXBASE_DUTY_PERIOD (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    CLIENT integer not null,
    START_ datetime(3),
    END_ datetime(3),
    FUNCTION_CATEGORY_ID varchar(32),
    REMARK varchar(255),
    --
    PERSON_ON_DUTY_ID varchar(32),
    SITE_ID varchar(32),
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
    MODE_OF_TRANSFER_ID varchar(32),
    --
    primary key (ID)
)^
-- end PAXBASE_CRAFT_TYPE
-- begin SEC_USERalter table SEC_USER add column WEIGHT integer ^
alter table SEC_USER add column WEIGHT_CHANGE_DATE date ^
alter table SEC_USER add column COMPANY_ID varchar(32) ^
alter table SEC_USER add column DEPARTMENT_ID varchar(32) ^
alter table SEC_USER add column CLIENT integer ^
alter table SEC_USER add column WEIGHT integer ^
alter table SEC_USER add column WEIGHT_CHANGE_DATE date ^
alter table SEC_USER add column COMPANY_ID varchar(32) ^
alter table SEC_USER add column DEPARTMENT_ID varchar(32) ^
alter table SEC_USER add column DTYPE varchar(100) ^
update SEC_USER set DTYPE = 'sec$User' where DTYPE is null ^
-- end SEC_USER
-- begin PAXBASE_MODE_OF_TRANSFERcreate table PAXBASE_MODE_OF_TRANSFER (
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
    MODE_ varchar(50),
    --
    primary key (ID)
)^
-- end PAXBASE_MODE_OF_TRANSFER
-- begin PAXBASE_SITE_TYPEcreate table PAXBASE_SITE_TYPE (
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
    TYPE_ varchar(50),
    PARENT_TYPE_ID varchar(32),
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
-- begin PAXBASE_CAMPAIGN
create table PAXBASE_CAMPAIGN (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    CLIENT integer not null,
    START_ datetime(3),
    END_ datetime(3),
    FUNCTION_CATEGORY_ID varchar(32),
    REMARK varchar(255),
    --
    CAMPAIGN_NUMBER varchar(10),
    SHUTDOWN_ boolean,
    SITE_ID varchar(32),
    --
    primary key (ID)
)^
-- end PAXBASE_CAMPAIGN

-- begin PAXBASE_USER_PREFERENCE
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
    ENTITY_UUID varchar(32),
    USER_VALUE varchar(255),
    CONTEXT_ID integer,
    --
    primary key (ID)
)^
-- end PAXBASE_USER_PREFERENCE
-- begin PAXBASE_PERIOD_TYPE
create table PAXBASE_PERIOD_TYPE (
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
    TYPE_NAME varchar(20),
    PARENT_TYPE_ID varchar(32),
    PERIOD_SUB_CLASS varchar(50),
    --
    primary key (ID)
)^
-- end PAXBASE_PERIOD_TYPE

-- begin PAXBASE_FUNCTION_CATEGORY
create table PAXBASE_FUNCTION_CATEGORY (
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
    CATEGORY_NAME varchar(50),
    PARENT_TYPE_ID varchar(32),
    PERIOD_SUB_CLASS varchar(50),
    --
    primary key (ID)
)^
-- end PAXBASE_FUNCTION_CATEGORY
-- begin PAXBASE_CONTRACTOR_DUTY_PERIOD
create table PAXBASE_CONTRACTOR_DUTY_PERIOD (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    CLIENT integer not null,
    START_ datetime(3),
    END_ datetime(3),
    FUNCTION_CATEGORY_ID varchar(32),
    REMARK varchar(255),
    DTYPE varchar(31),
    --
    CONTRACTOR_ID varchar(32),
    --
    primary key (ID)
)^
-- end PAXBASE_CONTRACTOR_DUTY_PERIOD

-- begin PAXBASE_PERIOD_IMPORT_STAGE
create table PAXBASE_PERIOD_IMPORT_STAGE (
    ID varchar(32),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    DTYPE varchar(31),
    --
    ITEM_DESIGNATION varchar(10),
    CAMPAIGN_NUMBER varchar(10),
    START_DATE date,
    END_DATE date,
    SHUTDOWN_ boolean,
    IMPORT_LOG longtext,
    --
    primary key (ID)
)^
-- end PAXBASE_PERIOD_IMPORT_STAGE
-- begin PAXBASE_QUALIFICATION_TYPE
create table PAXBASE_QUALIFICATION_TYPE (
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
    --
    primary key (ID)
)^
-- end PAXBASE_QUALIFICATION_TYPE
-- begin PAXBASE_ROLE
create table PAXBASE_ROLE (
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
    NAME varchar(30),
    --
    primary key (ID)
)^
-- end PAXBASE_ROLE
-- begin PAXBASE_JOBFUNCTION
create table PAXBASE_JOBFUNCTION (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    --
    NAME varchar(30),
    --
    primary key (ID)
)^
-- end PAXBASE_JOBFUNCTION
-- begin PAXBASE_CERTIFICATE
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
    STATE varchar(255),
    --
    primary key (ID)
)^
-- end PAXBASE_CERTIFICATE
-- begin PAXBASE_FUNCTION_ROLE_LINK
create table PAXBASE_FUNCTION_ROLE_LINK (
    FUNCTION_ID varchar(32),
    ROLE_ID varchar(32),
    primary key (FUNCTION_ID, ROLE_ID)
)^
-- end PAXBASE_FUNCTION_ROLE_LINK
-- begin PAXBASE_ROLE_QUALIFICATION_TYPE_LINK
create table PAXBASE_ROLE_QUALIFICATION_TYPE_LINK (
    ROLE_ID varchar(32),
    QUALIFICATION_TYPE_ID varchar(32),
    primary key (ROLE_ID, QUALIFICATION_TYPE_ID)
)^
-- end PAXBASE_ROLE_QUALIFICATION_TYPE_LINK
-- begin PAXBASE_DEPARTMENT
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
    NAME varchar(100),
    PARENT_DEPARTMENT_ID varchar(32),
    LEADER_ID varchar(32),
    DEPUTY_LEADER_ID varchar(32),
    ACRONYM varchar(15),
    --
    primary key (ID)
)^
-- end PAXBASE_DEPARTMENT
-- begin PAXBASE_USER_IMPORT_STAGE
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
    POSITION_ varchar(50),
    IMPORT_LOG longtext,
    --
    primary key (ID)
)^
-- end PAXBASE_USER_IMPORT_STAGE
-- begin PAXBASE_DUTY_PERIOD_TEMPLATE
create table PAXBASE_DUTY_PERIOD_TEMPLATE (
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
    FUNCTION_CATEGORY_ID varchar(32),
    DEFAULT_DURATION integer,
    SITE_ID varchar(32),
    USER_ID varchar(32),
    --
    primary key (ID)
)^
-- end PAXBASE_DUTY_PERIOD_TEMPLATE
