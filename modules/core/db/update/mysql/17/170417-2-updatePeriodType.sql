alter table PAXBASE_PERIOD_TYPE add column CLIENT integer ^
update PAXBASE_PERIOD_TYPE set CLIENT = 0 where CLIENT is null ;
alter table PAXBASE_PERIOD_TYPE modify column CLIENT integer not null ;
