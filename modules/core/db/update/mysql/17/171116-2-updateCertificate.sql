alter table PAXBASE_CERTIFICATE drop column OFFSHORE_USER_ID cascade ;
alter table PAXBASE_CERTIFICATE add column OFFSHORE_USER_ID varchar(32) ;
