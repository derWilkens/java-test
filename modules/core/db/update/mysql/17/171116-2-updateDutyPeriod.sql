alter table PAXBASE_DUTY_PERIOD drop column PERSON_ON_DUTY_ID cascade ;
alter table PAXBASE_DUTY_PERIOD add column PERSON_ON_DUTY_ID varchar(32) ;
alter table PAXBASE_DUTY_PERIOD drop column CONTRACTOR_ID cascade ;
