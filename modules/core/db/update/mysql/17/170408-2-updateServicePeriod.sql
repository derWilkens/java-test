alter table PAXBASE_SERVICE_PERIOD add column PERSON_ON_DUTY_ID varchar(32) ;
alter table PAXBASE_SERVICE_PERIOD drop column OFFSHORE_USER_ID cascade ;
