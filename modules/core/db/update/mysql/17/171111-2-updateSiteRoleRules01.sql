alter table PAXBASE_SITE_ROLE_RULES add column REQUIRED_NUMBER_OF_EMPLOYEES integer ;
alter table PAXBASE_SITE_ROLE_RULES add column NUMBER_OF_POB_EXCEED integer ;
alter table PAXBASE_SITE_ROLE_RULES add column ADDITIONALY_REQ_NUMBER_OF_EMP integer ;
alter table PAXBASE_SITE_ROLE_RULES drop column REQUIRED_FROM_NO_OF_POB cascade ;
