alter table PAXBASE_PAYLOAD add column CLIENT integer ^
update PAXBASE_PAYLOAD set CLIENT = 0 where CLIENT is null ;
alter table PAXBASE_PAYLOAD modify column CLIENT integer not null ;
