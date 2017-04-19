alter table PAXBASE_CAMPAIGN add column FUNCTION_CATEGORY_ID varchar(32) ;
alter table PAXBASE_CAMPAIGN drop column CATEGORY_ID cascade ;
