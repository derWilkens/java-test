alter table PAXBASE_DUTY_PERIOD drop column USER_ID cascade ;
-- alter table PAXBASE_DUTY_PERIOD add column USER_ID varchar(32) ^
-- update PAXBASE_DUTY_PERIOD set USER_ID = <default_value> ;
-- alter table PAXBASE_DUTY_PERIOD modify column USER_ID varchar(32) not null ;
alter table PAXBASE_DUTY_PERIOD add column USER_ID varchar(32) not null ;
