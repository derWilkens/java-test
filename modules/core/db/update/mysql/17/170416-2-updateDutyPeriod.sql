alter table PAXBASE_DUTY_PERIOD add column CATEGORY_ID varchar(32) ;
alter table PAXBASE_DUTY_PERIOD drop column TYPE_ID cascade ;
