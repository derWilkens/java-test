alter table PAXBASE_DUTY_PERIOD add column CLIENT integer ^
update PAXBASE_DUTY_PERIOD set CLIENT = 0 where CLIENT is null ;
alter table PAXBASE_DUTY_PERIOD modify column CLIENT integer not null ;
