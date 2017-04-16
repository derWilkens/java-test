alter table PAXBASE_CAMPAIGN add column CATEGORY_ID varchar(32) ;
alter table PAXBASE_CAMPAIGN drop column TYPE_ID cascade ;
