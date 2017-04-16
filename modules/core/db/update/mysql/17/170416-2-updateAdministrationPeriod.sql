alter table PAXBASE_ADMINISTRATION_PERIOD add column CATEGORY_ID varchar(32) ;
alter table PAXBASE_ADMINISTRATION_PERIOD drop column TYPE_ID cascade ;
