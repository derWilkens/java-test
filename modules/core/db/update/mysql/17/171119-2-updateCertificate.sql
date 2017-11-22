alter table PAXBASE_CERTIFICATE add column APP_USER_ID varchar(32) ;
alter table PAXBASE_CERTIFICATE drop column OFFSHORE_USER_ID cascade ;
