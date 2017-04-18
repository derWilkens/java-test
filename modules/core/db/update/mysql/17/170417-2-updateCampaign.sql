alter table PAXBASE_CAMPAIGN add column CLIENT integer ^
update PAXBASE_CAMPAIGN set CLIENT = 0 where CLIENT is null ;
alter table PAXBASE_CAMPAIGN modify column CLIENT integer not null ;
