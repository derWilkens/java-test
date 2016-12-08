alter table PAXBASE_SITE add column SITE_TYPE_ID varchar(32) ;
alter table PAXBASE_SITE drop column SITE_TYPE cascade ;
